package com.example.servicedesk.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servicedesk.R
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.ui.add.AddActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var tickets = arrayListOf<Ticket>()
    private var mainAdapter = MainAdapter(tickets) { Ticket -> onTicket(Ticket) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
        }

        rvCards.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvCards.adapter = mainAdapter
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.tickets.observe(this, Observer  { tickets ->
            this@MainActivity.tickets.clear()
            this@MainActivity.tickets.addAll(tickets)
            mainAdapter.notifyDataSetChanged()
        })
    }

    private fun onTicket(ticket: Ticket)   {
        val intent = Intent(this, TicketInfoActivity::class.java)
        intent.putExtra("EXTRA_TICKET", ticket)
        startActivityForResult(intent,TICKET_INFO_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
