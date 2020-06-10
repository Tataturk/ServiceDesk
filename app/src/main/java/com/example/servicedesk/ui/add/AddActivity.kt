package com.example.servicedesk.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.servicedesk.R
import com.example.servicedesk.model.Ticket
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*


const val EXTRA_TICKET = "EXTRA_TICKET"

class AddActivity : AppCompatActivity() {

    //private val chipGroup: ChipGroup = cgTicket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        btNewTicket.setOnClickListener {
            validateFields()
        }

        val items = listOf("Hardware", "Software", "Account")
        var adapter = ArrayAdapter(this, R.layout.list_category, items)
        tvCategoryDropdown.setAdapter(adapter)
    }

    private fun validateFields() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()
        val category = tvCategoryDropdown.text.toString()
        val status = findViewById<Chip>(cgTicket.checkedChipId).text.toString()

        if (
            title.isNotBlank() && description.isNotBlank()
            && category.isNotBlank() && status.isNotBlank()
        ) {
            val cal = Calendar.getInstance()
            val requester: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            val ticket = Ticket(
                requester!!.uid,
                requester!!.email.toString(),
                title,
                category,
                description,
                status,
                cal.time
            )

            val resultIntent = Intent()

            resultIntent.putExtra(EXTRA_TICKET, ticket)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_LONG).show()
        }
    }

}
