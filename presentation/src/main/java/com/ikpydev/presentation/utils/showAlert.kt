package com.ikpydev.presentation.utils

import android.app.AlertDialog
import android.content.Context

fun showAlert(
    context: Context,
    title: String = "",
    message: String = "",
    onYes: Runnable? = null,
    onNo: Runnable? = null
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
            onYes?.run()
        }

        alertDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->
            onNo?.run()
        }
        alertDialogBuilder.show()
    }