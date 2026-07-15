package com.example.siakad

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siakad.model.Post
import com.example.siakad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPengumumanActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvBody: TextView
    private lateinit var tvTag: TextView
    private lateinit var tvId: TextView
    private lateinit var tvUserId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pengumuman)

        // Setup action bar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Pengumuman"

        tvTitle = findViewById(R.id.tvDetailTitle)
        tvBody = findViewById(R.id.tvDetailBody)
        tvTag = findViewById(R.id.tvDetailTag)
        tvId = findViewById(R.id.tvDetailId)
        tvUserId = findViewById(R.id.tvDetailUserId)

        val postId = intent.getIntExtra("POST_ID", -1)
        val postTitle = intent.getStringExtra("POST_TITLE") ?: ""
        val postBody = intent.getStringExtra("POST_BODY") ?: ""
        val postUserId = intent.getIntExtra("POST_USER_ID", 0)

        // Display data passed from intent (instant, no extra API call needed)
        tvTag.text = "Pengumuman #$postId"
        tvTitle.text = postTitle.replaceFirstChar { it.uppercase() }
        tvBody.text = postBody
        tvId.text = "#$postId"
        tvUserId.text = "Admin Bagian $postUserId"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
