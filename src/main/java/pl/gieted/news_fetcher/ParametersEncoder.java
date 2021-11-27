package pl.gieted.news_fetcher;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class ParametersEncoder {
    public String encode(@NotNull Map<@NotNull String, @Nullable String> parameters) {
        if (parameters.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder("?");

        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            result.append(parameter.getKey());
            if (parameter.getValue() != null) {
                result.append("=").append(parameter.getValue());
            }
            result.append("&");
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }
}
