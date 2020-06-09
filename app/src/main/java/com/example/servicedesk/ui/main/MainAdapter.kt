package com.example.servicedesk.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.servicedesk.R
import com.example.servicedesk.model.Ticket
import kotlinx.android.synthetic.main.card_ticket.view.*

class MainAdapter(private val tickets:List<Ticket>, private val onClick: (Ticket) -> Unit) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_ticket,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        init {
            itemView.setOnClickListener {
                onClick(tickets[adapterPosition])
            }
        }
        fun bind(ticket: Ticket)   {
            itemView.tvTicketId.text = "#" + ticket.ticketId.toString()
            itemView.tvTicketStatus.text = ticket.status.toString()
            itemView.tvTicketTitle.text = ticket.title

        }
    }
}

