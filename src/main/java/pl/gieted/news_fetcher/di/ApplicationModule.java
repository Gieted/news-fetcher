package pl.gieted.news_fetcher.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import pl.gieted.news_fetcher.news.NewsApi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ApplicationModule {
    
    public Gson provideGson() {
        RuntimeTypeAdapterFactory<NewsApi.Response> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(NewsApi.Response.class, "status")
                .registerSubtype(NewsApi.OkResponse.class, "ok")
                .registerSubtype(NewsApi.ErrorResponse.class, "error");

        return new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .create();
    }

    public String provideApiKey() {
        String apiKey = System.getenv("API_KEY");
        
        if (apiKey == null) {
            System.err.println("Please provide api key");
            System.exit(101);
        }
        
        return apiKey;
    }

    public Path provideArticlesFilePath() {
        return Paths.get(".", "articles.txt");
    }

    public boolean provideIsDevModeEnabled() {
        String devModeString = System.getenv("DEV_MODE");

        if (devModeString == null) {
            return false;
        } else {
            return devModeString.equalsIgnoreCase("TRUE");
        }
    }
}
