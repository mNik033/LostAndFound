package com.ink.lnf.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileFragmentViewModel : ViewModel(){
    val name = MutableLiveData<String>()
    fun setName(newData : String){
        name.value = newData
    }
    val email = MutableLiveData<String>()
    fun setEmail(newData: String){
        email.value = newData
    }
    val image = MutableLiveData<String>()
    fun setImage(newData: String){
        image.value = newData
    }
}