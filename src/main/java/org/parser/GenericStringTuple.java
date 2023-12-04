package org.parser;

public class GenericStringTuple<T> {
    private final T first;
    private final String second;

    public GenericStringTuple(T first, String second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }

    public String getSecond() { return second; }

    public String toString() {
        if (this.first != null)
            return "(" + "(" + first.getClass().getName() + ") " + first + ", " + second + ")";
        else
            return "(" + "(" + null + ") " + null + ", " + second + ")";
    }
}
