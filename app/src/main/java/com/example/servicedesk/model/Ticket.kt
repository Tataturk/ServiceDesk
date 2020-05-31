package com.example.servicedesk.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "tickets")
data class Ticket (
    var requester: Long,
    var title: String,
    var category: String,
    var description: String,
    var status: Int,
    var date: Date,
    @PrimaryKey var ticketId: Long? = null
) : Parcelable

/*
data class Ticketcomments(
    @Embedded var user: User,
    @Relation(
        parentColumn = "ticketId",
        entityColumn = "ticket"
    )
    val comments: List<Comment>
)*/
