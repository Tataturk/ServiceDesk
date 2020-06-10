package com.example.servicedesk.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.servicedesk.database.CommentRepository
import com.example.servicedesk.model.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivityViewModel(application: Application, ticketId: Long) : ViewModel() {

    private val commentRepository: CommentRepository

    private val ioScope = CoroutineScope(Dispatchers.IO)

    val comments: LiveData<List<Comment>>

    init {
        commentRepository = CommentRepository(application.applicationContext)
        comments = commentRepository.getComments(ticketId)
    }

    fun insertComment(comment: Comment) {
        ioScope.launch {
            commentRepository.insertComment(comment)
        }
    }
}


//https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments?rq=1

class CustomFactory(private val application: Application, private val ticketId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivityViewModel(application, ticketId) as T
    }

}