package pl.gieted.news_fetcher.news;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import pl.gieted.news_fetcher.ParametersEncoder;
import pl.gieted.news_fetcher.fetchers.Fetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ClassicalNewsService implements NewsApi {

    private final String apiKey;
    private final ParametersEncoder parametersEncoder;
    private final Gson gson;
    private final Fetcher fetcher;

    public ClassicalNewsService(@NotNull String apiKey, @NotNull ParametersEncoder parametersEncoder, @NotNull Gson gson, @NotNull Fetcher fetcher) {
        this.apiKey = apiKey;
        this.parametersEncoder = parametersEncoder;
        this.gson = gson;
        this.fetcher = fetcher;
    }

    @Override
    public Response fetchTopHeadlines(@NotNull Country country, @NotNull Category category) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("apiKey", apiKey);
        parameters.put("country", country.toString());
        parameters.put("category", category.toString());

        String responseData = fetcher.fetch(BASE_URL + "top-headlines" + parametersEncoder.encode(parameters));
        
        return gson.fromJson(responseData, Response.class);
    }
}
