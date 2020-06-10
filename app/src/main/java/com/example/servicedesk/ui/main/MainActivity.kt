package com.example.servicedesk.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servicedesk.R
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.ui.add.AddActivity
import com.example.servicedesk.ui.add.EXTRA_TICKET
import com.example.servicedesk.ui.detail.DetailActivity
import com.example.servicedesk.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


const val TICKET_REQUEST_CODE = 100
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
            startActivityForResult(intent, TICKET_REQUEST_CODE)
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
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_TICKET", ticket)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)   {
            when (requestCode)  {
                TICKET_REQUEST_CODE ->    {
                    val ticket = data!!.getParcelableExtra<Ticket>(EXTRA_TICKET)
                    mainActivityViewModel.insertTicket(ticket)
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }
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

        if (item.itemId == R.id.action_logout)  {onLogout()}
        return when (item.itemId) {
            R.id.action_logout -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onLogout()  {
        Firebase.auth.signOut()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }
}
