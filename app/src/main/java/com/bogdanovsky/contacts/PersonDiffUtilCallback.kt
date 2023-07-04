package com.bogdanovsky.contacts

import androidx.recyclerview.widget.DiffUtil

class PersonDiffUtilCallback(private val oldList: List<Person>, private val newList: List<Person>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson.name == newPerson.name &&
                oldPerson.surname == newPerson.surname &&
                oldPerson.phoneNumber == newPerson.phoneNumber
    }
}