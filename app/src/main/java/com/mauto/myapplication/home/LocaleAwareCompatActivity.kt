package com.mauto.myapplication.home

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.mauto.myapplication.utils.localefolder.LocaleHelperActivityDelegateImpl

import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


open class LocaleAwareCompatActivity : AppCompatActivity() {
    private val localeDelegate = LocaleHelperActivityDelegateImpl()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localeDelegate.onCreate(this)
    }

    override fun onResume() {
        super.onResume()
        localeDelegate.onResumed(this)
    }

    override fun onPause() {
        super.onPause()
        localeDelegate.onPaused()
    }

    open fun updateLocale(locale: Locale) {
        localeDelegate.setLocale(this, locale)
    }

    @InternalCoroutinesApi
    open fun onMapReady(mGoogleMap: GoogleMap?) {
    }
}