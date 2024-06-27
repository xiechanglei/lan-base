package io.github.xiechanglei.lan.base.json;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextJsonContainer extends JsonContainerAdapter {
    private final String body;

    public String body() {
        return body;
    }
    
}
