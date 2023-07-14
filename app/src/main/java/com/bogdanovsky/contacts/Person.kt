package com.bogdanovsky.contacts

data class Person(
    var id: Long,
    var name: String,
    var surname: String,
    var phoneNumber: String,
    var avatar: String,
)