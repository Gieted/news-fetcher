package pl.gieted.news_fetcher;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ExceptionHandler {
    private final boolean isDevModeEnabled;

    public ExceptionHandler(boolean isDevModeEnabled) {
        this.isDevModeEnabled = isDevModeEnabled;
    }

    public void onDatabaseFileInaccessible(IOException e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.printf("The file cannot be saved: %s.\n", e.getMessage());
        }
    }

    public void onUnknownHost(UnknownHostException e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("Check your internet connection.");
        }
    }

    public void onConnectionTimeout(SocketTimeoutException e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("The NewsAPI is down or you don't have an internet connection.");
        }
    }

    public void onUnknownExceptionWhileFetchingArticles(Exception e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("An unknown error have happened while trying to download the articles.");
        }
    }

    public void onUnknownExceptionWhileSavingArticles(Exception e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("An unknown error have happened while trying to save the articles.");
        }
    }

    public void onNewsApiError(String message) {
        System.err.println("NewsAPI have returned an error:");
        System.err.println(message);
    }
}
