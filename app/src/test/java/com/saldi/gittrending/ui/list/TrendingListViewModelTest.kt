package com.saldi.gittrending.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.BuiltBy
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.data.repository.TrendingRepository
import com.saldi.gittrending.util.TestUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
class TrendingListViewModelTest {

    private lateinit var trendingRepository: TrendingRepository

    private lateinit var trendingListViewModel: TrendingListViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        trendingRepository = mock(TrendingRepository::class.java)
        trendingListViewModel = TrendingListViewModel(trendingRepository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getPosts_setsTrendingData() =
        runBlockingTest {
            //given
            val trendingList = TestUtil.createTrendingResponse()
            val expectedResponse = ApiResponse.success(trendingList)

            Mockito.`when`(trendingRepository.getTrending("", "", "", false))
                .thenReturn(flowOf(expectedResponse as ApiResponse<*>) as Flow<ApiResponse<List<TrendingListItem>>>?)

            //when
            trendingListViewModel.getPosts("", "", "", false)

            //then
            assert(
                trendingListViewModel?.trendingLiveData?.value?.data?.get(0)?.builtBy?.get(0)?.username == expectedResponse.data?.get(
                    0
                )?.builtBy?.get(0)?.username ?: ""
            )
        }


    @ExperimentalCoroutinesApi
    @Test
    fun getForcePosts_setsTrendingData() =
        runBlockingTest {
            //given
            val builtByList = arrayListOf<BuiltBy>()
            builtByList.add(BuiltBy("", "", "saldi"))

            val trendingListItem =
                TrendingListItem(0, "", "", builtByList, 0, "", 0, "", "", "", 0, "")
            val trendingList = arrayListOf<TrendingListItem>()
            trendingList.add(trendingListItem)

            val expectedResponse = ApiResponse.success(trendingList)

            Mockito.`when`(trendingRepository.getTrending("", "", "", true))
                .thenReturn(flowOf(expectedResponse as ApiResponse<*>) as Flow<ApiResponse<List<TrendingListItem>>>?)

            //when
            trendingListViewModel.getPosts("", "", "", true)

            //then
            assert(
                trendingListViewModel?.trendingLiveData?.value?.data?.get(0)?.builtBy?.get(0)?.username == expectedResponse.data?.get(
                    0
                )?.builtBy?.get(0)?.username ?: ""
            )
        }
}