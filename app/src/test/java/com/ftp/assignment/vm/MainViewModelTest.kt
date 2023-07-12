package com.ftp.assignment.vm

import app.cash.turbine.test
import com.ftp.assignment.model.DataModel
import com.ftp.assignment.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import retrofit2.Response

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_expectedResultEmptyData() = runTest {
        Mockito.`when`(repository.fetchData()).thenReturn(
            flowOf(Response.success(emptyList()))
        )
        val sut = MainViewModel(repository)
        sut.fetchServerData()
        testDispatcher.scheduler.advanceUntilIdle()

        sut.jsonLiveData.test {
            val res = awaitItem()
            assertEquals(0, res.data!!.size)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun test_expectedValidData() = runTest {
        val response = Response.success(
            listOf(
                DataModel(1, 1, "title_one", "", ""),
                DataModel(1, 2, "title_two", "", ""),
                DataModel(1, 3, "title_three", "", "")
            )
        )
        Mockito.`when`(repository.fetchData()).thenReturn(
            flowOf(response)
        )
        val sut = MainViewModel(repository)
        sut.fetchServerData()
        testDispatcher.scheduler.advanceUntilIdle()

        sut.jsonLiveData.test {
            val res = awaitItem()
            assertEquals("title_two", res.data!![1].title)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}