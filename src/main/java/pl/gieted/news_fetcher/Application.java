package pl.gieted.news_fetcher;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.gieted.news_fetcher.di.ApplicationComponent;
import pl.gieted.news_fetcher.news.NewsApi;
import pl.gieted.news_fetcher.news.NewsDatabase;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public final class Application {

    public static void main(String[] args) {
        String articlesPath = null;
        if (args.length >= 1) {
            articlesPath = args[0];
        }

        ApplicationComponent component = new ApplicationComponent(articlesPath);
        Application application = component.application();

        if (articlesPath != null) {
            application.validateArticlesPath(articlesPath);
        }
        application.ensureApiKeyIsPresent();
        application.downloadAndSaveArticles();
    }

    private final NewsApi newsApi;
    private final NewsDatabase newsDatabase;
    private final ExceptionHandler exceptionHandler;
    private final String apiKey;

    public Application(@NotNull NewsApi newsApi,
                       @NotNull NewsDatabase newsDatabase,
                       @NotNull ExceptionHandler exceptionHandler,
                       @Nullable String apiKey) {

        this.newsApi = newsApi;
        this.newsDatabase = newsDatabase;
        this.exceptionHandler = exceptionHandler;
        this.apiKey = apiKey;
    }

    public void ensureApiKeyIsPresent() {
        if (apiKey == null) {
            exceptionHandler.onApiKeyAbsent();
        }
    }

    public void validateArticlesPath(@NotNull String path) {
        try {
            Paths.get(path);
            File file = new File(path);
            if (file.isDirectory()) {
                exceptionHandler.onArticlesPathIsDirectory(path);
            }
        } catch (InvalidPathException e) {
            exceptionHandler.onInvalidArticlesPath(path);
        }
    }

    public void downloadAndSaveArticles() {
        NewsApi.Response response;
        try {
            response = newsApi.fetchTopHeadlines(NewsApi.Country.PL, NewsApi.Category.BUSINESS);
        } catch (UnknownHostException e) {
            exceptionHandler.onUnknownHost(e);
            return;
        } catch (SocketTimeoutException e) {
            exceptionHandler.onConnectionTimeout(e);
            return;
        } catch (Exception e) {
            exceptionHandler.onUnknownExceptionWhileFetchingArticles(e);
            return;
        }

        if (response instanceof NewsApi.ErrorResponse) {
            NewsApi.ErrorResponse errorResponse = (NewsApi.ErrorResponse) response;
            exceptionHandler.onNewsApiError(errorResponse.getMessage());

            return;
        }

        try {
            newsDatabase.saveArticles(((NewsApi.OkResponse) response).getArticles());
        } catch (IOException e) {
            exceptionHandler.onDatabaseFileInaccessible(e);
        } catch (Exception e) {
            exceptionHandler.onUnknownExceptionWhileSavingArticles(e);
        }
    }
}
