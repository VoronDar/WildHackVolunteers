package com.astery.xapplication.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answer(@PrimaryKey var id: Int, val question: String, val answer: String)
