package pl.gieted.news_fetcher.news;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public interface NewsApi {
    String BASE_URL = "https://newsapi.org/v2/";
    
    abstract class Response { }

    final class OkResponse extends Response {
        private final int totalResults;
        private final List<Article> articles;

        public OkResponse(int totalResults, @NotNull List<Article> articles) {
            this.totalResults = totalResults;
            this.articles = articles;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public List<Article> getArticles() {
            return articles;
        }
    }

    final class ErrorResponse extends Response {
        private final String code;
        private final String message;

        public ErrorResponse(@NotNull String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    enum Country {
        PL;

        @Override
        public String toString() {
            return "pl";
        }
    }

    enum Category {
        BUSINESS;

        @Override
        public String toString() {
            return "business";
        }
    }


    Response fetchTopHeadlines(@NotNull Country country, @NotNull Category category) throws IOException;
}
