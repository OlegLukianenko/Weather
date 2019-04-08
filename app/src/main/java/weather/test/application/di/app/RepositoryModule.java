package weather.test.application.di.app;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import weather.test.application.data.repository.RepositoryImpl;
import weather.test.application.data.retrofit.RepositoryApi;

@Module
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract RepositoryApi provideServerApi(RepositoryImpl repositoryImpl);
}
