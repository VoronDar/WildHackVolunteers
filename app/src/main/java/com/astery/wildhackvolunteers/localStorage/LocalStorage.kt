package com.astery.wildhackvolunteers.localStorage

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
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LocalStorage @Inject constructor(
    @ApplicationContext var context: Context,
    @set:Inject var appDatabase: AppDatabase
) {


    suspend fun getAnswers(sentence: String): List<Answer> {
        val a = sentence.replace("?|.|,", "").replace("ё", "e").lowercase().split(" ").toList()
        Timber.d("${a.toList()}")
        return appDatabase.articleDao().getArticlesWithTag(a)
    }

    suspend fun getAnswers(): List<Answer> {
        return appDatabase.articleDao().getAdvices()
    }

    suspend fun getSavedAnswers(): List<Answer> {
        return appDatabase.articleDao().getSavedAnswers(true)
    }

    suspend fun updateAnswer(answer: Answer) {
        appDatabase.articleDao().updateAnswer(answer)
    }

    suspend fun fillAnswers() {
        val a1 = AnswerTag("перелёт")
        val a2 = AnswerTag("переезд")
        val a3 = AnswerTag("камчатка")
        val a32 = AnswerTag("камчатку")
        val a31 = AnswerTag("камчатки")
        val a4 = AnswerTag("доехать")
        val a41 = AnswerTag("добраться")
        val a5 = AnswerTag("оплачивает")
        val a51 = AnswerTag("оплачиваемое")
        val a6 = AnswerTag("питание")
        val a7 = AnswerTag("пропитание")
        val a71 = AnswerTag("еда")
        val a72 = AnswerTag("еду")


        val arrayForMedicine = AnswerTag.makeList(
            listOf(
                "медицина",
                "лекарства",
                "врачи",
                "врач",
                "доктор",
                "больница",
                "аптечка",
                "аптечку",
                "брать"
            )
        )
        val an0 = Answer(990,
            "Заповедные территории – зоны высокой вулканической и сейсмической активности, вокруг множество диких животных. Больницы рядом нет, поэтому возьмите личную аптечку",
            "Есть ли рядом больницы?"
        )
        an0.tags = arrayForMedicine
        appDatabase.articleDao().addArticleWithTags(an0)

        val arrayForQr = AnswerTag.makeList(
            listOf(
                "covid19",
                "коронавирус",
                "qr",
                "код",
                "коронавируса",
                "медецина"
            )
        )
        val an3 = Answer(
            65,
            "Необходимо наличие отрицательного ПЦР теста, qr код не обязателен",
            "Нужен ли qr код?"
        )
        an3.tags = arrayForQr
        appDatabase.articleDao().addArticleWithTags(an3)

        val arrayForNext = AnswerTag.makeList(
            listOf(
                "когда",
                "приезжать",
                "приехать",
                "делать",
                "дальше"
            )
        )
        val an4 = Answer(
            543,
            "График работы волонтеров на сезоны размещается на сайте www.kronoki.ru ",
            "Когда призжать?"
        )
        an4.tags = arrayForNext
        appDatabase.articleDao().addArticleWithTags(an4)


        val arrayForClothes = AnswerTag.makeList(
            listOf(
                "какую",
                "одежду",
                "одежда",
                "брать",
                "нужна"
            )
        )
        val an5= Answer(
            123,
            "Температура на Камчатке быстро меняется. Возьмите теплую и легкую одежду. Не берите с собой чрезмерно открытую одежду",
            "Какую одежду взять?"
        )
        an5.tags = arrayForClothes
        appDatabase.articleDao().addArticleWithTags(an5)


        val arrayForNet = AnswerTag.makeList(
            listOf(
                "интернет",
                "есть",
                "связь",
            )
        )
        val an6= Answer(
            1666,
            "На кордонах утром и вечером есть медленный интернет. Хватит только на отправку текстовых сообщений. Связи практически нет",
            "Есть интернет?"
        )
        an6.tags = arrayForNet
        appDatabase.articleDao().addArticleWithTags(an6)




        val an1 = Answer(
            1,
            "Основное питание предоставляется. Фрукты, молочные продукты и сладости необходимо докупать отдельно",
            "Предоставляется ли питание?"
        )
        an1.tags = arrayListOf(a5, a51, a6, a7, a71, a72)
        val an2 = Answer(
            2,
            "Перелет до Камчатки осуществляется за ваш счет",
            "Кто оплачивает перелёт до Камчатки?"
        )
        an2.tags = arrayListOf(a1, a2, a3, a31, a32, a41, a4, a5)
        val a = appDatabase.articleDao().addArticleWithTags(an1)
        appDatabase.articleDao().addArticleWithTags(an2)

        Timber.d("принт алл ${appDatabase.articleDao().getTags()}")
        Timber.d("принт $a")
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