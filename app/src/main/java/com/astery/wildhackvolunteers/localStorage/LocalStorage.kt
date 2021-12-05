package com.astery.wildhack

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.astery.wildhack.model.Answer
import com.astery.wildhackvolunteers.localStorage.database.AppDatabase
import com.astery.wildhackvolunteers.model.AnswerTag
import com.astery.wildhackvolunteers.model.Person
import com.astery.wildhackvolunteers.model.PersonFields
import com.astery.wildhackvolunteers.model.TaskId
import com.astery.wildhackvolunteers.ui.fragments.main.State
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class LocalStorage @Inject constructor(
    @ApplicationContext var context: Context,
    @set:Inject var appDatabase: AppDatabase
) {


    suspend fun getAnswers(sentence: String): List<Answer> {
        val a = sentence.split(" ").toList()
        return appDatabase.articleDao().getArticlesWithTag(a)
    }

    suspend fun fillAnswers() {
        val a1 = AnswerTag("Перелёт")
        val a2 = AnswerTag("Переезд")
        val a3 = AnswerTag("Камчатка")
        val a31 = AnswerTag("Камчатки")
        val a4 = AnswerTag("Доехать")
        val a5 = AnswerTag("Оплачивает")
        val a51 = AnswerTag("Оплачиваемое")
        val a6 = AnswerTag("Питание")
        val a7 = AnswerTag("Пропитание")

        val an1 = Answer(
            1,
            "Основное питание предоставляется. Фрукты, молочные продукты и сладости необходимо докупать отдельно",
            "Предоставляется ли питание?"
        )
        an1.tags = arrayListOf(a5, a51, a6, a7)
        val an2 = Answer(
            2,
            "Перелет до Камчатки осуществляется за ваш счет",
            "Кто оплачивает перелёт до Камчатки?"
        )
        an2.tags = arrayListOf(a1, a2, a3, a31, a4, a5)
        appDatabase.articleDao().addArticleWithTags(an1)
        appDatabase.articleDao().addArticleWithTags(an2)

    }


    @Throws(ValueNotFoundException::class)
    fun getToken(now: Date): String {
        val tokenValidUntil = getPref(context).getLong(tokenValidUntilPref(), 0)

        if (now.time > tokenValidUntil) throw ValueNotFoundException()
        return getPref(context).getString(tokenPref(), null) ?: throw ValueNotFoundException()
    }

    fun setToken(token: String, tokenValidUntil: Long) {
        getPref(context).edit {
            putLong(tokenValidUntilPref(), tokenValidUntil)
            putString(tokenPref(), token)
        }
    }

    /** it assumes, that this function may be called only after authorisation */
    @Throws(ValueNotFoundException::class)
    fun getRefreshToken(): String {
        return getPref(context).getString(refreshTokenPref(), null)
            ?: throw ValueNotFoundException()

    }


    fun setRefreshToken(refreshToken: String) {
        getPref(context).edit {
            putString(refreshTokenPref(), refreshToken)
        }
    }


    fun isEntered(): Boolean {
        return getPref(context).getBoolean(isEnteredPref(), false)
    }


    fun setEntered(entered: Boolean) {
        getPref(context).edit {
            putBoolean(isEnteredPref(), entered)
        }
    }

    fun getPerson(): Person? {
        val person = Person(
            name = getStr(PersonFields.title),
            surname = getStr(PersonFields.surname),
            patronymic = getStr(PersonFields.patronymic),
            birthDate = getDate(PersonFields.birthDate),
            email = getStr(PersonFields.email),
            phone = getStr(PersonFields.phone),
            link = getStr(PersonFields.link),
            address = getStr(PersonFields.address),
            sphere = getStr(PersonFields.sphere),
            experience = getStr(PersonFields.experience),
            travelPlace = getStr(PersonFields.travelPlace),
        )
        return person
    }

    fun savePerson(person: Person?) {
        getPref(context).edit {
            putString(PersonFields.title.name, person?.name)
            putString(PersonFields.surname.name, person?.surname)
            putString(PersonFields.patronymic.name, person?.patronymic)
            person?.birthDate?.time?.let { putLong(PersonFields.birthDate.name, it) }
            putString(PersonFields.email.name, person?.email)
            putString(PersonFields.phone.name, person?.phone)
            putString(PersonFields.link.name, person?.link)
            putString(PersonFields.address.name, person?.address)
            putString(PersonFields.sphere.name, person?.sphere)
            putString(PersonFields.experience.name, person?.experience)
            putString(PersonFields.travelPlace.name, person?.travelPlace)
        }
    }

    fun getState(): State {
        return State.values()[getPref(context).getInt("state", 0)]
    }

    fun setState(state: State) {
        getPref(context).edit {
            putInt("state", state.ordinal)
        }
    }

    fun getMadeSubTasks(profile: TaskId): Int {
        val person = getPerson()
        if (person == null) return 0
        var count = 0
        if (person.name != null) count += 1
        if (person.surname != null) count += 1
        if (person.patronymic != null) count += 1
        if (person.email != null) count += 1
        if (person.phone != null) count += 1
        if (person.link != null) count += 1
        if (person.address != null) count += 1
        if (person.sphere != null) count += 1
        if (person.experience != null) count += 1
        if (person.travelPlace != null) count += 1
        return count
    }


    private fun getDate(pref: PersonFields): Date? {
        val date = getPref(context).getLong(PersonFields.birthDate.name, 0)
        if (date == 0L) return null
        else return Date(date)
    }

    private fun getStr(pref: PersonFields): String? {
        return getPref(context).getString(pref.name, null)
    }


    private fun getPref(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", 0)
    }


    private fun tokenPref() = "token"
    private fun tokenValidUntilPref() = "tokenvailduntil"
    private fun refreshTokenPref() = "refreshToken"
    private fun isEnteredPref() = "entered"
}

class ValueNotFoundException : Exception()