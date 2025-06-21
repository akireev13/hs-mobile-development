import com.example.testapp2.data.unsplash.UnsplashDetailedItem

interface UnsplashDetailsResult {
    fun onSuccessFetchImageDetails(image: UnsplashDetailedItem)

    fun onFailFetchImageDetails()
}