package com.example.siakad

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siakad.adapter.PengumumanAdapter
import com.example.siakad.model.Post
import com.example.siakad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutError: LinearLayout
    private lateinit var tvError: TextView
    private lateinit var btnRetry: Button
    private lateinit var searchView: SearchView
    private lateinit var adapter: PengumumanAdapter

    private var allPosts: List<Post> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvPengumuman)
        progressBar = view.findViewById(R.id.progressBar)
        layoutError = view.findViewById(R.id.layoutError)
        tvError = view.findViewById(R.id.tvError)
        btnRetry = view.findViewById(R.id.btnRetry)
        searchView = view.findViewById(R.id.searchView)

        adapter = PengumumanAdapter { post ->
            // Navigate to detail
            val intent = Intent(requireContext(), DetailPengumumanActivity::class.java).apply {
                putExtra("POST_ID", post.id)
                putExtra("POST_TITLE", post.title)
                putExtra("POST_BODY", post.body)
                putExtra("POST_USER_ID", post.userId)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Search listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filterPosts(newText ?: "")
                return true
            }
        })

        btnRetry.setOnClickListener { loadPosts() }

        loadPosts()
    }

    private fun loadPosts() {
        progressBar.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
        recyclerView.visibility = View.GONE

        RetrofitClient.apiService.getAllPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    allPosts = response.body() ?: emptyList()
                    adapter.submitList(allPosts)
                    recyclerView.visibility = View.VISIBLE
                } else {
                    showError("Server error: ${response.code()}\nGagal memuat data pengumuman.")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                progressBar.visibility = View.GONE
                showError("❌ Koneksi gagal!\n\nPastikan perangkat terhubung ke internet.\n\n${t.localizedMessage}")
            }
        })
    }

    private fun filterPosts(query: String) {
        val filtered = if (query.isEmpty()) {
            allPosts
        } else {
            allPosts.filter { it.title.contains(query, ignoreCase = true) }
        }
        adapter.submitList(filtered)
    }

    private fun showError(message: String) {
        tvError.text = message
        layoutError.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}
