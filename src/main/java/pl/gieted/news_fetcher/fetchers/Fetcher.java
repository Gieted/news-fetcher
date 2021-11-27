package pl.gieted.news_fetcher.fetchers;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface Fetcher {
    String fetch(@NotNull String url) throws IOException;
}
