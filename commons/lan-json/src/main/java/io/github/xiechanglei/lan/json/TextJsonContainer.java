package io.github.xiechanglei.lan.json;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextJsonContainer extends JsonContainerAdapter {
    private final String body;

    public String body() {
        return body;
    }
    
}
