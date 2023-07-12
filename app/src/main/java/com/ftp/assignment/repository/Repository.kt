package com.ftp.assignment.repository


import com.ftp.assignment.remote.ApiService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Repository(
    private val apiService: ApiService
) {

    fun fetchData() = flow {
        emit(apiService.getData())
    }
}