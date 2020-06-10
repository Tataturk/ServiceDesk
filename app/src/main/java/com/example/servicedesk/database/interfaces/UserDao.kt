package com.example.servicedesk.database.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.servicedesk.model.Comment
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUser(id: String): LiveData<User>

}

@Dao
interface TicketDao {

    @Insert
    suspend fun insertTicket(ticket: Ticket)

    @Query("SELECT * FROM tickets")
    fun getTickets(): LiveData<List<Ticket>>

    @Delete
    suspend fun deleteTicket(ticket: Ticket)
}

@Dao
interface CommentDao    {

    @Insert
    suspend fun insertComment(comment:Comment)

    @Transaction
    @Query("SELECT * FROM comments WHERE ticket = :id")
    fun getComments(id: Long): LiveData<List<Comment>>
}