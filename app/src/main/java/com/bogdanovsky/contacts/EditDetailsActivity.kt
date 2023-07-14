package com.bogdanovsky.contacts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val ID = "ID"
const val ID_DEFAUlT_VALUE = 0L

class EditDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_details)

        val id = intent.getLongExtra(ID, ID_DEFAUlT_VALUE)

        val person = Database.peopleListFromDB?.firstOrNull {
            it.id == id
        }

        person?.let {
            findViewById<TextView>(R.id.id_text_view).text = "ID: ${it.id.toString()}"

            val nameEditText = findViewById<EditText>(R.id.name_edit_view)
            nameEditText.setText(it.name)

            val surnameEditText = findViewById<EditText>(R.id.surname_edit_view)
            surnameEditText.setText(it.surname)

            val phoneEditText = findViewById<EditText>(R.id.phone_edit_view)
            phoneEditText.setText(it.phoneNumber)

            findViewById<Button>(R.id.save_button).setOnClickListener {
                with(person) {
                    name = nameEditText.text.toString()
                    surname = surnameEditText.text.toString()
                    phoneNumber = phoneEditText.text.toString()
                }
            }
        }
    }
}