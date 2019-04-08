package weather.test.application.di.app;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.test.application.data.retrofit.ServerApi;

@Module
public class DataModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient() {
        return new  OkHttpClient.Builder()
                .hostnameVerifier(((hostname, session) -> true))
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public ServerApi getServerApi(Retrofit retrofit) {
        return retrofit.create(ServerApi.class);
    }

}