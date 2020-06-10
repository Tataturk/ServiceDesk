package com.example.servicedesk.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "comments")
data class Comment (
    var comment: String,
    var commenterId: String,
    var commenterName: String,
    var date: Date,
    var ticket: Long,
    @PrimaryKey(autoGenerate = true) var commentId: Long? = null
) : Parcelable
