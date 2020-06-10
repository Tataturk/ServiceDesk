package com.example.servicedesk.ui.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.servicedesk.R
import com.example.servicedesk.model.Comment
import com.example.servicedesk.model.Ticket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.comment_new.*
import java.util.*

const val EXTRA_COMMENT = "EXTRA_COMMENT"

class AddCommentActivity : AppCompatActivity() {

    private lateinit var ticket: Ticket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_new)

        ticket = intent.getParcelableExtra<Ticket>(EXTRA_TICKET)
        initViews()
    }

    private fun initViews() {
        btNewComment.setOnClickListener {
            validateFields()
        }
        Toast.makeText(this,ticket.ticketId.toString(),Toast.LENGTH_LONG).show()
    }

    private fun validateFields() {
        val commentText = etComment.text.toString()

        if (commentText.isNotBlank()) {
            val cal = Calendar.getInstance()
            val requester: FirebaseUser? = FirebaseAuth.getInstance().currentUser

            val comment = Comment(
                commentText,
                requester!!.uid,
                requester!!.email.toString(),
                cal.time,
                ticket.ticketId!!.toLong()
            )

            val resultIntent = Intent()

            resultIntent.putExtra(EXTRA_COMMENT, comment)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_LONG).show()
        }

    }
}
