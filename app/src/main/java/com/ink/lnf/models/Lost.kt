package com.ink.lnf.models

data class Lost(
    val useruid : String = "",
    val image : String,
    val name : String = "",
    val location : String = "",
    val date : String = "",
    val contact : String = "",
    val description : String = ""
)