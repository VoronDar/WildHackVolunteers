package com.astery.wildhack.ui.stt

import android.speech.RecognizerIntent

import android.content.Intent

import android.content.pm.PackageManager
import com.astery.wildhackvolunteers.ui.activity.MainActivity


class SpeechToText(val activity: MainActivity) {

    var fragment:SPTUsable? = null


    private fun isRecognitionAvailable():Boolean {
        val manager: PackageManager = activity.packageManager
        val activities =
            manager.queryIntentActivities(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0)
        return activities.isNotEmpty()
    }

    private fun speak() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, javaClass.getPackage().name)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
        )
        activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST)
    }

    companion object{

        const val FILTER_REQUEST = 41
        const val FILTER_RESULT_OK = 1
        const val FILTER_TYPE_PARAM = "type"
        const val VOICE_RECOGNITION_REQUEST = 111
    }

}


interface SPTUsable{
    fun getText(value:String)
}