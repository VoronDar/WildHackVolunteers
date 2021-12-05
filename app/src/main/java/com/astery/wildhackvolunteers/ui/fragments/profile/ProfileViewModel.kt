package com.astery.wildhackvolunteers.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.wildhackvolunteers.model.Person
import com.astery.wildhack.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    @set:Inject
    lateinit var repository: Repository

    private val _person:MutableLiveData<Person> = MutableLiveData()
    val person:LiveData<Person>
    get() = _person

    private val _saved:MutableLiveData<Boolean> = MutableLiveData(false)
    val saved:LiveData<Boolean>
        get() = _saved

    fun loadPerson(){
        viewModelScope.launch{
            _person.value = repository.getPerson()
        }
    }

    fun save(){
        Timber.d("${person.value}")
        viewModelScope.launch {
            repository.savePersonToLocal(person.value)
            _saved.value = true
        }
    }
}
