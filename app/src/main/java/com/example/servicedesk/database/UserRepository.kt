package com.example.servicedesk.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.servicedesk.database.interfaces.CommentDao
import com.example.servicedesk.database.interfaces.TicketDao
import com.example.servicedesk.database.interfaces.UserDao
import com.example.servicedesk.model.Comment
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.model.User

class UserRepository(context: Context) {
    private val userDao: UserDao
    private val ticketDao: TicketDao

    init {
        val database = ServiceDeskRoomDatabase.getDatabase(context)
        userDao = database!!.userDao()
        ticketDao = database!!.ticketDao()
    }

    fun getUser(id: String): LiveData<User>  {
        return userDao.getUser(id)
    }

    suspend fun insertUser(user: User)  {
        return userDao.insertUser(user)
    }

    suspend fun insertTicket(ticket: Ticket)    {
        return ticketDao.insertTicket(ticket)
    }

    fun getTickets(): LiveData<List<Ticket>>    {
        return ticketDao.getTickets()
    }

}

class CommentRepository(context: Context)   {
    private val ticketDao: TicketDao
    private val commentDao: CommentDao

    init {
        val database = ServiceDeskRoomDatabase.getDatabase(context)
        ticketDao = database!!.ticketDao()
        commentDao = database!!.commentDao()
    }

    fun getComments(id: Long): LiveData<List<Comment>>   {
        return commentDao.getComments(id)
    }

    suspend fun insertComment(comment: Comment) {
        return commentDao.insertComment(comment)
    }
}