package com.example.bbmr_project.alertdialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.bbmr_project.BasketActivity
import com.example.bbmr_project.R

class CustomDialog(context: Context) : Dialog(context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog)
        val btnYes = findViewById<Button>(R.id.btnYesAD)
        val btnNo = findViewById<Button>(R.id.btnNoAD)

        btnYes.setOnClickListener {
            // 여기서 인텐트가 실행이 되지 않음. 메인 액티비티에서 인텐트를 사용해야 할듯?
            val intent = Intent(context, BasketActivity::class.java)
            context.startActivity(intent)
            dismiss()
        }
        btnNo.setOnClickListener {
            dismiss()
        }

    }

}