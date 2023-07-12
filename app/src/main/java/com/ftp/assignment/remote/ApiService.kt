package com.ftp.assignment.remote

import com.ftp.assignment.model.DataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    suspend fun getData() : Response<List<DataModel>>
}