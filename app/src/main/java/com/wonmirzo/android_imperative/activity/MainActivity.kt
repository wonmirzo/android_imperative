package com.wonmirzo.android_imperative.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wonmirzo.android_imperative.adapter.TVShowAdapter
import com.wonmirzo.android_imperative.databinding.ActivityMainBinding
import com.wonmirzo.android_imperative.model.TVShow
import com.wonmirzo.android_imperative.utils.Logger
import com.wonmirzo.android_imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initObserves()

        val lm = GridLayoutManager(this, 2)
        binding.rvHome.layoutManager = lm
        refreshAdapter(ArrayList())

        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (lm.findLastVisibleItemPosition() == viewModel.tvShowsFromApi.value!!.size - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPage = viewModel.tvShowPopular.value!!.pages
                    if (nextPage <= totalPage) {
                        viewModel.apiTVShowPopular(nextPage)
                    }
                }
            }
        })

        binding.btnFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }

        viewModel.apiTVShowPopular(1)
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.rvHome.adapter = adapter
    }

    private fun initObserves() {
        // Retrofit Related
        viewModel.tvShowsFromApi.observe(this) {
            Logger.d(TAG, it.size.toString())
            adapter.setNewTVShows(it)
        }

        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(this) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }


        // Room Related
    }
}