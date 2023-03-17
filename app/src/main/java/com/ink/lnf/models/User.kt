package com.ink.lnf.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id : String = "",
    val name : String = "",
    val email : String = "",
    val image : String = "",
    val mobile : Long = 0,
    val fcmToken : String = ""
)