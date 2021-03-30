package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber


class RefreshDataWorker(private val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val dataBase = getDatabase(appContext.applicationContext)
        val videosRepository = VideosRepository(dataBase)

        try {
            videosRepository.refreshVideos()
            Timber.d("Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

    companion object{
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }

}