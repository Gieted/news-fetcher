package pl.gieted.news_fetcher.utils;

import java.io.BufferedReader;
import java.util.stream.Collectors;

public final class BufferedReaderUtils {
    private BufferedReaderUtils() {}

    public static String readText(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining());
    }
}
