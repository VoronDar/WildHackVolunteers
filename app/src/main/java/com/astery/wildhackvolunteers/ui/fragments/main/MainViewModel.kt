package com.astery.wildhackvolunteers.ui.fragments.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.wildhack.model.Answer
import com.astery.wildhack.repository.Repository
import com.astery.wildhack.states.JIdle
import com.astery.wildhack.states.JobState
import com.astery.wildhackvolunteers.model.Task
import com.astery.wildhackvolunteers.model.TaskId
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {

    @set:Inject
    lateinit var repository: Repository

    private val _tasks: MutableLiveData<ArrayList<Task>> = MutableLiveData(arrayListOf())
    val tasks: LiveData<ArrayList<Task>>
        get() = _tasks

    private val _answers: MutableLiveData<ArrayList<Answer>> = MutableLiveData(arrayListOf())
    val answers: LiveData<ArrayList<Answer>>
        get() = _answers

    private val _loadingState: MutableLiveData<JobState> = MutableLiveData(JIdle())
    val loadingState: LiveData<JobState>
        get() = _loadingState

    private val _wrongTask: MutableLiveData<TaskId> = MutableLiveData()
    val wrongTask: LiveData<TaskId>
        get() = _wrongTask

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state


    fun getInfo() {
        getTasks()
        getAnswers()
        loadState()
        get()
    }


    private fun get(){
        viewModelScope.launch {
            repository.fillAnswers()
            Timber.d("all answers ${repository.getAllAnswers()}")
            val list = repository.getAnswers("Кто оплачивает")
            val list2 = repository.getAnswers("Кто оплачивает питание")
            val list3 = repository.getAnswers("перелет до камчатки")
            Timber.d("list $list")
            Timber.d("list $list2")
            Timber.d("list $list3")
        }
    }

    private fun loadState(){
        _state.value = repository.getState()
    }

    private fun getTasks() {
        viewModelScope.launch {
            _tasks.value = arrayListOf(
                Task(
                    TaskId.Profile,
                    false,
                    subTaskAmount = 9,
                    madeSubTasks = repository.getMadeSubtaskForTask(TaskId.Profile)
                )
            )
        }
    }

    private fun getAnswers() {
        viewModelScope.launch {
            _answers.value = arrayListOf(
                Answer(1, "Не дерись с медведями в лесу", "Что делать с медведями?"),
                Answer(2, "Не играй с оленями", "Что делать с оленями?")
            )
            // repository.getAnswers()
        }
    }

    fun send() {
        viewModelScope.launch {
            val person = repository.getPerson()
            if (person?.name == null || person.surname == null || person.patronymic == null
                || person.phone == null || person.email == null || person.address == null || person.travelPlace == null || person.sphere == null){
                _wrongTask.value = TaskId.Profile
            } else{
                _state.value = State.idle
                repository.setState(State.idle)
            }
        }
    }

}

enum class State{
    new,
    idle,
    learning,
    canceled,
    approved,
    accepted
}
