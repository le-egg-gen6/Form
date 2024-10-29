package com.myproject.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.form.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupDatePicker()
        setupSubmitButton()
    }

    private fun setupSpinners() {
        // Dữ liệu mẫu cho các spinner
        val tinhAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Hà Nội", "TP.HCM", "Đà Nẵng"))
        binding.spnTinh.adapter = tinhAdapter

        val huyenAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Quận 1", "Quận 2", "Quận 3"))
        binding.spnHuyen.adapter = huyenAdapter

        val xaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Phường 1", "Phường 2", "Phường 3"))
        binding.spnXa.adapter = xaAdapter
    }

    private fun setupDatePicker() {
        binding.btnChonNgay.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                binding.tvNgaySinh.text = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            }, selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            if (validateForm()) {
                // Xử lý khi form hợp lệ
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Kiểm tra MSSV
        if (binding.edtMSSV.text.toString().trim().isEmpty()) {
            binding.edtMSSV.error = "Vui lòng nhập MSSV"
            isValid = false
        }

        // Kiểm tra Họ tên
        if (binding.edtHoTen.text.toString().trim().isEmpty()) {
            binding.edtHoTen.error = "Vui lòng nhập họ tên"
            isValid = false
        }

        // Kiểm tra giới tính
        if (binding.rgGioiTinh.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Kiểm tra Email
        val email = binding.edtEmail.text.toString().trim()
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Email không hợp lệ"
            isValid = false
        }

        // Kiểm tra Số điện thoại
        val phone = binding.edtPhone.text.toString().trim()
        if (phone.isEmpty() || !android.util.Patterns.PHONE.matcher(phone).matches()) {
            binding.edtPhone.error = "Số điện thoại không hợp lệ"
            isValid = false
        }

        // Kiểm tra Ngày sinh
        if (binding.tvNgaySinh.text.toString() == "Ngày sinh") {
            Toast.makeText(this, "Vui lòng chọn ngày sinh", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Kiểm tra có ít nhất một sở thích được chọn
        if (!binding.cbTheThao.isChecked && !binding.cbDienAnh.isChecked && !binding.cbAmNhac.isChecked) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một sở thích", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Kiểm tra đồng ý điều khoản
        if (!binding.cbDongY.isChecked) {
            Toast.makeText(this, "Vui lòng đồng ý với điều khoản", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
}