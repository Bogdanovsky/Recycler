package com.bogdanovsky.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogdanovsky.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = PersonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler: RecyclerView = binding.root.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(PersonItemTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(recycler)

        val deleteSelectedButton = binding.deleteSelectedButton
        deleteSelectedButton.setOnClickListener {
            Database.peopleListFromDB.removeAll(Database.peopleListFromDB.filter {
                Database.itemsToDeleteIds.contains(it.id)
            })
            val personDiffCallback =
                PersonDiffUtilCallback(adapter.people, Database.peopleListFromDB)
            val personDiffResult = DiffUtil.calculateDiff(personDiffCallback)
            personDiffResult.dispatchUpdatesTo(adapter)
            adapter.setData()
        }
    }

    override fun onStart() {
        super.onStart()
        val personDiffCallback = PersonDiffUtilCallback(adapter.people, Database.peopleListFromDB)
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