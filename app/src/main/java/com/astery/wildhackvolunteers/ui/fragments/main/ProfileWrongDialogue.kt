package com.astery.wildhackvolunteers.ui.fragments.main

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.astery.wildhackvolunteers.R
import com.astery.wildhackvolunteers.databinding.AlertDeleteCardBinding
import com.astery.wildhackvolunteers.model.PersonFields
import com.astery.wildhackvolunteers.model.TaskId


class ProfileWrongDialogue(layoutInflater: LayoutInflater, context: Context, field: TaskId) {

    val binding = AlertDeleteCardBinding.inflate(layoutInflater, null, false)
    private val adb: AlertDialog.Builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
    private val alertDialog: AlertDialog

    init {
        adb.setView(binding.root)
        alertDialog = adb.create()
        binding.description.text = field.wrong
        binding.submit.text = "Исправить"
        binding.cancel.text = "Назад"
        binding.cancel.setOnClickListener { alertDialog.cancel() }
    }

    fun show() {
        alertDialog.show()
    }

    fun setOnOkListener(listener: () -> Unit):ProfileWrongDialogue {
        binding.submit.setOnClickListener {
            listener()
            alertDialog.cancel()
        }
        return this
    }
}