package com.astery.wildhackvolunteers.localStorage.database.dao

import androidx.room.*
import com.astery.wildhack.model.Answer
import com.astery.wildhackvolunteers.model.AnswerAndTag
import timber.log.Timber

@Dao
interface ArticleDao {
    /** return articles */
    @Query("SELECT Answer.id, Answer.answer, Answer.question, Answer.saved FROM Answer INNER JOIN AnswerAndTag ON Answer.id == AnswerAndTag.answerId AND AnswerAndTag.tagId IN (:tags) GROUP BY Answer.id")
    suspend fun getArticlesWithTag(tags: List<String>): List<Answer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAdvises(advice: List<Answer>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAdvise(advice: Answer)

    @Query("SELECT * FROM ANSWER")
    suspend fun getAdvices():List<Answer>
    @Query("SELECT * FROM AnswerAndTag")
    suspend fun getTags():List<AnswerAndTag>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTagRelation(articleAndTag: AnswerAndTag)

    @Transaction
    suspend fun addArticleWithTags(article: Answer){
        addAdvise(article)
        for (i in article.tags){
           addTagRelation(AnswerAndTag(article.id, i.id))
        }
    }

    @Query("SELECT * FROM Answer WHERE Answer.saved = :saved")
    suspend fun getSavedAnswers(saved:Boolean):List<Answer>

    @Update
    suspend fun updateAnswer(answer:Answer)

    @Query("DELETE FROM answerandtag")
    suspend fun deleteArticleAndTagRelations()

    @Query("DELETE FROM answer")
    suspend fun deleteArticles()

    @Transaction
    suspend fun deleteArticlesWithTags(){
        deleteArticles()
        deleteArticleAndTagRelations()
    }



}