package com.example.servicedesk.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "users")
data class User (
    var name: String,
    var email: String,
    var type: Int,
    @PrimaryKey var userId: Long? = null
) : Parcelable

data class UserTickets(
    @Embedded var user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "requester"
    )
    val tickets: List<Ticket>
)