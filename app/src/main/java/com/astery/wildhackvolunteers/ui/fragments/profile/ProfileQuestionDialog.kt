package com.astery.wildhackvolunteers.ui.fragments.profile

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.astery.wildhackvolunteers.R
import com.astery.wildhackvolunteers.databinding.AlertDeleteCardBinding
import com.astery.wildhackvolunteers.model.PersonFields


class ProfileQuestionDialog(layoutInflater: LayoutInflater, context: Context, field: PersonFields) {

    val binding = AlertDeleteCardBinding.inflate(layoutInflater, null, false)
    private val adb: AlertDialog.Builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
    private val alertDialog: AlertDialog

    init {
        adb.setView(binding.root)
        alertDialog = adb.create()
        binding.description.text = field.description
        binding.cancel.setOnClickListener { alertDialog.cancel() }
    }

    fun show() {
        alertDialog.show()
    }

    fun setOnOkListener(listener: () -> Unit):ProfileQuestionDialog {
        binding.submit.setOnClickListener {
            listener()
            alertDialog.cancel()
        }
        return this
    }
}