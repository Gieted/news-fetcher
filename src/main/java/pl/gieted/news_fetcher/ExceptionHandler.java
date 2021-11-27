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
        System.exit(1);
    }

    public void onUnknownHost(UnknownHostException e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("Check your internet connection.");
        }
        System.exit(1);
    }

    public void onConnectionTimeout(SocketTimeoutException e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("The NewsAPI is down or you don't have an internet connection.");
        }
        System.exit(1);
    }

    public void onUnknownExceptionWhileFetchingArticles(Exception e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("An unknown error have happened while trying to download the articles.");
        }
        System.exit(1);
    }

    public void onUnknownExceptionWhileSavingArticles(Exception e) {
        if (isDevModeEnabled) {
            e.printStackTrace();
        } else {
            System.err.println("An unknown error have happened while trying to save the articles.");
        }
        System.exit(1);
    }

    public void onNewsApiError(String message) {
        System.err.println("NewsAPI have returned an error:");
        System.err.println(message);
        System.exit(1);
    }

    public void onApiKeyAbsent() {
        System.err.println("Please provide an api key");
        System.exit(1);
    }
}
