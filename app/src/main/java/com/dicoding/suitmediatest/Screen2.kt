package com.dicoding.suitmediatest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.suitmediatest.databinding.ActivityScreen2Binding

class Screen2 : AppCompatActivity() {
    private lateinit var binding: ActivityScreen2Binding
    private lateinit var showName: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var btnChoose: Button
    private lateinit var selectedUser: TextView
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreen2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        showName = binding.tvShowName
        btnChoose = binding.btnChooseUser
        selectedUser = binding.tvSelectedUserName

        toolbar = binding.toolbarScreen2
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val name = intent.getStringExtra("name")
        showName.text = name

        btnChoose.setOnClickListener {
            val intent = Intent(this@Screen2, Screen3::class.java)
            resultLauncher.launch(intent)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val firstName = data?.getStringExtra("firstName")
                    val lastName = data?.getStringExtra("lastName")
                    selectedUser.text = "$firstName $lastName"
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}