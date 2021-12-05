package com.astery.wildhackvolunteers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnswerTag(@PrimaryKey val id:String){
    companion object{
        fun makeList(names: List<String>):ArrayList<AnswerTag>{
            val list = ArrayList<AnswerTag>()
            for (i in names){
                list.add(AnswerTag(i))
            }
            return list
        }
    }
}