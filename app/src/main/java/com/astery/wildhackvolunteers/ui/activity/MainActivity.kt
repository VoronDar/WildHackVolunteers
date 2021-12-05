package com.astery.wildhackvolunteers.ui.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.astery.wildhack.ui.fragments.TFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import android.widget.Toast

import android.speech.RecognizerIntent

import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.core.view.isGone
import com.astery.wildhack.ui.activity.MainActivityViewModel
import com.astery.wildhack.ui.stt.SPTUsable
import com.astery.wildhack.ui.stt.SpeechToText
import com.astery.wildhackvolunteers.R


/**
 * single activity
 * */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ParentActivity {


    private var _navController: NavController? = null
    private val navController
        get() = _navController!!


    val speechToText:SpeechToText = SpeechToText(this)

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity)

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.hostFragment) as NavHostFragment
            _navController = navHostFragment.navController

            setSupportActionBar(findViewById(R.id.toolbar)!!)

            setFullScreen(setFullScreen)
        }

        fun changeTitle(title: String?) {
            supportActionBar?.title = title
            Timber.i(supportActionBar?.title.toString())
        }

    var setFullScreen = false
    override fun setFullScreen(set:Boolean){
        setFullScreen = set
        findViewById<View>(R.id.app_bar)?.isGone = set
    }

    override fun setSearch(set:Boolean, fragment: SPTUsable){
        findViewById<View>(R.id.search).isGone = !set

        if (speechToText.isRecognitionAvailable()) {
            findViewById<View>(R.id.voice).isGone = !set
            findViewById<View>(R.id.voice).setOnClickListener {
                speechToText.speak()
            }
        }
        findViewById<View>(R.id.search).setOnClickListener {
            fragment.getText(findViewById<EditText>(R.id.search_text)!!.text.toString())
        }
        findViewById<View>(R.id.search_text).isGone = !set
        findViewById<View>(R.id.toolbar).isGone = set
        speechToText.fragment = fragment

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("recognition $data $requestCode $resultCode")
        if (requestCode == SpeechToText.VOICE_RECOGNITION_REQUEST) {
            if (data != null) {
                Timber.d("$data")
                val matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (matches!!.isNotEmpty()) {
                    findViewById<EditText>(R.id.search_text)!!.setText(matches[0])
                    speechToText.fragment?.getText(matches[0])
                }
            } else {
                Toast.makeText(this, "распознавание не удалось", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }

interface ParentActivity{
    fun setFullScreen(set:Boolean)
    fun setSearch(set:Boolean, fragment: SPTUsable)
}