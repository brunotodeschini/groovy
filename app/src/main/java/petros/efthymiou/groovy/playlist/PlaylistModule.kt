package petros.efthymiou.groovy.playlist

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.details.PlaylistDetailAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playlistAPI(retrofit: Retrofit): PlaylistAPI = retrofit.create(PlaylistAPI::class.java)

    @Provides
    fun retrofit() = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/") //Always analyze local ip address
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun playlistDetailsAPI(
        retrofit: Retrofit
    ): PlaylistDetailAPI = retrofit.create(PlaylistDetailAPI::class.java)


}