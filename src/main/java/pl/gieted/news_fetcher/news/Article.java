package pl.gieted.news_fetcher.news;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class Article {
    private final String title;
    private final String author;
    private final String description;

    public Article(@NotNull String title, @Nullable String author, @Nullable String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Optional<String> getAuthor() {
        return Optional.ofNullable(author);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }
}
