package pl.gieted.news_fetcher.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import pl.gieted.news_fetcher.news.NewsApi;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class ApplicationModule {
    
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
        return  System.getenv("API_KEY");
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
