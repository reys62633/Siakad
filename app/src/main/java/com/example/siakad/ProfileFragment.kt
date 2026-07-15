package com.example.siakad

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.siakad.model.User
import com.example.siakad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var pbProfile: ProgressBar
    private lateinit var scrollContent: View
    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileUsername: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var tvProfilePhone: TextView
    private lateinit var tvProfileInitial: TextView
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pbProfile = view.findViewById(R.id.pbProfile)
        scrollContent = view.findViewById(R.id.scrollContent)
        tvProfileName = view.findViewById(R.id.tvProfileName)
        tvProfileUsername = view.findViewById(R.id.tvProfileUsername)
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail)
        tvProfilePhone = view.findViewById(R.id.tvProfilePhone)
        tvProfileInitial = view.findViewById(R.id.tvProfileInitial)
        btnLogout = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        pbProfile.visibility = View.VISIBLE
        scrollContent.visibility = View.GONE

        // Load user #1 as the logged-in student profile
        RetrofitClient.apiService.getUserById(1).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                pbProfile.visibility = View.GONE
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        tvProfileName.text = it.name
                        tvProfileUsername.text = "@${it.username}"
                        tvProfileEmail.text = it.email
                        tvProfilePhone.text = it.phone
                        tvProfileInitial.text = it.name.first().uppercaseChar().toString()
                    }
                    scrollContent.visibility = View.VISIBLE
                } else {
                    // Show default data on error
                    showDefaultProfile()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                pbProfile.visibility = View.GONE
                showDefaultProfile()
            }
        })
    }

    private fun showDefaultProfile() {
        tvProfileName.text = "Muhammad Esa priangga"
        tvProfileUsername.text = "@esa.priangga"
        tvProfileEmail.text = "230201055@student.aisyah.ac.id"
        tvProfilePhone.text = "08123456789"
        tvProfileInitial.text = "M"
        scrollContent.visibility = View.VISIBLE
    }
}
