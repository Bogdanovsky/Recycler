package com.bogdanovsky.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EditDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_details)

        val id = intent.getLongExtra("ID", 0L)

        val person = Database.peopleListFromDB.filter {
            it.id == id
        }.first()

        val idTextView = findViewById<TextView>(R.id.id_text_view)
        idTextView.text = "ID: ${person.id.toString()}"

        val nameEditText = findViewById<EditText>(R.id.name_edit_view)
        nameEditText.setText(person.name)

        val surnameEditText = findViewById<EditText>(R.id.surname_edit_view)
        surnameEditText.setText(person.surname)

        val phoneEditText = findViewById<EditText>(R.id.phone_edit_view)
        phoneEditText.setText(person.phoneNumber)

        findViewById<Button>(R.id.save_button).setOnClickListener {
            person.name = nameEditText.text.toString()
            person.surname = surnameEditText.text.toString()
            person.phoneNumber = phoneEditText.text.toString()
        }
    }
}