package com.sophia.movie_api_test_2

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {
    //Pattern
    companion object {
        private val instance: AppExecutors = AppExecutors()

        fun getInstance(): AppExecutors {
            return instance
        }
    }

    private val mNetWorkIO: ScheduledExecutorService = Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService = mNetWorkIO

}