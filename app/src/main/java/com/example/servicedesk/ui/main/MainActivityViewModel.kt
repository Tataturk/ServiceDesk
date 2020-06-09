package com.example.servicedesk.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.servicedesk.database.UserRepository
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) :
    AndroidViewModel(application){

    private val userRepository: UserRepository

    private  val ioScope = CoroutineScope(Dispatchers.IO)

    val tickets: LiveData<List<Ticket>>

    init {
        userRepository = UserRepository(application.applicationContext)
        tickets = userRepository.getTickets()
    }

    fun insertUser(user: User)  {
        ioScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun insertTicket(ticket: Ticket)    {
        ioScope.launch {
            userRepository.insertTicket(ticket)
        }
    }

}