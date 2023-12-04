package org.parser;
import java.util.HashMap;
import java.util.Map;
public class Main {
    public static void main(String[] args) throws IllegalArgumentException {

        System.out.println("---- Lex Number Test ----");
        System.out.println(Lexer.lexNumber("4124"));
        System.out.println(Lexer.lexNumber("41.24"));
        System.out.println(Lexer.lexNumber("-4124.42342342"));
        System.out.println(Lexer.lexNumber("6.0220943E+23"));
        System.out.println(Lexer.lexNumber("6.25E-27"));
        System.out.println(Lexer.lexNumber("62541E3"));

        // Test Bool
        System.out.println("---- Lex Boolean Test ----");
        System.out.println(Lexer.lexBool("true"));
        System.out.println(Lexer.lexBool("false"));
        System.out.println(Lexer.lexBool("falsedadw"));
        System.out.println(Lexer.lexBool("True"));

        // Test Null
        System.out.println("---- Lex Null Test ----");
        System.out.println(Lexer.lexNull("null"));
        System.out.println(Lexer.lexNull("null das wdwqdd213"));
        System.out.println(Lexer.lexNull("Null"));
        System.out.println(Lexer.lexNull("Dasdwfd dadw dwa"));

        // Test Lex
        System.out.println("---- Test Lex ----");
        System.out.println(Lexer.lex("{\"foo\":[1,2,\"three\"]}"));

    }

    public static <V> Map<String, V> parseJSON(String json) throws Exception {
        Lexer.lex(json);
        return new HashMap();
    }

    static class lexNumberReturn<T> {
        private T number;
        private String string;

        public lexNumberReturn(T number, String string) {
            this.number = number;
            this.string = string;
        }

        public String toString() {
            return this.number + " " + this.string + this.number.getClass().getName();
        }
    }
}
