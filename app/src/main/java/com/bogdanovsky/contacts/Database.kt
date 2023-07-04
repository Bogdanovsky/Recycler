package com.bogdanovsky.contacts

import com.github.javafaker.Faker

class Database {
    companion object {

        private val faker: Faker = Faker.instance()
        val peopleListFromDB =
            (1..100).map {
                Person(
                    id = it.toLong(),
                    name = faker.name().firstName(),
                    surname = faker.name().lastName(),
                    phoneNumber = faker.phoneNumber().cellPhone(),
                    avatar = "https://xsgames.co/randomusers/assets/avatars/female/${it % 79}.jpg",
                )
            }.toMutableList()

        val itemsToDeleteIds: MutableList<Long> = mutableListOf()
    }
}