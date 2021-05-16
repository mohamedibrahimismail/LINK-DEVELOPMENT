package com.example.link.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.example.link.ui.base.BaseActivity
import com.example.link.R
import com.example.link.adapters.NavigationRVAdapter
import com.example.link.adapters.NewsFeedListAdapter
import com.example.link.model.main.ArticlesModelItem
import com.example.link.model.main.NavigationItemModel
import com.example.link.viewModels.MainVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_no_internet_connection.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "MainActivity"

class MainActivity : BaseActivity() {

    private val mainVM: MainVM by viewModel()

    override fun getActivityView(): Int = R.layout.activity_main

    override fun afterInflation(savedInstance: Bundle?) {

        setupViews()
        setupObservers()
        mainVM.getAllArticles()

    }

    private fun setupViews() {
        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        // Set Navigation Drawer
        setupNavigationDrawer()
    }

    private fun setupObservers() {
        mainVM.loading.observe(this, Observer {
            showLoading(it)
        })
        mainVM.AllArticles.observe(this, Observer {
            setupNewsFeedAdapter(it)
        })
        mainVM.ErrorHappened.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.sorry_server_error_happened),
                    Toast.LENGTH_LONG
                ).show()
                mainVM.ErrorHappened.value = false
            }

        })

    }

    private fun setupNavigationDrawer() {
        setupNavigationAdapter()
        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            activity_main_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupNavigationAdapter() {
        var adapter: NavigationRVAdapter = NavigationRVAdapter(getNavigationDrawerListItems())
        navigation_rv.adapter = adapter
    }

    private fun getNavigationDrawerListItems(): ArrayList<NavigationItemModel> {
        //Add Navigation Models to array list
        return arrayListOf<NavigationItemModel>(
            NavigationItemModel(
                R.drawable.explore,
                resources.getString(R.string.explore)
            ),
            NavigationItemModel(
                R.drawable.live,
                resources.getString(R.string.live_chat)
            ),
            NavigationItemModel(
                R.drawable.gallery,
                resources.getString(R.string.gallery)
            ),
            NavigationItemModel(
                R.drawable.wishlist,
                resources.getString(R.string.wishlist)
            ),
            NavigationItemModel(
                R.drawable.e_magazine,
                resources.getString(R.string.e_magazine)
            ),

            )
    }

    private fun setupNewsFeedAdapter(it: MutableList<ArticlesModelItem>) {
        var newsAdapter = NewsFeedListAdapter(this, it)
        news_feed_recyclerview.adapter = newsAdapter
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

}
