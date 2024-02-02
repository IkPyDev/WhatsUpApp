package com.ikpydev.presentation.utils


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.text.InputType
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Group
import com.ikpydev.domain.model.User
import com.ikpydev.domain.model.UserResult
import com.ikpydev.presentation.R
import java.util.UUID


class DialogHelper(private val context: Context) {

    fun showAlert(
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
        send: (name: String, phone: String) -> Unit
    ) {
        val alertDialog = AlertDialog.Builder(context)
        val inputName = EditText(context)
        val inputPhone = EditText(context)

        inputName.hint = "Enter name"
        inputPhone.hint = "Enter phone number"

        alertDialog.setTitle("Add Contact")
        alertDialog.setView(inputName)
        alertDialog.setView(inputPhone)

        alertDialog.setPositiveButton("Add") { _, _ ->
            val name = inputName.text.toString()
            val phone = inputPhone.text.toString()
            send(name, phone)
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun showUserDialog2(users: List<Chat>,userResult:(result: UserResult) -> Unit){
        val checkedUsers = mutableListOf<User>()
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val scrollView = ScrollView(context)
        scrollView.addView(layout)
        var groupName = ""
        val textTitle = TextView(context)
        textTitle.text = "New Group Name"

        val input = EditText(context)
        input.maxHeight = 20
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.hint = "Group name .."

        layout.addView(textTitle)
        layout.addView(input)
        users.forEach { chat ->
            val checkBox = CheckBox(context)
            checkBox.text = chat.user.name
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedUsers.add(chat.user)
                } else {
                    checkedUsers.remove(chat.user)
                }
            }
            layout.addView(checkBox)
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle("New Group")
        builder.setView(scrollView)
        builder.setPositiveButton("OK") { _, _ ->
            groupName = input.text.toString()
            val userIds = checkedUsers.map { it.id }
            userResult(UserResult(userIds, groupName))
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }



}

@SuppressLint("ClickableViewAccessibility")
fun showImageDialog(uri: Uri, imageContext: Context) {
    val dialog = Dialog(imageContext, R.style.Theme_WhatsUpApp)
    dialog.setContentView(R.layout.dialog_fullscreen_image)
    val imageView = dialog.findViewById<ImageView>(R.id.fullscreen_image)
    val backButton = dialog.findViewById<ImageView>(R.id.back_button)

    // Glide kutubxonasini ishlatib rasmni yuklash
    Glide.with(imageContext).load(uri).into(imageView)

    // ImageView'ga zoom in/zoom out qilish imkoniyatini qo'shish
    imageView.setOnTouchListener(ImageMatrixTouchHandler(imageContext))

    // Orqaga qaytish tugmasini sozlash
    backButton.setImageResource(R.drawable.ic_back)
    backButton.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}


//fun showAlert(
//    context: Context,
//    title: String = "",
//    message: String = "",
//    onYes: Runnable? = null,
//    onNo: Runnable? = null
//) {
//    val alertDialogBuilder = AlertDialog.Builder(context)
//    alertDialogBuilder.setTitle(title)
//    alertDialogBuilder.setMessage(message)
//    alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
//        onYes?.run()
//    }
//
//    alertDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->
//        onNo?.run()
//    }
//    alertDialogBuilder.show()
//}
//
//fun addContact(
//    context: Context,
//    layoutInflater: LayoutInflater,
//    send: (name: String, phone: String) -> Unit
//) {
//    val view = AddContactBinding.inflate(layoutInflater, null, false)
//    val alertDialog = AlertDialog.Builder(context)
//        .setView(view.root)
//        .setTitle("Add contact")
//        .setPositiveButton("Add") { _, _ ->
//            if (view.name.text.toString().isNullOrBlank().not() && view.phone.text.toString()
//                    .isNullOrBlank().not()
//            ) {
//                val name = view.name.text.toString()
//                val phone = view.phone.text.toString()
//                send(name, phone)
//            } else {
//                Toast.makeText(context, "There should be no head", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        .setNegativeButton("Canceled") { _, _ -> }
//        .create()
//    alertDialog.show()
//
//}
//
//
//
//@SuppressLint("SetTextI18n")
//fun showUserDialog2(users: List<Chat>, context: Context, userResult:(group:Group) -> Unit){
//    val checkedUsers = mutableListOf<User>()
//    val layout = LinearLayout(context)
//    layout.orientation = LinearLayout.VERTICAL
//
//    val scrollView = ScrollView(context)
//    scrollView.addView(layout)
//    var groupName = ""
//    val textTitle = TextView(context)
//    textTitle.text = "New Group Name"
//
//    val input = EditText(context)
//    input.maxHeight = 20
//    input.inputType = InputType.TYPE_CLASS_TEXT
//    input.hint = "Group name .."
//
//    layout.addView(textTitle)
//    layout.addView(input)
//    users.forEach { chat ->
//        val checkBox = CheckBox(context)
//        checkBox.text = chat.user.name
//        checkBox.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                checkedUsers.add(chat.user)
//            } else {
//                checkedUsers.remove(chat.user)
//            }
//        }
//        layout.addView(checkBox)
//    }
//
//    val builder = AlertDialog.Builder(context)
//    builder.setTitle("New Group")
//    builder.setView(scrollView)
//    builder.setPositiveButton("OK") { _, _ ->
//        groupName = input.text.toString()
//        val userIds = checkedUsers.map { it.id }
//        userResult(Group(_id = UUID.randomUUID().toString(), name = groupName, members = userIds))
//    }
//
//    builder.setNegativeButton("Bekor qilish") { _, _ ->
//        // Bekor qilish tugmasi bosilganda ishlaydi
//    }
//
//    builder.create().show()
//}
//
//fun addGroups(
//    context: Context,
//    layoutInflater: LayoutInflater,
//    send: (name: String, phone: String) -> Unit
//) {
//    val view = AddContactBinding.inflate(layoutInflater, null, false)
//    val alertDialog = AlertDialog.Builder(context)
//        .setView(view.root)
//        .setTitle("Add contact")
//        .setPositiveButton("Add") { _, _ ->
//            if (view.name.text.toString().isNullOrBlank().not() && view.phone.text.toString()
//                    .isNullOrBlank().not()
//            ) {
//                val name = view.name.text.toString()
//                val phone = view.phone.text.toString()
//                send(name, phone)
//            } else {
//                Toast.makeText(context, "There should be no head", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        .setNegativeButton("Canceled") { _, _ -> }
//        .create()
//    alertDialog.show()
//
//
//}


//
//fun showUserDialog(users: List<User>, context: Context): UserGroups {
//    val checkedUsers = mutableListOf<User>()
//    val checkedItems = BooleanArray(users.size)
//
//    val userNames = users.map { it.userName }.toTypedArray()
//
//    val builder = AlertDialog.Builder(context)
//    builder.setTitle("Foydalanuvchilarni tanlang")
//    builder.setMultiChoiceItems(userNames, checkedItems) { _, which, isChecked ->
//        if (isChecked) {
//            checkedUsers.add(users[which])
//        } else if (checkedUsers.contains(users[which])) {
//            checkedUsers.remove(users[which])
//        }
//    }
//
//    var groupName = ""
//    val input = EditText(context)
//    builder.setView(input)
//    builder.setPositiveButton("OK") { _, _ ->
//        groupName = input.text.toString()
//    }
//
//    builder.setNegativeButton("Bekor qilish") { _, _ ->
//        // Bekor qilish tugmasi bosilganda ishlaydi
//    }
//
//    builder.create().show()
//
//    val userIds = checkedUsers.map { it.userId }
//    return UserGroups(userIds, groupName)
//}





