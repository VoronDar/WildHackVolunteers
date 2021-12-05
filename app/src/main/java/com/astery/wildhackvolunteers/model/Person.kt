package com.astery.wildhackvolunteers.model

import java.util.*

data class Person(
    var name: String?,
    var surname: String?,
    var patronymic: String?,
    var birthDate: Date?,
    var email: String?,
    var phone: String?,
    var link: String?,
    var address: String?,
    var sphere: String?,
    var experience: String?,
    var travelPlace: String?
)

enum class PersonFields {
    title,
    surname,
    patronymic {
        override val nullable = true
    },
    birthDate,
    email,
    phone,
    link{
        override val nullable = true
        override val description: String? = "Ссылка на ваш аккаунт в любимой соцсети"
    },
    address{
        override val description: String? = "Область и город, в которых вы проживаете"
    },
    sphere{
        override val description: String? = "Ссылка на ваш аккаунт в любимой соцсети"
          },
    experience,
    travelPlace{
        override val description: String? = "Самая далекая точка России в которой ты был"
    };


    open val nullable: Boolean = false
    open val description: String? = null
}