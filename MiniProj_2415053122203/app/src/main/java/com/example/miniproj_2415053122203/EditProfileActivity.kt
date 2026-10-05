package com.example.miniproj_2415053122203

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniproj_2415053122203.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private var originalStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalStudent = intent.getSerializableExtra("STUDENT") as? Student
        if (originalStudent != null) {
            binding.edtName.setText(originalStudent!!.name)
            binding.edtClass.setText(originalStudent!!.className)
            binding.edtGpa.setText(originalStudent!!.gpa.toString())
        }

        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val className = binding.edtClass.text.toString().trim()
            val gpaStr = binding.edtGpa.text.toString()
            val gpa = gpaStr.toDoubleOrNull()

            if (name.isEmpty() || gpa == null || gpa < 0.0 || gpa > 4.0) {
                Toast.makeText(this, "Vui lòng nhập tên và GPA hợp lệ (0.0-4.0)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (originalStudent != null) {
                val updatedStudent = originalStudent!!.copy(name = name, className = className, gpa = gpa)
                val resultIntent = Intent()
                resultIntent.putExtra("UPDATED", updatedStudent)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}