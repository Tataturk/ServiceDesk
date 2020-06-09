package com.example.servicedesk.ui.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.servicedesk.R
import com.example.servicedesk.model.Ticket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*

const val EXTRA_TICKET = "EXTRA_TICKET"
class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initViews()

        val items = listOf("Hardware", "Software", "Account")
        var adapter = ArrayAdapter(this, R.layout.list_category, items)
        tvCategoryDropdown.setAdapter(adapter)
    }

    private fun initViews() {
        btNewTicket.setOnClickListener{
            validateFields()
        }
    }

    private fun validateFields()    {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()
        val category = tvCategoryDropdown.text.toString()

        if (
            title.isNotBlank() && description.isNotBlank()
            && category.isNotBlank()
                )   {
            val cal = Calendar.getInstance()
            val status = 0 //New
            val requester: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            val ticket = Ticket(requester!!.uid,title,category,description,status,cal.time)

            val resultIntent = Intent()

            resultIntent.putExtra(EXTRA_TICKET, ticket)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        else    {
            Toast.makeText(this,"All fields must be filled in", Toast.LENGTH_LONG).show()
        }
    }

}
