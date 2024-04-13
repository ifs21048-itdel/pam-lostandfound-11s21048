package com.ifs21048.lostandfound.presentation.lostandfound

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ifs21048.lostandfound.data.remote.MyResult
import com.ifs21048.lostandfound.data.remote.response.DataAddLostandFoundResponse
import com.ifs21048.lostandfound.data.remote.response.DelcomLostandFoundResponse
import com.ifs21048.lostandfound.data.remote.response.DelcomResponse
import com.ifs21048.lostandfound.data.repository.LostandFoundRepository
import com.ifs21048.lostandfound.presentation.ViewModelFactory

class LostandFoundViewModel(
    private val lostandFoundRepository: LostandFoundRepository
) : ViewModel() {

    fun getLostandFound(lostandfoundId: Int): LiveData<MyResult<DelcomLostandFoundResponse>> {
        return lostandFoundRepository.getLostandFound(lostandfoundId).asLiveData()
    }

    fun postLostandFound(
        title: String,
        description: String,
        status : String,
    ): LiveData<MyResult<DataAddLostandFoundResponse>> {
        return lostandFoundRepository.postLostandFound(
            title,
            description,
            status
        ).asLiveData()
    }

    fun putLostandFound(
        todoId: Int,
        title: String,
        description: String,
        status: String,
        is_completed: Boolean,
    ): LiveData<MyResult<DelcomResponse>> {
        return lostandFoundRepository.putLostandFound(
            todoId,
            title,
            description,
            status,
            is_completed,
        ).asLiveData()
    }

    fun deleteLostandFound(todoId: Int): LiveData<MyResult<DelcomResponse>> {
        return lostandFoundRepository.deleteLostandFound(todoId).asLiveData()
    }

    companion object {
        @Volatile
        private var INSTANCE: LostandFoundViewModel? = null

        fun getInstance(
            lostandfoundRepository: LostandFoundRepository
        ): LostandFoundViewModel {
            synchronized(ViewModelFactory::class.java) {
                INSTANCE = LostandFoundViewModel(
                    lostandfoundRepository
                )
            }
            return INSTANCE as LostandFoundViewModel
        }
    }
}