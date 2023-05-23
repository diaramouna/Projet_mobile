package com.example.myblog

import android.widget.LinearLayout.LayoutParams
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {
    private lateinit var linearLayout: LinearLayout
    private lateinit var baseline_add: View
    private lateinit var mydb: SQLiteHelper
    private lateinit var posts: List<Article>
    private lateinit var scroll: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mydb = SQLiteHelper(this)
        scroll = findViewById(R.id.scrollView)
        posts = mydb.getAllArticles()

        Log.d("postNumber", posts.size.toString())

        for (i in posts.indices) {
            val cardContainer = CardView(scroll.context)
            cardContainer.tag = posts[i].id.toString()
            cardContainer.setContentPadding(16, 16, 16, 16)
            cardContainer.cardElevation = 4.0f
            cardContainer.radius = 12.0f
            cardContainer.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 16, 0, 16)
            cardContainer.layoutParams = layoutParams

            val container = LinearLayout(cardContainer.context)
            container.orientation = LinearLayout.VERTICAL
            container.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )

            val title = TextView(container.context)
            val desc = TextView(container.context)
            title.text = posts[i].titre
            title.setTextColor(Color.BLACK)
            title.setTextAppearance(R.style.h3)

            desc.setTextAppearance(R.style.txt1)
            desc.text = posts[i].description

            container.addView(title)
            container.addView(desc)
            cardContainer.addView(container)
            scroll.addView(cardContainer)

            cardContainer.setOnClickListener {
                val intent = Intent(this@MainActivity, Articles::class.java)
                intent.putExtra("id", cardContainer.tag.toString())
                startActivity(intent)
            }
        }

        linearLayout = findViewById(R.id.linearlayout)
        baseline_add = findViewById(R.id.nvBlog)
        baseline_add.setOnClickListener {
            val intent = Intent(this@MainActivity, NvBlog::class.java)
            startActivity(intent)
        }
    }

}