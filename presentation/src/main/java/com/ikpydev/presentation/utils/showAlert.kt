package com.ikpydev.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.ikpydev.presentation.databinding.AddContactBinding

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

fun addContact(
    context: Context,
    layoutInflater: LayoutInflater,
    send: (name: String, phone: String) -> Unit
) {
    val view = AddContactBinding.inflate(layoutInflater, null, false)
    val alertDialog = AlertDialog.Builder(context)
        .setView(view.root)
        .setTitle("Add contact")
        .setPositiveButton("Add") { _, _ ->
            if (view.name.text.toString().isNullOrBlank().not() && view.phone.text.toString()
                    .isNullOrBlank().not()
            ) {
                val name = view.name.text.toString()
                val phone = view.phone.text.toString()
                send(name, phone)
            } else {
                Toast.makeText(context, "There should be no head", Toast.LENGTH_SHORT).show()
            }

        }
        .setNegativeButton("Canceled") { _, _ -> }
        .create()
    alertDialog.show()


}

