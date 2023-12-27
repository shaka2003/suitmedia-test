package com.dicoding.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.suitmediatest.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sentence: TextInputEditText
    private lateinit var name: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = binding.edName
        sentence = binding.edPalindrome
        val btnCheck = binding.btnCheck
        val btnNext = binding.btnNext

        btnCheck.setOnClickListener {
            val cleanedSentence = sentence.text?.toString()?.replace("\\s".toRegex(), "") ?: ""

            if (cleanedSentence.isEmpty()) {
                Toast.makeText(this, "please write a sentence", Toast.LENGTH_SHORT).show()
            } else {
                if (cleanedSentence.equals(cleanedSentence.reversed(), ignoreCase = true)) {
                    Toast.makeText(this, "Is Palindrome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
                }
            }

        }

        btnNext.setOnClickListener {
            if (name.text.toString().isEmpty()) {
                Toast.makeText(this, "please write a name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@MainActivity, Screen2::class.java)
                intent.putExtra("name", name.text.toString())
                startActivity(intent)
            }
        }
    }
}