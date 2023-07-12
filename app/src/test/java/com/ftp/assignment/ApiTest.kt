package com.ftp.assignment

import com.ftp.assignment.remote.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ApiTest {

    lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun test_expectedEmptyArray() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getData()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.isEmpty())
    }

    @Test
    fun test_expectedJsonData() = runTest {
        val mockResponse = MockResponse()
        val jsonData = JsonHelper.readFileResource("/dummy.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(jsonData)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getData()
        mockWebServer.takeRequest()

        Assert.assertEquals(2, response.body()!!.size)
    }

    @Test
    fun test_expectedError() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong!!")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getData()
        mockWebServer.takeRequest()

        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown() {

    }

}