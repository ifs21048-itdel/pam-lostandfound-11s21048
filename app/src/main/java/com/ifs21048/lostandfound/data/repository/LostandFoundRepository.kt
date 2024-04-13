package com.ifs21048.lostandfound.data.repository

import com.google.gson.Gson
import com.ifs21048.lostandfound.data.remote.MyResult
import com.ifs21048.lostandfound.data.remote.response.DelcomResponse
import com.ifs21048.lostandfound.data.remote.retrofit.IApiService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class LostandFoundRepository private constructor(
    private val apiService: IApiService,
) {
    fun postLostandFound(
        title: String,
        description: String,
        status: String,
    ) = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(
                MyResult.Success(
                    apiService.postLostandFound(title, description, status).data
                )
            )
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }

    fun putLostandFound(
        lostandfoundId: Int,
        title: String,
        description: String,
        status: String,
        is_completed: Boolean,
    ) = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(
                MyResult.Success(
                    apiService.putLostandFound(
                        lostandfoundId,
                        title,
                        description,
                        status,
                        if (is_completed) 1 else 0
                    )
                )
            )
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }

    fun getLostandFounds(
        is_completed: Int?,
//        is_me: Int?,
//        status: String,
    ) = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(MyResult.Success(apiService.getLostandFounds(is_completed)))
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }

    fun getLostandFound(
        lostandfoundId: Int,
    ) = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(MyResult.Success(apiService.getLostandFound(lostandfoundId)))
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }

    fun deleteLostandFound(
        lostandfoundId: Int,
    ) = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(MyResult.Success(apiService.deleteLostandFound(lostandfoundId)))
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LostandFoundRepository? = null

        fun getInstance(
            apiService: IApiService,
        ): LostandFoundRepository {
            synchronized(LostandFoundRepository::class.java) {
                INSTANCE = LostandFoundRepository(
                    apiService
                )
            }
            return INSTANCE as LostandFoundRepository
        }
    }
}