package pl.gieted.news_fetcher.news;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;

public class RetrofitNewsService implements NewsApi {


    public interface Service {

        @GET("top-headlines")
        Call<Response> fetchTopHeadlines(@Query("country") NewsApi.Country country,
                                         @Query("category") NewsApi.Category category,
                                         @Query("apiKey") String apiKey) throws IOException;
    }

    private final Service service;
    private final String apiKey;

    public RetrofitNewsService(@NotNull String apiKey, @NotNull Gson gson) {
        this.apiKey = apiKey;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        
        this.service = retrofit.create(Service.class);
    }

    @Override
    public Response fetchTopHeadlines(@NotNull Country country, @NotNull Category category) throws IOException {
        retrofit2.Response<Response> retrofitResponse = service.fetchTopHeadlines(country, category, apiKey).execute();
        return retrofitResponse.body();
    }
}
