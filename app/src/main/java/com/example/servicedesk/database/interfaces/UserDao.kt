package com.example.servicedesk.database.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserWithTickets(id: Long): LiveData<List<User>>

}

@Dao
interface TicketDao {

    @Insert
    suspend fun insertTicket(ticket: Ticket)

    @Query("SELECT * FROM tickets")
    fun getAllTickets(): LiveData<List<Ticket>>

    @Delete
    suspend fun deleteTicket(ticket: Ticket)
}