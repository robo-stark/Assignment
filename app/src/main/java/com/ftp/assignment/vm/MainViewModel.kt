package com.ftp.assignment.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftp.assignment.model.DataModel
import com.ftp.assignment.repository.Repository
import com.ftp.assignment.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _jsonLiveData: MutableStateFlow<NetworkResult<List<DataModel>>>
            = MutableStateFlow(NetworkResult.Loading())
    val jsonLiveData = _jsonLiveData.asStateFlow()

    init {
        fetchServerData()
    }

    fun fetchServerData() {
        _jsonLiveData.value = NetworkResult.Loading()
        try {
            viewModelScope.launch {
                repository.fetchData()
                    .catch { e ->
                        _jsonLiveData.value = NetworkResult.Error(e.message.toString())
                    }
                    .collect {
                        _jsonLiveData.value = NetworkResult.Success(it.body()!!)
                    }
            }
        }catch ( e : Exception) {
            _jsonLiveData.value = NetworkResult.Error(e.message.toString())
        }
    }

}