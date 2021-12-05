package com.astery.wildhack.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.astery.wildhackvolunteers.model.AnswerTag

@Entity
data class Answer(@PrimaryKey val id:Int, val answer:String, val question:String){
    var saved:Boolean = false
    @Ignore
    var tags = ArrayList<AnswerTag>()
}