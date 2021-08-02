package com.cuc.constellationapp.model.api

data class AstroidResponse (
    val msg: String,
    val result: List<AstroResult>,
    val status: Int
)