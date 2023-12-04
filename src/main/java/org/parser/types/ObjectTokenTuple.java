package org.parser.types;

import java.util.List;

public class ObjectTokenTuple {
    private final Object object;
    private final List<Object> tokens;

    public ObjectTokenTuple(Object object, List<Object> tokens) {
        this.object = object;
        this.tokens = tokens;
    }
    public Object getObject() {
        return this.object;
    }
    public List<Object> getTokens() {
        return tokens;
    }
}
