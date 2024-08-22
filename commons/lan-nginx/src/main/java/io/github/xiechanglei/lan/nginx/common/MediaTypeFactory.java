package io.github.xiechanglei.lan.nginx.common;

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
    private static final Map<String, String> fileExtensionToMediaTypes = parseMimeTypes();

    private static Map<String, String> parseMimeTypes() {
        try (InputStream is = MediaTypeFactory.class.getResourceAsStream("/lan-nginx-mime.types");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII))) {
            Map<String, String> result = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || line.charAt(0) == '#') {
                    continue;
                }
                String[] split = line.split("\\s+");
                if (split.length < 2) {
                    continue;
                }
                for (int i = 1; i < split.length; i++) {
                    result.put(split[i], split[0]);
                }

            }
            return result;
        } catch (IOException var10) {
            throw new IllegalStateException("Could not read lan-nginx-mime.types");
        }
    }

    /**
     * 根据文件名获取媒体类型
     *
     * @param fileName 文件名
     * @return 媒体类型
     */
    public static String getMediaType(@NonNull String fileName) {
        String extension = getFileExtension(fileName);
        if (extension == null) {
            return null;
        }
        return fileExtensionToMediaTypes.get(extension);
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


}
