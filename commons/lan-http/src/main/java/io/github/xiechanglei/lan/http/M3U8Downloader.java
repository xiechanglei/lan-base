package io.github.xiechanglei.lan.http;

import io.github.xiechanglei.lan.digest.aes.AESDigest;
import io.github.xiechanglei.lan.lang.file.FileSize;
import io.github.xiechanglei.lan.lang.thread.ThreadPoolHelper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * M3U8下载器
 *
 * @author xie
 * @date 2024/9/2
 */
@Log4j2
public class M3U8Downloader {
    /**
     * 多线程下载m3u8文件
     *
     * @param url         m3u8文件的url
     * @param path        下载的路径
     * @param threadCount 线程数
     * @param force       是否强制下载
     */
    public static void download(String url, String path, int threadCount, boolean force) throws Exception {
        File target = new File(path);
        if (target.exists()) {
            log.error("文件已存在:{}", target.getAbsolutePath());
            throw new RuntimeException("文件已存在:" + target.getAbsolutePath());
        }
        M3U8Info m3U8Info = parse(url);
        try {
            downloadTsFilesFromM3U8Info(m3U8Info, path + "_download", threadCount);
        } catch (Exception e) {
            if (!force) {
                throw e;
            }
        }
        contactFiles(path + "_download", path);
    }

    public static void contactFiles(String path, String mp4Path) {
        log.info("合并文件:{}", path);
        File ffmpegConcatFile = new File(path + "/ffmpeg_concat.txt");
        if (!ffmpegConcatFile.exists()) {
            log.error("ffmpeg合并文件不存在:{}", ffmpegConcatFile.getAbsolutePath());
            throw new RuntimeException("ffmpeg合并文件不存在:" + ffmpegConcatFile.getAbsolutePath());
        }
        try {
            Runtime.getRuntime().exec("ffmpeg -f concat -safe 0 -ignore_unknown -i " + ffmpegConcatFile.getAbsolutePath() + " -c copy " + mp4Path).waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("合并文件失败:{}", path);
            throw new RuntimeException("合并文件失败:" + path);
        }
        log.info("合并文件成功:{},文件大小:{}", mp4Path, FileSize.format(new File(mp4Path).length()));
        //删除临时文件
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.delete()) {
                    log.error("删除文件失败:{}", f.getAbsolutePath());
                }
            }
        }
        if (!file.delete()) {
            log.error("删除文件夹失败:{}", file.getAbsolutePath());
        }
    }

    /**
     * 下载m3u8文件到指定路径
     *
     * @param m3U8Info m3u8信息
     * @param path     下载路径
     */
    public static void downloadTsFilesFromM3U8Info(M3U8Info m3U8Info, String path, int threadCount) throws Exception {
        log.info("下载m3u8文件到:{}", path);
        File downloadDir = new File(path);
        if (!downloadDir.exists()) {
            if (!downloadDir.mkdirs()) {
                log.error("创建文件夹失败:{}", downloadDir.getAbsolutePath());
                throw new RuntimeException("创建文件夹失败," + downloadDir.getAbsolutePath());
            }
        }
        File ffmpegConcatFile = new File(downloadDir.getAbsolutePath() + "/ffmpeg_concat.txt");
        if (!ffmpegConcatFile.exists()) {
            if (!ffmpegConcatFile.createNewFile()) {
                log.error("创建ffmpeg合并文件失败:{}", ffmpegConcatFile.getAbsolutePath());
                throw new RuntimeException("创建ffmpeg合并文件失败:" + ffmpegConcatFile.getAbsolutePath());
            }
        }
        AtomicBoolean hasException = new AtomicBoolean(false);
        try (ThreadPoolExecutor threadPool = ThreadPoolHelper.createFixedBlockingVirtualThreadPool(Math.max(threadCount, 1));
             FileOutputStream fileOutputStream = new FileOutputStream(ffmpegConcatFile)) {
            int i = 0;
            for (String tsUrl : m3U8Info.getTsList()) {
                File file = new File(downloadDir.getAbsolutePath() + "/" + (i++) + ".ts");
                fileOutputStream.write(("file '" + file.getAbsolutePath() + "'\n").getBytes());
                fileOutputStream.flush();
                if (file.exists()) {
                    continue;
                }
                log.info("下载ts文件[{}/{}]: {}", i, m3U8Info.getTsList().size(), tsUrl);
                threadPool.execute(() -> {
                    try {
                        downloadTsToFile(tsUrl, file, m3U8Info.isHasKey(), m3U8Info.getKey(), m3U8Info.getIv());
                    } catch (Exception e) {
                        hasException.set(true);
                        throw new RuntimeException(e.getMessage());
                    }
                });
            }
        }
        if (hasException.get()) {
            log.error("下载ts文件存在失败记录，请重试");
            throw new RuntimeException("下载ts文件存在失败记录，请重试");
        }
    }

    public static void downloadTsToFile(String tsUrl, File file, boolean hasKey, byte[] key, byte[] iv) throws Exception {
        int retry = 0;
        while (true) {
            try {
                Files.write(file.toPath(), getTsBytes(tsUrl, hasKey, key, iv));
                return;
            } catch (Exception e) {
                if (retry > 3) {
                    log.error("下载ts文件失败:{}", tsUrl);
                    throw new RuntimeException("下载ts文件失败:" + tsUrl);
                }
                log.error("下载ts文件失败，重试第{}次", ++retry);
            }
        }
    }

    /**
     * 下载ts文件
     *
     * @param tsUrl  ts文件的url
     * @param hasKey 是否有key
     * @param key    key
     * @param iv     iv
     */
    public static byte[] getTsBytes(String tsUrl, boolean hasKey, byte[] key, byte[] iv) throws Exception {
        byte[] bytes = HttpHelper.get(tsUrl).bodyAsBytes();
        if (hasKey) {
            bytes = AESDigest.decode(bytes, key, iv);
        }
        return bytes;
    }

    /**
     * 解析m3u8文件
     *
     * @param url m3u8文件的url
     * @return M3U8信息
     * @throws IOException IO异常
     */
    public static M3U8Info parse(String url) throws IOException {
        log.info("解析m3u8文件:{}", url);
        String[] lines = HttpHelper.get(url).body().split("\n");
        M3U8Info m3U8Info = new M3U8Info();
        for (String line : lines) {
            if (line.startsWith("#EXT-X-KEY")) {
                log.info("当前m3u8文件存在key,{}", line);
                m3U8Info.setHasKey(true);
                String[] split = line.split(",");
                for (String s : split) {
                    if (s.startsWith("URI")) {
                        String keyUrl = s.split("=")[1];
                        m3U8Info.setKey(HttpHelper.get(formatUrl(url, keyUrl)).body().getBytes());
                    }
                    if (s.startsWith("IV")) {
                        String ivStr = s.split("=")[1];
                        // 前16位
                        if (ivStr.length() > 16) {
                            ivStr = ivStr.substring(0, 16);
                        }
                        m3U8Info.setIv(ivStr.getBytes());
                    }
                }
            }
            if (!line.startsWith("#")) {
                m3U8Info.getTsList().add(formatUrl(url, line));
            }
        }
        return m3U8Info;
    }

    /**
     * 格式化url
     *
     * @param url  原始url
     * @param path 路径
     * @return 格式化后的url
     */
    public static String formatUrl(String url, String path) {
        path = path.replaceAll("\"", "");
        if (path.startsWith("http")) {
            return path;
        }
        if (path.startsWith("/")) {
            //根目录
            return url.substring(0, url.indexOf("/", 8)) + path;
        }
        return url.substring(0, url.lastIndexOf("/")) + "/" + path;
    }

}
