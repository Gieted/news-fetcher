package pl.gieted.news_fetcher.fetchers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public final class OkHttpFetcher implements Fetcher {

    private final OkHttpClient client;

    public OkHttpFetcher(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public String fetch(@NotNull String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }
}
