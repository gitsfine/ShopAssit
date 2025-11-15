package com.aisight.shopassist.ui.initiallogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InitialLoginViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "UserName"
    }
    val text: LiveData<String> = _text
}