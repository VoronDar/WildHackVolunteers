package com.astery.wildhack.remoteStorage

import com.astery.wildhack.model.*
import com.astery.wildhackvolunteers.model.Person
import retrofit2.http.*

interface ApiService {
        @Headers("Content-Type: application/json", "accept: application/json")
        @POST("auth/login")
        suspend fun login(@Body user:UserAccess): UserToken

        @Headers("Content-Type: application/json", "accept: application/json")
        @POST("auth/refresh")
        suspend fun refreshToken(@Body refreshToken: RefreshToken): UserToken

        @Headers("Content-Type: application/json", "accept: application/json")
        @GET("cards/")
        suspend fun getCardsForStage(stage:Int, @Header("Authorization") token:String): ArrayList<Person>

        @Headers("Content-Type: application/json", "accept: application/json")
        @PUT("cardStage/")
        suspend fun changeCardStage(personId:Int, stage:Int, @Header("Authorization") token:String)


}