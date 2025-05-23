package guru.qa.api;

import guru.qa.config.Config;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Arrays;

public abstract class RestService {

    protected static final Config config = Config.getInstance();
    protected final OkHttpClient httpClient;
    protected final Retrofit retrofit;

    public RestService(String baseUrl) {
        this(baseUrl, false);
    }

    public RestService(String baseUrl, boolean followRedirect) {
        this(baseUrl, followRedirect, (Interceptor) null);
    }

    public RestService(String baseUrl, boolean followRedirect, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AllureOkHttp3())
                .followRedirects(followRedirect);

        if (interceptors != null) {
            Arrays.stream(interceptors).forEach(builder::addNetworkInterceptor);
        }
        builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        this.httpClient = builder.build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
