package com.capstone.leukovision.retrofit

import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {

    @GET("path/to/your/api")  // Sesuaikan dengan endpoint yang sesuai
    suspend fun getApiResponse(): Response<YourApiResponse>
}
