package com.example.siakad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.siakad.model.Post
import com.example.siakad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private lateinit var tvLatestPost: TextView
    private lateinit var pbDashboard: ProgressBar
    private lateinit var tvError: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLatestPost = view.findViewById(R.id.tvLatestPost)
        pbDashboard = view.findViewById(R.id.pbDashboard)
        tvError = view.findViewById(R.id.tvDashboardError)

        loadLatestPost()
    }

    private fun loadLatestPost() {
        pbDashboard.visibility = View.VISIBLE
        tvLatestPost.visibility = View.GONE
        tvError.visibility = View.GONE

        RetrofitClient.apiService.getAllPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                pbDashboard.visibility = View.GONE
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (!posts.isNullOrEmpty()) {
                        val latest = posts.take(3)
                        val text = latest.joinToString("\n\n") { post ->
                            "• ${post.title.replaceFirstChar { it.uppercase() }}"
                        }
                        tvLatestPost.text = text
                        tvLatestPost.visibility = View.VISIBLE
                    }
                } else {
                    showError("Gagal memuat data (${response.code()})")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                pbDashboard.visibility = View.GONE
                showError("Koneksi gagal: ${t.localizedMessage}")
            }
        })
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
        tvLatestPost.visibility = View.GONE
    }
}
