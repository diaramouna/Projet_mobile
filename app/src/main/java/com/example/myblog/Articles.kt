package com.example.myblog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Articles : AppCompatActivity() {
    private lateinit var back: FloatingActionButton
    private lateinit var mydb: SQLiteHelper
    private lateinit var tt: TextView
    private lateinit var cc: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        mydb = SQLiteHelper(this)

        val intent = intent
        val id = intent.getStringExtra("id")?.toLong()
        Log.d("id recuperer", id.toString())

        val p = id?.let { mydb.getArticleById(it) }

        back = findViewById(R.id.back)
        tt = findViewById(R.id.title)
        cc = findViewById(R.id.content)

        tt.text = p?.titre
        cc.text = p?.description

        back.setOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }
    }
}