package com.astery.wildhackvolunteers.ui.fragments.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.wildhack.model.Answer
import com.astery.wildhackvolunteers.model.Person
import com.astery.wildhack.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {

    @set:Inject
    lateinit var repository: Repository

    private val _anwers:MutableLiveData<ArrayList<Answer>> = MutableLiveData()
    val answers:LiveData<ArrayList<Answer>>
    get() = _anwers

    fun getAnswers(string:String){
        viewModelScope.launch {
            _anwers.value = repository.getAnswers(string)
        }
    }
}
