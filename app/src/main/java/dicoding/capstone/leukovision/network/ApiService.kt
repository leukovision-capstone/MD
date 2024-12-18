package dicoding.capstone.leukovision.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("detect")
    suspend fun detectImage(
        @Part image: MultipartBody.Part
    ): okhttp3.ResponseBody

    @Multipart
    @POST("analyze")
    suspend fun analyzeImage(
        @Part image: MultipartBody.Part
    ): ApiResponse
}

data class ApiResponse(
    val status: String,
    val data: ApiData
)

data class ApiData(
    val predicted_class: String,
    val confidence: Float
)
