package io.github.xiechanglei.lan.http;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * M3U8信息
 *
 * @author xie
 * @date 2024/9/2
 */
@Getter
@Setter
public class M3U8Info {
    private boolean hasKey;
    private byte[] key;
    private byte[] iv;
    private List<String> tsList = new ArrayList<>();
}
