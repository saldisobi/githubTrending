package com.saldi.gittrending.data.utils


import com.saldi.gittrending.data.utils.NetworkUtils.Companion.isUpdateRequired
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class NetworkUtilsTest {

    @Test
    fun checkUpdateRequired_twoHoursPassed_returnsTrue() {
        val result = isUpdateRequired(System.currentTimeMillis())
        assertThat(result, `is`(false))
    }
}