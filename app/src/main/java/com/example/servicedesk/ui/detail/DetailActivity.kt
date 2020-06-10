package com.example.servicedesk.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servicedesk.R
import com.example.servicedesk.model.Comment
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.ui.add.AddCommentActivity
import com.example.servicedesk.ui.add.EXTRA_COMMENT
import com.example.servicedesk.ui.add.EXTRA_TICKET
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.comments_detail.*
import kotlinx.android.synthetic.main.content_detail.*

const val COMMENT_REQUEST_CODE = 100
class DetailActivity : AppCompatActivity() {
    private lateinit var ticket: Ticket
    private lateinit var detailActivityViewModel: DetailActivityViewModel
    private var comments = arrayListOf<Comment>()
    private var detailAdapter = DetailAdapter(comments)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        ticket = intent.getParcelableExtra<Ticket>(EXTRA_TICKET)

        initviews()
        initViewModel()
    }

    private fun initviews() {

        tvTicketTitle.text = ticket.title
        tvTicketDescription.text = ticket.description.toString()
        tvTicketDate.text = ticket.date.toString()
        tvTicketStatusText.text = ticket.status

        rvComments.layoutManager = LinearLayoutManager(this@DetailActivity, RecyclerView.VERTICAL, false)
        rvComments.adapter = detailAdapter

        fab.setOnClickListener {
            val intent = Intent(this, AddCommentActivity::class.java)
            intent.putExtra(EXTRA_TICKET, ticket)
            startActivityForResult(intent,COMMENT_REQUEST_CODE)
        }
    }

    private fun initViewModel() {
        detailActivityViewModel = ViewModelProvider(this,CustomFactory(this.application,ticket.ticketId!!.toLong())).get(DetailActivityViewModel::class.java)
        detailActivityViewModel.comments.observe(this, Observer { comments ->
            this@DetailActivity.comments.clear()
            this@DetailActivity.comments.addAll(comments)
            detailAdapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)   {
            when (requestCode)  {
                COMMENT_REQUEST_CODE ->    {
                    val comment = data!!.getParcelableExtra<Comment>(EXTRA_COMMENT)
                    detailActivityViewModel.insertComment(comment)
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}
