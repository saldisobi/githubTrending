package com.saldi.gittrending.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import com.saldi.gittrending.data.db.TinyDB
import com.saldi.gittrending.data.db.TrendingDao
import com.saldi.gittrending.data.model.TrendingListItem
import com.saldi.gittrending.data.network.GitHubService
import com.saldi.gittrending.data.utils.NetworkUtils
import com.saldi.gittrending.ui.list.TrendingListViewModel
import com.saldi.gittrending.util.TestUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Response

/**
 * need help not working as of now
 */
@RunWith(JUnit4::class)
class TrendingRepositoryTest {

    private lateinit var trendingDao: TrendingDao

    private lateinit var gitHubService: GitHubService

    private lateinit var tinyDb: TinyDB

    private lateinit var repo: TrendingRepository

    private lateinit var networkUtils: NetworkUtils


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        trendingDao = mock(TrendingDao::class.java)
        gitHubService = mock(GitHubService::class.java)
        tinyDb = mock(TinyDB::class.java)
        networkUtils = mock(NetworkUtils::class.java)
        repo = TrendingRepository(gitHubService, trendingDao, tinyDb)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
    }


    @Test
    fun getTrending() {
        repo.getTrending("", "", "", false)
        verify(trendingDao).getAllTrending()
    }

    @Test
    fun isUpdateRequired() {
    }

    @Test
    fun goToNetwork() {
        runBlocking {
            val dbData = MutableLiveData<List<TrendingListItem>>().asFlow()

            `when`(trendingDao.getAllTrending()).thenReturn(dbData)

            `when`(gitHubService.getTrendingRepositories("", "", "")).thenReturn(
                Response.success(
                    TestUtil.createTrendingResponse()
                )
            )



            verify(gitHubService, never()).getTrendingRepositories("", "", "")

            val updatedDbData = MutableLiveData<List<TrendingListItem>>()

            `when`(trendingDao.getAllTrending()).thenReturn(updatedDbData.asFlow())

            // dbData.value = null

            // verify(githubService).getUser("foo")
        }
    }

    @Test
    fun dontGoToNetwork() {
        /* val dbData = MutableLiveData<User>()
         val user = TestUtil.createUser("foo")
         dbData.value = user
         `when`(userDao?.findByLogin("foo")).thenReturn(dbData)
         val observer = mock<Observer<Resource<User>>>()
         repo.loadUser("foo").observeForever(observer)
         verify(githubService, never()).getUser("foo")
         verify(observer).onChanged(Resource.success(user))*/
    }
}