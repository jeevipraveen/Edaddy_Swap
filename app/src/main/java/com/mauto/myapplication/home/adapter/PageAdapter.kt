package com.mauto.myapplication.home.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mauto.myapplication.home.PreBookingFragment
import com.mauto.myapplication.home.RefundFragment

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 1;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return PreBookingFragment()
            }
            1 -> {
                return RefundFragment()
            }

            else -> {
                return PreBookingFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Collection"
            }
            1 -> {
                return "Cancellation"
            }

        }
        return super.getPageTitle(position)
    }

}
