package com.astery.wildhackvolunteers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnswerAndTag(val answerId:Int, val tagId:String){
    @PrimaryKey(autoGenerate = true) var id:Int? = null
}