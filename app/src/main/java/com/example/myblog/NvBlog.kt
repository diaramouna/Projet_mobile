package com.example.myblog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NvBlog : AppCompatActivity() {
    private lateinit var titre: EditText
    private lateinit var description: EditText
    private lateinit var add_button: Button
    private lateinit var close: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_blog)

        titre = findViewById(R.id.titre)
        description = findViewById(R.id.contenu)
        add_button = findViewById(R.id.add_button)
        close = findViewById(R.id.button)

        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        add_button.setOnClickListener {
            val mydb = SQLiteHelper(this)
            mydb.addArticle(titre.text.toString().trim(), description.text.toString().trim())
        }
    }
}