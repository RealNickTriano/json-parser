package org.parser;

import org.parser.constants.Constants;
import org.parser.types.ObjectTokenTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntaxAnalysis {

    public static ObjectTokenTuple parseArray(List<Object> tokens) throws Exception {
        List<Object> jsonArray = new ArrayList<>();

        Object t = tokens.get(0);
        if (t.equals(Constants.JSON_RIGHTBRACKET)) {
            return new ObjectTokenTuple(jsonArray, tokens.subList(1, tokens.size()));
        }

        while (true) {
            // parse next token in array
            ObjectTokenTuple objectAndTokens = parse(tokens);
            tokens = objectAndTokens.getTokens();
            // append item to list
            jsonArray.add(objectAndTokens.getObject());

            t = tokens.get(0);

            if (t.equals(Constants.JSON_RIGHTBRACKET)) {
                return new ObjectTokenTuple(jsonArray, tokens.subList(1, tokens.size()));
            } else if (!t.equals(Constants.JSON_COMMA)) {
                throw new Exception("Expected comma after item  in array");
            } else {
                tokens = tokens.subList(1, tokens.size());
            }
        }
    }

    public static ObjectTokenTuple parseObject(List<Object> tokens) throws Exception {
        Map<String, Object> jsonObject = new HashMap<>();

        Object t = tokens.get(0);
        if (t.equals(Constants.JSON_RIGHTBRACE)) {
            // Object is done
            return new ObjectTokenTuple(jsonObject, tokens.subList(1, tokens.size()));
        }

        while (true) {
            Object jsonKey = tokens.get(0);

            if (jsonKey instanceof String) {
                tokens = tokens.subList(1, tokens.size());
            } else {
                throw new Exception(String.format("Expected string key, got: %s", jsonKey));
            }

            if (!tokens.get(0).equals(Constants.JSON_COLON))
                throw new Exception(String.format("Expected colon after key in object, got: %s", t));

            ObjectTokenTuple tuple = parse(tokens.subList(1, tokens.size()));
            tokens = tuple.getTokens();
            Object jsonValue = tuple.getObject();

            jsonObject.put((String) jsonKey, jsonValue);

            t = tokens.get(0);

            if (t.equals(Constants.JSON_RIGHTBRACE)) {
                return new ObjectTokenTuple(jsonObject, tokens.subList(1, tokens.size()));
            } else if (!t.equals(Constants.JSON_COMMA)) {
                throw new Exception(String.format("Expected comma after pair in object, got: %s", t));
            }

            tokens = tokens.subList(1, tokens.size());
        }
    }

    public static ObjectTokenTuple parse(List<Object> tokens) throws Exception {
        Object t = tokens.get(0);

        if (t == Constants.JSON_LEFTBRACKET) {
            return parseArray(tokens.subList(1, tokens.size()));
        } else if (t == Constants.JSON_LEFTBRACE) {
            return parseObject(tokens.subList(1, tokens.size()));
        } else {
            return new ObjectTokenTuple(t, tokens.subList(1, tokens.size()));
        }
    }
}
