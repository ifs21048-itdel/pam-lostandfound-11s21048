package com.ifs21048.lostandfound.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DelcomLostandFound(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val author: String,
    val status: String,
    var isCompleted: Boolean,
    val cover: String?
) : Parcelable
