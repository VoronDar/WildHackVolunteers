package com.astery.wildhack.ui.activity

import androidx.lifecycle.ViewModel
import com.astery.wildhack.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    @set:Inject
    lateinit var repository: Repository
}
