package com.ayesh.spectrum.extention

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.ayesh.spectrum.R
import com.ayesh.spectrum.presentation.listener.AlertClickListener
import com.xiteb.argo.presentation.state.DialogState



fun Activity.showDialogAlert(text: String?, type: DialogState = DialogState.General,listener: AlertClickListener? = null) {

    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_main)

    val msgTextView = dialog.findViewById<TextView>(R.id.msgTextView)
    val doneButton = dialog.findViewById<Button>(R.id.doneButton)
    msgTextView.text = text
    when (type) {
        is DialogState.Error -> {
            doneButton.setBackgroundColor(Color.RED)
        }
        else -> {
        }
    }


    doneButton.setOnClickListener {
        dialog.dismiss()
        listener?.let {
            listener.omAlertClick()
        }
    }
    dialog.show()
}
