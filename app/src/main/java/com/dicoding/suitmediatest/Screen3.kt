package com.dicoding.suitmediatest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.suitmediatest.databinding.ActivityScreen3Binding

class Screen3 : AppCompatActivity() {
    private lateinit var binding: ActivityScreen3Binding
    private lateinit var rvUser: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var toolbar3: Toolbar
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreen3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar3 = binding.toolbarScreen3
        setSupportActionBar(toolbar3)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val firstName = data?.getStringExtra("firstName")
                    val lastName = data?.getStringExtra("lastName")
                    val intent = Intent()
                    intent.putExtra("firstName", firstName)
                    intent.putExtra("lastName", lastName)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        swipeRefreshLayout = binding.swipeRefreshLayout
        rvUser = binding.rvListUser
        val adapter = UserAdapter(this)
        rvUser.adapter = adapter
        rvUser.layoutManager = LinearLayoutManager(this)

        viewModel.users.observe(this) { users ->
            adapter.setList(users)
        }
        viewModel.fetchUsersIncrement(10)

        rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchUsersIncrement(10)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.resetPage()
            viewModel.fetchUsersIncrement(10)
            swipeRefreshLayout.isRefreshing = false
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