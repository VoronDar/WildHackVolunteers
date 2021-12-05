package com.astery.wildhack.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.astery.wildhackvolunteers.localStorage.LocalStorage
import com.astery.wildhackvolunteers.localStorage.ValueNotFoundException
import com.astery.wildhack.model.Answer
import com.astery.wildhackvolunteers.model.Person
import com.astery.wildhack.remoteStorage.RemoteDataStorage
import com.astery.wildhackvolunteers.model.TaskId
import com.astery.wildhackvolunteers.ui.fragments.main.State
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject


open class Repository @Inject constructor(
    private val remoteStorage: RemoteDataStorage,
    private val localStorage: LocalStorage,
    @ApplicationContext var context: Context
) {
    private suspend fun getToken():String{
        return try{
            localStorage.getToken(Date())
        } catch (e: ValueNotFoundException){
            Timber.d("there is no token in cache")
            getUpdatedToken()
        }
    }

    private suspend fun getUpdatedToken():String{
            // there might be an exception
            Timber.d("refresh token ${localStorage.getRefreshToken()}")
            val userToken = remoteStorage.updateToken(localStorage.getRefreshToken())
            localStorage.setRefreshToken(userToken.refreshToken)
            localStorage.setToken(userToken.accessToken, userToken.expiresIn + Date().time)
            return userToken.accessToken
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Timber.tag("Internet").i("NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Timber.tag("Internet").i("NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Timber.tag("Internet").i("NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }

        }
        return false
    }

    fun getPerson(): Person? {
        return localStorage.getPerson()
    }

    fun savePersonToLocal(value: Person?) {
        localStorage.savePerson(value)
    }

    fun getMadeSubtaskForTask(profile: TaskId): Int {
        return localStorage.getMadeSubTasks(profile)
    }

    fun getState(): State {
        return localStorage.getState()
    }
    fun setState(state:State){
        localStorage.setState(state)
    }

    suspend fun getSavedAnswers():ArrayList<Answer>{
        val list = ArrayList<Answer>()
        val l = localStorage.getSavedAnswers()
        for (i in l){
            list.add(i)
        }
        return list
    }

    suspend fun saveAnswer(save:Boolean, answer: Answer){
        answer.saved = save
        localStorage.updateAnswer(answer)
    }

    suspend fun fillAnswers(){
        localStorage.fillAnswers()
    }
    suspend fun getAnswers(string:String):ArrayList<Answer>{
        val list = ArrayList<Answer>()
        val l = localStorage.getAnswers(string)
        for (i in l){
            list.add(i)
        }
        return list
    }
    suspend fun getAllAnswers():List<Answer>{
        return localStorage.getAnswers()
    }
}