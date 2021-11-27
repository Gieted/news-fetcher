package pl.gieted.news_fetcher;

import org.jetbrains.annotations.NotNull;
import pl.gieted.news_fetcher.di.ApplicationComponent;
import pl.gieted.news_fetcher.news.NewsApi;
import pl.gieted.news_fetcher.news.NewsDatabase;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public final class Application {
    
    public static void main(String[] args) {
        ApplicationComponent component = new ApplicationComponent();
        Application application = component.application();

        application.downloadAndSaveArticles();
    }

    private final NewsApi newsApi;
    private final NewsDatabase newsDatabase;
    private final ExceptionHandler exceptionHandler;

    public Application(@NotNull NewsApi newsApi,
                       @NotNull NewsDatabase newsDatabase,
                       @NotNull ExceptionHandler exceptionHandler) {

        this.newsApi = newsApi;
        this.newsDatabase = newsDatabase;
        this.exceptionHandler = exceptionHandler;
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
