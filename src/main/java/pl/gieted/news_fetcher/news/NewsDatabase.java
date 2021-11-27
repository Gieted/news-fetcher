package pl.gieted.news_fetcher.news;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class NewsDatabase {
    private final Path path;

    public NewsDatabase(@NotNull Path path) {
        this.path = path;
    }

    public void saveArticles(@NotNull List<Article> articles) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        try (BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {
            for (Article article : articles) {
                writer.write(article.getTitle() + ":" + article.getDescription().orElse("") + ":" + article.getAuthor().orElse("") + "\n");
            }
        }
    }
}
