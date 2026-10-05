package com.example.miniproj_2415053122203

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.miniproj_2415053122203.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // Khởi tạo đối tượng chứa thông tin cá nhân của bạn
    private var student = Student("2415053122203", "Hà Phước Bình", "Kỹ thuật phần mềm", 3.8)

    // 1. Launcher nhận kết quả chỉnh sửa hồ sơ
    private val editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            (res.data?.getSerializableExtra("UPDATED") as? Student)?.let {
                student = it
                bindData(student)
                Toast.makeText(this, "Đã lưu thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 2. Launcher lấy ảnh từ Gallery
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.imgAvatar.setImageURI(it)
            Toast.makeText(this, "Đã đổi avatar!", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Launcher xin quyền Camera
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        val msg = if (isGranted) "Đã cấp quyền Camera!" else "Bị từ chối quyền!"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindData(student)

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("STUDENT", student)
            }
            editLauncher.launch(intent)
        }

        binding.btnChangeAvatar.setOnClickListener { galleryLauncher.launch("image/*") }

        binding.btnRequestCamera.setOnClickListener { cameraLauncher.launch(Manifest.permission.CAMERA) }

        binding.btnCallHotline.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:0905123456") }
            startActivity(intent)
        }
    }

    private fun bindData(s: Student) {
        binding.tvName.text = s.name
        binding.tvDetails.text = "MSSV: ${s.id} - Lớp: ${s.className}"
        binding.tvGpaBadge.text = "GPA: ${s.gpa}"
    }
}