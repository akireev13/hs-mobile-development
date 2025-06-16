import com.example.testapp2.data.UnsplashItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashApi {
    @Headers("Authorization: Client-ID Hy6iXm_4xiOVbqyHH_DOEiXF84j97eQJ4MsH8HuNmx8")
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashItem>>
}