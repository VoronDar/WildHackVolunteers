package com.astery.wildhack.remoteStorage

import com.astery.wildhack.model.*
import com.astery.wildhackvolunteers.model.Person
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class RemoteDataStorage @Inject constructor(
    private val retrofit: RetrofitInstance
) {

    @kotlin.jvm.Throws(RemoteWrongArgumentException::class, RemoteUnexpectedException::class)
    suspend fun auth(user: UserAccess): UserToken {
        if (user.password.equals("pvision") && user.username.equals("pvision"))
            return UserToken("aaa", "aaa", 1121)
        else throw RemoteWrongArgumentException()
        try {
            return retrofit.api.login(user)
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 422 || e.code() == 401) {
                throw RemoteWrongArgumentException()
            }
            throw catchException(e)
        }
    }

    suspend fun updateToken(refreshToken: String): UserToken {
        return UserToken("aaa", "aaa", 1121)
        try {
            return retrofit.api.refreshToken(RefreshToken(refreshToken))
        } catch (e: retrofit2.HttpException) {
            Timber.w("update token: failure response ${e.localizedMessage}")
            if (e.code() == 422 || e.code() == 401) {
                throw RemoteWrongArgumentException()
            }
            throw catchException(e)
        }
    }

    suspend fun getPersonForStage(stage: Int, token: String): ArrayList<Person> {
        return arrayListOf()
        try {
            return retrofit.api.getCardsForStage(stage, getBearerToken(token))
        } catch (e: retrofit2.HttpException) {
            throw catchException(e)
        }
    }

    suspend fun changePersonStage(stage: Int, personId: Int, token: String) {
        try {
            retrofit.api.changeCardStage(stage, personId, token)
        } catch (e: retrofit2.HttpException) {
            throw catchException(e)
        }
    }


    private fun catchException(e: retrofit2.HttpException): java.lang.Exception {
        if (e.code() == 401) return RemoteAccessDeniedException()
        return RemoteUnexpectedException()
    }

    private fun getBearerToken(token: String): String {
        return "Bearer $token"
    }

}

class RemoteUnexpectedException : Exception()
class RemoteWrongArgumentException : Exception()
class RemoteAccessDeniedException : Exception()