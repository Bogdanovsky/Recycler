package com.bogdanovsky.contacts

import android.graphics.Color
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.bogdanovsky.contacts.Database.Companion.itemsToDeleteIds
import java.util.*

class PersonAdapter() : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(),
    ItemTouchHelperAdapter {

    var people = mutableListOf<Person>()

    init {
        setData()
    }

    fun setData() {
        people.clear()
        people = Database.peopleListFromDB.map(Person::clone).toMutableList()
    }

    class PersonViewHolder(itemView: View, val adapter: PersonAdapter) : ViewHolder(itemView),
        View.OnTouchListener,
        GestureDetector.OnGestureListener {

        private val gestureDetector = GestureDetector(itemView.context, this)

        init {
            itemView.setOnTouchListener(this)
        }

        override fun onTouch(view: View?, event: MotionEvent?): Boolean {
            event?.let {
                gestureDetector.onTouchEvent(it)
            }
            return true
        }

        override fun onDown(p0: MotionEvent): Boolean {
            return false
        }

        override fun onShowPress(p0: MotionEvent) {
        }

        override fun onSingleTapUp(p0: MotionEvent): Boolean {
            val id: Long = itemView.findViewById<TextView>(R.id.id_number).text.toString().toLong()
            if (itemsToDeleteIds.contains(id)) {
                itemsToDeleteIds.remove(id)
                itemView.findViewById<ConstraintLayout>(R.id.item_layout)
                    .setBackgroundColor(Color.WHITE)
            } else {
                itemsToDeleteIds.add(id)
                itemView.findViewById<ConstraintLayout>(R.id.item_layout)
                    .setBackgroundColor(Color.GRAY)
            }
            return true
        }

        override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return true
        }

        override fun onLongPress(p0: MotionEvent) {
        }

        override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = people[position]
        holder.itemView.apply {
            findViewById<ConstraintLayout>(R.id.item_layout)
                .setBackgroundColor(if (itemsToDeleteIds.contains(item.id)) Color.GRAY else Color.WHITE)
            findViewById<TextView>(R.id.name).text = "${item.name} ${item.surname}"
            findViewById<TextView>(R.id.phone).text = "tel. ${item.phoneNumber}"
            findViewById<TextView>(R.id.id_number).text = item.id.toString()
            findViewById<ImageView>(R.id.avatar).load(item.avatar) {
                placeholder(R.drawable.baseline_broken_image_24)
                error(R.drawable.baseline_error_24)
            }
            findViewById<Button>(R.id.edit_button).setOnClickListener {
                (context as MainActivity).navigateToEditDetailsActivity(item.id)
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(Database.peopleListFromDB, i, i + 1)
                setData()
            }
        } else {
            for (i in toPosition until fromPosition) {
                Collections.swap(Database.peopleListFromDB, i, i - 1)
                setData()
            }
        }
        val personDiffCallback = PersonDiffUtilCallback(people, Database.peopleListFromDB)
        val personDiffResult = DiffUtil.calculateDiff(personDiffCallback)
        personDiffResult.dispatchUpdatesTo(this)
//        notifyItemMoved(fromPosition, toPosition) // more effective than diffutil in that case
        setData()
    }

    override fun onItemDismiss(position: Int) {
        Database.peopleListFromDB.removeAt(position)
        val personDiffCallback = PersonDiffUtilCallback(people, Database.peopleListFromDB)
        val personDiffResult = DiffUtil.calculateDiff(personDiffCallback)
        personDiffResult.dispatchUpdatesTo(this)
//        notifyItemRemoved(position) // more effective than diffutil in case of one item deletion
        setData()
    }
}

