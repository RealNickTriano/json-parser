package org.parser.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final List<Character> JSON_WHITESPACE = Arrays.asList(' ', '\t', '\b', '\n', '\r');
    public static final List<Character> JSON_SYNTAX = Arrays.asList('{', '}', '[', ']', ',', ':');
    public static final Character JSON_LEFTBRACKET = '[';
    public static final Character JSON_LEFTBRACE = '{';
    public static final Character JSON_RIGHTBRACKET = ']';
    public static final Character JSON_RIGHTBRACE = '}';
    public static final Character JSON_COMMA = ',';
    public static final Character JSON_COLON = ':';
}
