package org.parser.types;

import java.util.List;

public class ArrayTokenReturnType {
    private final List<Object> list;
    private final List<Object> tokens;

    public ArrayTokenReturnType(List<Object> list, List<Object> tokens) {
        this.list = list;
        this.tokens = tokens;
    }
}
