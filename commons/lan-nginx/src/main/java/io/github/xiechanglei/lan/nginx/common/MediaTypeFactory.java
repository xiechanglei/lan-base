package io.github.xiechanglei.lan.nginx.common;

import io.github.xiechanglei.lan.nginx.DefaultConfig;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 媒体类型字典，根据文件后缀名可以获取对应的媒体类型
 */
public class MediaTypeFactory {

    /**
     * 文件后缀名到媒体类型的映射
     */
    private static final Map<String, String> fileExtensionToMediaTypes = parseMimeTypes();

    /**
     * 解析jar目录下的lan-nginx-mime.types文件，获取所有的mime类型列表，
     * 文件每一行表示一个mime的类型，具体格式如下：
     * text/html					html htm
     * 第一个字符串表示mime的类型，后面的表示文件的后缀名，一个mime类型可以对应多个文件后缀名
     */
    private static Map<String, String> parseMimeTypes() {
        try (InputStream is = MediaTypeFactory.class.getResourceAsStream(DefaultConfig.MIME_RESOURCE_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII))) {
            Map<String, String> result = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty() && !line.startsWith("#")) {
                String[] split = line.split("\\s+");
                for (int i = 1; i < split.length; i++) {
                    result.put(split[i], split[0]);
                }
            }
            return result;
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read " + DefaultConfig.MIME_RESOURCE_PATH);
        }
    }

    /**
     * 根据文件名获取媒体类型
     *
     * @param fileName 文件名
     * @param charset  文件编码
     * @return 媒体类型
     */
    public static String getMediaType(@NonNull String fileName, String charset) {
        String extension = getFileExtension(fileName);
        if (extension != null) {
            String mediaType = fileExtensionToMediaTypes.get(extension);
            if (mediaType != null) {
                if (needCharsetSupport(mediaType)) {
                    return mediaType + ";charset=" + charset;
                }
                return mediaType;
            }
        }
        return DefaultConfig.DEFAULT_MEDIA_TYPE;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getFileExtension(@NonNull String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return fileName.substring(index + 1);
    }

    /**
     * 定义哪些媒体类型需要设置字符集的支持
     *
     * @param mediaType 媒体类型
     * @return 是否需要设置字符集
     */
    public static boolean needCharsetSupport(String mediaType) {
        return mediaType.startsWith("text/") || mediaType.equals("application/javascript") ||
                mediaType.equals("application/json") || mediaType.equals("application/xml");

    }
}
