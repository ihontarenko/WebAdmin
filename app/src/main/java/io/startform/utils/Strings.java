package io.startform.utils;

public class Strings {

    public static String substringBetween(String start, String last, String source) {
        source = source.substring(source.indexOf(start));

        return source.substring(0, source.lastIndexOf(last));
    }

}
