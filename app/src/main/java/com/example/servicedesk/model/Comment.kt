package com.example.servicedesk.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "comments")
data class Comment (
    var commenttext: String,
    var commenter: Long,
    var ticket: Long,
    @PrimaryKey var commentId: Long? = null
) : Parcelable
