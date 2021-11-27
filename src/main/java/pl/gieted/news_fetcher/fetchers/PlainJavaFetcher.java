package pl.gieted.news_fetcher.fetchers;

import org.jetbrains.annotations.NotNull;
import pl.gieted.news_fetcher.utils.BufferedReaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public final class PlainJavaFetcher implements Fetcher {

    @Override
    public String fetch(@NotNull String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            return BufferedReaderUtils.readText(bufferedReader);
        }
    }
}
