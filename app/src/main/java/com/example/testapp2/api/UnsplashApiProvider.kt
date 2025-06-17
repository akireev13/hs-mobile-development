import com.example.testapp2.data.SearchResponse
import com.example.testapp2.data.UnsplashItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val ACCESS_KEY = "Hy6iXm_4xiOVbqyHH_DOEiXF84j97eQJ4MsH8HuNmx8"
private const val AuthHeader = "Authorization: Client-ID $ACCESS_KEY"
interface UnsplashApi {
    @Headers(AuthHeader)
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashItem>>

    @Headers(AuthHeader)
    @GET("/search/photos")
    fun searchPhotos(@Query ("query") query: String) : Call<SearchResponse>
}