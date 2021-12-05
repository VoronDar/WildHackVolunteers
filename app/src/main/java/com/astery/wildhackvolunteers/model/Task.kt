package com.astery.wildhackvolunteers.model

import androidx.navigation.NavDirections
import com.astery.wildhackvolunteers.ui.fragments.main.MainFragmentDirections

data class Task(val id: TaskId, val isDone:Boolean, val subTaskAmount:Int, val madeSubTasks:Int)

enum class TaskId{
    Profile {
        override val title: String
            get() = "Составить анкету"
        override val isNeccesary: Boolean
            get() = true
        override val action: NavDirections
            get() = MainFragmentDirections.actionMainFragmentToProfileFragment()
        override val wrong: String
            get() = "Не все обязательные поля анкеты заполнены"
    },
    Learn {
        override val title: String
            get() = "Пройти курс"
        override val isNeccesary: Boolean
            get() =  false
        override val action: NavDirections
            get() = TODO("Not yet implemented")
        override val wrong: String
            get() = TODO("Not yet implemented")
    },
    Test {
        override val title: String
            get() = "Выполнить тест"
        override val isNeccesary: Boolean
            get() = true
        override val action: NavDirections
            get() = TODO("Not yet implemented")
        override val wrong: String
            get() = TODO("Not yet implemented")
    };

    abstract val title:String
    abstract val isNeccesary:Boolean
    abstract val action:NavDirections
    abstract val wrong:String
}