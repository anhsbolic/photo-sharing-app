package com.imageworld.ui.activity.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.imageworld.R
import com.imageworld.ui.activity.addPost.AddPostActivity
import com.imageworld.ui.fragment.home.HomeFragment
import com.imageworld.ui.fragment.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var currentFragment : Fragment
    private lateinit var lastFragment : Fragment

    private var isFirstVisit : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(dashboardToolbar)
        dashboardToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        actionBar = supportActionBar!!
        actionBar.title = null

        //Fragment backStack management
        supportFragmentManager.addOnBackStackChangedListener {
            lastFragment = if(supportFragmentManager.backStackEntryCount > 0){
                val fragments = supportFragmentManager.fragments
                fragments[fragments.size-1]
            }else{
                val fragments = supportFragmentManager.fragments
                fragments[0]
            }
        }

        if (intent.hasExtra(NAV_TO_PROFILE)) {
            val isNavToProfile = intent.getBooleanExtra(NAV_TO_PROFILE,false)
            if (isNavToProfile) {
                displaySelectedScreen(R.id.nav_profile)
            } else {
                displaySelectedScreen(R.id.nav_home)
            }
        } else {
            displaySelectedScreen(R.id.nav_home)
        }

        dashboardBottomNav.setOnNavigationItemSelectedListener {item: MenuItem ->
            displaySelectedScreen(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun displaySelectedScreen(itemId: Int) {

        when(itemId){
            R.id.nav_home->{
                currentFragment = HomeFragment()
            }
            R.id.nav_camera->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                    } else {
                        goToAddPost()
                    }
                } else {
                    goToAddPost()
                }
            }
            R.id.nav_profile->{
                currentFragment = ProfileFragment()
            }
            else -> {
                currentFragment = HomeFragment()
            }
        }

        if(isFirstVisit){
            isFirstVisit = false
            lastFragment = currentFragment
            supportFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.dashboardFrameContainer, currentFragment)
                    .commit()
        }else{
            if(currentFragment.javaClass.simpleName != lastFragment.javaClass.simpleName){
                supportFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.dashboardFrameContainer, currentFragment,
                                currentFragment.javaClass.simpleName)
                        .addToBackStack(lastFragment.javaClass.simpleName)
                        .commit()
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            super.onBackPressed()
        }else{
            AlertDialog.Builder(this@DashboardActivity)
                    .setMessage(R.string.dashboard_dialog_exit_message)
                    .setPositiveButton(R.string.dashboard_dialog_exit_positive,{ _ , _ ->
                        finish()
                    })
                    .setNegativeButton(R.string.dashboard_dialog_exit_negative, null)
                    .show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToAddPost()
            }
        }
    }

    private fun goToAddPost(){
        val intentAddPost = Intent(this@DashboardActivity, AddPostActivity::class.java)
        startActivity(intentAddPost)
    }

    companion object {
        const val NAV_TO_PROFILE = "NavToProfile"
    }
}
