package com.example.servicedesk.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.servicedesk.R
import com.example.servicedesk.model.Comment
import kotlinx.android.synthetic.main.comment.view.*
import java.text.SimpleDateFormat
import java.util.*

class DetailAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: DetailAdapter.ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: Comment) {
            itemView.tvCommentName.text = comment.commenterName
            itemView.tvCommentDate.text = getDateFormatted(comment.date)
            itemView.tvCommentDescription.text = comment.comment


        }
    }

    private fun getDateFormatted(date: Date): String {
        return SimpleDateFormat("d MMM yyyy").format(date)
    }
}

