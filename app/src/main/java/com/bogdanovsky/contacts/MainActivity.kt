package com.bogdanovsky.contacts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogdanovsky.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = PersonAdapter(Database.peopleListFromDB)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.root.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        val itemTouchHelper = ItemTouchHelper(PersonItemTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(recycler)

        val deleteSelectedButton = binding.deleteSelectedButton
        deleteSelectedButton.setOnClickListener {
            Database.peopleListFromDB.removeAll(Database.peopleListFromDB.filter {
                Database.itemsToDeleteIds.contains(it.id)
            })
            val personDiffCallback =
                PersonDiffUtilCallback(adapter.currentPeopleList, Database.peopleListFromDB)
            val personDiffResult = DiffUtil.calculateDiff(personDiffCallback)
            personDiffResult.dispatchUpdatesTo(adapter)
            adapter.setData()
        }
    }

    override fun onStart() {
        super.onStart()
        val personDiffCallback = PersonDiffUtilCallback(adapter.currentPeopleList, Database.peopleListFromDB)
        val personDiffResult = DiffUtil.calculateDiff(personDiffCallback)
        personDiffResult.dispatchUpdatesTo(adapter)
        adapter.setData()
    }

    fun navigateToEditDetailsActivity(id: Long) {
        val intent = Intent(this, EditDetailsActivity::class.java)
        intent.putExtra("ID", id)
        startActivity(intent)
    }
}