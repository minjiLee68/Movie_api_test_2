package com.sophia.movie_api_test_2

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {
    //Pattern
    companion object {
        private lateinit var instance: AppExecutors

        fun getInstance(): AppExecutors {
            if (instance == null) {
                instance = AppExecutors()
            }
            return instance
        }
    }

    private val mNetWorkIO: ScheduledExecutorService = Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService = mNetWorkIO

}