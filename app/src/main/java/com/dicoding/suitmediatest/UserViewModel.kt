package com.dicoding.suitmediatest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.suitmediatest.api.ApiConfig
import com.dicoding.suitmediatest.api.DataItem
import com.dicoding.suitmediatest.api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<List<DataItem>>()
    val users: LiveData<List<DataItem>> get() = _users

    private var page = 1
    private var isLoading = false

    fun fetchUsersIncrement(perPage: Int) {
        if (isLoading) {
            return
        }
        isLoading = true
        val service = ApiConfig.getApiService()
        val call = service.getUsers(page, perPage)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _users.value = response.body()?.data?.filterNotNull()
                    if (page < (response.body()?.totalPages ?: 0)) {
                        page++
                    }
                }
                isLoading = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // Handle the error
                isLoading = false
            }
        })
    }

    fun resetPage() {
        page = 1
    }

}
