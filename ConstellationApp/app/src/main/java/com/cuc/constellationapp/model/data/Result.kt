package com.cuc.constellationapp.model.data

import com.cuc.constellationapp.model.api.Month
import com.cuc.constellationapp.model.api.Today
import com.cuc.constellationapp.model.api.Week
import com.cuc.constellationapp.model.api.Year

data class Result(
    val astroid: String,
    val astroname: String,
    val month: Month,
    val today: Today,
//    val tomorrow: Tomorrow,
    val week: Week,
    val year: Year
)