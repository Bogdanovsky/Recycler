package com.bogdanovsky.flags

import java.util.*

class Countries() {
    companion object {
        fun getCountries(): List<Pair<String, String>> {
            val isoCountryCodes = Locale.getISOCountries()
            val countries = mutableListOf<Pair<String, String>>()
            for (code in isoCountryCodes) {
                val locale = Locale(code, code)
                countries.add(locale.country.lowercase() to locale.displayCountry)
                println(locale.country to locale.displayCountry)
            }
            return countries
        }
    }
}
