package pl.gieted.news_fetcher.di;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import pl.gieted.news_fetcher.Application;
import pl.gieted.news_fetcher.ExceptionHandler;
import pl.gieted.news_fetcher.ParametersEncoder;
import pl.gieted.news_fetcher.fetchers.Fetcher;
import pl.gieted.news_fetcher.fetchers.OkHttpFetcher;
import pl.gieted.news_fetcher.fetchers.PlainJavaFetcher;
import pl.gieted.news_fetcher.news.ClassicalNewsService;
import pl.gieted.news_fetcher.news.NewsApi;
import pl.gieted.news_fetcher.news.NewsDatabase;
import pl.gieted.news_fetcher.news.RetrofitNewsService;

import java.nio.file.Path;

public final class ApplicationComponent {
    private final ApplicationModule module = new ApplicationModule();

    private String apiKey() {
        return module.provideApiKey();
    }

    private ParametersEncoder parametersEncoder() {
        return new ParametersEncoder();
    }

    private Gson gson() {
        return module.provideGson();
    }

    private OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    private OkHttpFetcher okHttpFetcher() {
        return new OkHttpFetcher(okHttpClient());
    }

    private PlainJavaFetcher plainJavaFetcher() {
        return new PlainJavaFetcher();
    }

    private Fetcher fetcher() {
        return okHttpFetcher();
    }

    private ClassicalNewsService classicalNewsService() {
        return new ClassicalNewsService(apiKey(), parametersEncoder(), gson(), fetcher()); 
    }

    private RetrofitNewsService retrofitNewsService() {
        return new RetrofitNewsService(apiKey(), gson());
    }

    private NewsApi newsApi() {
        return classicalNewsService();
    }

    private NewsDatabase newsDatabase() {
        return new NewsDatabase(newsDatabasePath());
    }

    private Path newsDatabasePath() {
        return module.provideArticlesFilePath();
    }

    private boolean isDevModeEnabled() {
        return module.provideIsDevModeEnabled();
    }

    private ExceptionHandler exceptionHandler() {
        return new ExceptionHandler(isDevModeEnabled());
    }
    
    public Application application() {
        return new Application(newsApi(), newsDatabase(), exceptionHandler(), apiKey());
    }
}
