package org.parser;

import org.parser.constants.Constants;

import java.util.*;

public class Lexer {

    public static GenericStringTuple<String> lexString(String str) throws IllegalArgumentException {
        if (str.charAt(0) != '"') {
            return new GenericStringTuple<>(null, str);
        }

        StringBuilder stringToReturn = new StringBuilder();
        for(int i = 1; i < str.length(); i++) {
            char character = str.charAt(i);
            if (character == '"') {
                // completed string, return as string
                return new GenericStringTuple<>(stringToReturn.toString(), str.substring(i + 1));
            } else {
                stringToReturn.append(str.charAt(i));
            }

        }

        throw new IllegalArgumentException("Unexpected end of string: Missing ending quotation");

    }

    public static GenericStringTuple<? extends Number> lexNumber(String str) {
        StringBuilder number = new StringBuilder();
        Set<Integer> numbersSet = new HashSet(Arrays.asList(
                '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                '+', '-', 'e', 'E', '.'));

        boolean isInt = true;
        boolean isDouble = false;

        int i;
        for(i = 0; i < str.length(); i++) {
            if (numbersSet.contains(str.charAt(i))) {
                if (str.charAt(i) == '.' || str.charAt(i) == 'e' || str.charAt(i) == 'E') {
                    isInt = false;
                    isDouble = true;
                }
                number.append(str.charAt(i));
            } else {
                break;
            }
        }

        String rest = str.substring(i);

        if (number.isEmpty()) return new GenericStringTuple<>(null, str);

        if (isInt)
            return new GenericStringTuple<>(Integer.parseInt(number.toString()), rest);
        if (isDouble)
            return new GenericStringTuple<>(Double.parseDouble(number.toString()), rest);

        // Never reached
        return null;

    }

    public static GenericStringTuple<Boolean> lexBool(String str) {
        if (str.length() >= 4 && str.substring(0, 4).equals("true")) {
            return new GenericStringTuple<>(true, str.substring(4));
        } else if (str.length() >= 5 && str.substring(0, 5).equals("false")) {
            return new GenericStringTuple<>(false, str.substring(5));
        }

        return new GenericStringTuple<>(null, str);
    }

    public static GenericStringTuple<Boolean> lexNull(String str) {
        if (str.length() >= 4 && str.substring(0, 4).equals("null")) {
            return new GenericStringTuple<>(true, str.substring(4));
        }

        return new GenericStringTuple<>(null, str);
    }

    public static List<Object> lex(String str) throws IllegalArgumentException {

        List<Object> tokens = new ArrayList<>();

        while(!str.isEmpty()) {
            // Lex String
            GenericStringTuple<String> stringTuple = lexString(str);
            String jsonString = stringTuple.getFirst() == null ? "" : stringTuple.getFirst();
            str = stringTuple.getSecond();
            if (!jsonString.isEmpty()) {
                tokens.add(jsonString);
                continue;
            }

            // Lex Number
            GenericStringTuple<? extends Number> numTuple = lexNumber(str);
            str = numTuple.getSecond();
            if (numTuple.getFirst() != null) {
                tokens.add(numTuple.getFirst());
                continue;
            }

            // Lex Boolean
            GenericStringTuple<Boolean> boolTuple = lexBool(str);
            str = boolTuple.getSecond();
            if (boolTuple.getFirst() != null) {
                tokens.add(boolTuple.getFirst());
                continue;
            }

            // Lex Null
            GenericStringTuple<Boolean> nullTuple = lexNull(str);
            str = nullTuple.getSecond();
            if (nullTuple.getFirst() != null) {
                tokens.add(nullTuple.getFirst());
                continue;
            }

            if (Constants.JSON_WHITESPACE.contains(str.charAt(0))) {
                str = str.substring(1);
            } else if (Constants.JSON_SYNTAX.contains(str.charAt(0))) {
                tokens.add(str.charAt(0));
                str = str.substring(1);
            } else {
                throw new IllegalArgumentException(
                        String.format("Unexpected Identifier: %s", str.charAt(0))
                );
            }
        }

        return tokens;
    }
}
