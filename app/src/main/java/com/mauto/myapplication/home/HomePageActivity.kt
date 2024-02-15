package com.mauto.myapplication.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.mauto.myapplication.R
import com.mauto.myapplication.home.adapter.PageAdapter
import com.mauto.myapplication.home.livedata.HomeLiveData
import com.mauto.myapplication.home.model.CancelPaymentResponse
import com.mauto.myapplication.login.LoginActivity
import com.mauto.myapplication.prebooking.PreBookingHistoryActivity
import com.mauto.myapplication.profile.AboutActivity
import com.mauto.myapplication.profile.FeedbackActivity
import com.mauto.myapplication.profile.ProfileActivity
import com.mauto.myapplication.profile.SupportActivity
import com.mauto.myapplication.utils.MAutoApplication
import com.mauto.myapplication.utils.MAutoSharedPref
import com.mauto.myapplication.utils.ToastUtils
import de.hdodenhof.circleimageview.CircleImageView


class HomePageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var mDrawerLayout: DrawerLayout;
    lateinit var pd: ProgressDialog
    lateinit var model:HomeLiveData
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        instances2 = this
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = PageAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

         model = ViewModelProvider(this)[HomeLiveData::class.java]


        pd = ProgressDialog(this@HomePageActivity)
        pd.setMessage("Loading...")

        mDrawerLayout = findViewById<DrawerLayout>(R.id.my_drawer_layout)

        val mNavigationView = findViewById<NavigationView>(R.id.nav_view) as NavigationView

        mNavigationView?.setNavigationItemSelectedListener(this)

        val hamMenu: ImageView = findViewById(R.id.home_list)
        val profileImg: CircleImageView = findViewById(R.id.profile_img)
        val username: TextView = findViewById(R.id.username)

        hamMenu.setOnClickListener {

            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
                mDrawerLayout.openDrawer(Gravity.RIGHT) else mDrawerLayout.closeDrawer(
                Gravity.LEFT
            )
        }


        val profileInfo = MAutoSharedPref.getAppPrefs(applicationContext).profileInfo


        username.text = profileInfo!!.name

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.user_profiles)
            .error(R.drawable.user_profiles)

        Glide.with(this).load(profileInfo.image).apply(options).into(profileImg);

        val navigationView: NavigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerview: View = navigationView.inflateHeaderView(R.layout.nav_header_main);
        val closeView: ImageView = headerview.findViewById(R.id.closeView) as ImageView
        val userName: TextView = headerview.findViewById(R.id.nav_profile_name) as TextView
        val userImage: ImageView = headerview.findViewById(R.id.nav_profile_img) as ImageView
     closeView.setOnClickListener{
         if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
             mDrawerLayout.closeDrawer(Gravity.RIGHT)
         }
     }

        userName.text=profileInfo!!.name



        Glide.with(this).load(profileInfo.image).apply(options).into(userImage);


        userImage.setOnClickListener{

            val i = Intent(this@HomePageActivity, ProfileActivity::class.java)
            startActivity(i)
        }




    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item != null && item.itemId === android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT)
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT)
            }
        }
        return false
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {

            R.id.nav_booking_history -> {

                val i = Intent(this@HomePageActivity, PreBookingHistoryActivity::class.java)
                startActivity(i)

            }
//            R.id.nav_cancel_history -> {
//
//                val i = Intent(this@HomePageActivity, CancelHistoryActivity::class.java)
//                startActivity(i)
//
//            }
            R.id.nav_about_us -> {

                val i = Intent(this@HomePageActivity, AboutActivity::class.java)
                startActivity(i)
            }

            R.id.nav_feed_back -> {

                val i = Intent(this@HomePageActivity, FeedbackActivity::class.java)
                startActivity(i)
            }


            R.id.nav_support_us -> {

                val i = Intent(this@HomePageActivity, SupportActivity::class.java)
                startActivity(i)
            }
            R.id.nav_about_logout -> {
                showAlertDialog()
            }
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT)
        } else {
            mDrawerLayout.openDrawer(Gravity.RIGHT)
        }
        return true
    }


    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@HomePageActivity)
        alertDialog.setTitle("Information")
        alertDialog.setMessage("Do you want to log out?")
        alertDialog.setPositiveButton(
            "Yes"
        ) { _, _ ->


            logoutFromApp()

        }
        alertDialog.setNegativeButton(
            "No"
        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }


    private fun logoutFromApp() {
        try {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
         pd.show()
        //Calling API from Observer

        if (model == null)
            model = ViewModelProvider(this)[HomeLiveData::class.java]

        model.logout()
            ?.observe(this, Observer { this.setStatus(it) })


    }

    private fun setStatus(userStatus: CancelPaymentResponse?) {
        if (userStatus!!.status.equals("1")) {
            if (pd != null)
                pd.dismiss()

            MAutoSharedPref.getAppPrefs(
                MAutoApplication.getInstance().applicationContext
            ).clearAllSharedPrefs()

            ToastUtils.showToast(applicationContext, "Logged out successfully")


            val i = Intent(this@HomePageActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        } else {
            if (pd != null)
                pd.dismiss()
            MAutoSharedPref.getAppPrefs(
                MAutoApplication.getInstance().applicationContext
            ).clearAllSharedPrefs()

            ToastUtils.showToast(applicationContext, "Logged out successfully")


            val i = Intent(this@HomePageActivity, LoginActivity::class.java)
            startActivity(i)
            finish()        }

    }

    companion object {
        private var instances2: HomePageActivity? = null
        fun getInstance(): HomePageActivity? {
            return instances2
        }
    }

    fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }


}