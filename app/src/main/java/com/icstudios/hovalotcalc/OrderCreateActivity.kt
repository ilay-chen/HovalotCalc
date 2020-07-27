package com.icstudios.hovalotcalc

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OrderCreateActivity : FragmentActivity(), ViewPagerNavigation {

    companion object {
        @JvmStatic
        var newOrder = OrderObject()
    }

    private lateinit var pagerAdapter: OrderCreateActivity.ScreenSlidePagerAdapter
    lateinit var viewPager: ViewPager2
    var page = 0
    var id : String? = null

    val mClientDetailsFragment = ClientDetailsFragment()
    val mAddressAndDateFragment = AddressAndDateFragment()
    val mExtraDetailsFragment = ExtraDetailsFragment()
    val mItemsAndRoomsFragment = ItemsAndRoomsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_create)

        val intent = intent
        id = intent.getStringExtra("id")
        if(id!=null&& id != "")
            setCurrentOrder()
        else
            newOrder = OrderObject()


        // Need to ask for write permissions on SDK 23 and up, this is ignored on older versions
        if (ContextCompat.checkSelfPermission(this@OrderCreateActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@OrderCreateActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)

//        mSingInOnboardingFragment = SignInFragment()
//        mSingInOnboardingFragment.setOnSignInListener {
//            onProgress(3)
//        }

        // The pager adapter, which provides the pages to the view pager widget.
        pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        dotsIndicator.setViewPager2(viewPager)

        // fetch remote config so will have updated ui
        //Firebase.remoteConfig.fetchAndActivate()

        viewPager.setCurrentItem(page, true)
        onProgress(4)
    }

    fun setCurrentOrder()
    {
        for(orderObject in appData.allOrders)
        {
            if(orderObject.id == id)
                newOrder = orderObject
        }
    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        var mProgress = 4

        override fun getItemCount(): Int = mProgress

        override fun createFragment(position: Int): Fragment {
            if (position == 0) {
                //AnalyticsManager.logPage("IntroOnboardingFragment")
                return mClientDetailsFragment
            }
            else if(position == 1)
            {
                //AnalyticsManager.logPage("mSingInOnboardingFragment")
                return mAddressAndDateFragment
            }
            else if (position == 2){
                //AnalyticsManager.logPage("mVideoExplanationFragment")
                return mExtraDetailsFragment
            }
            else if (position == 3){
                //AnalyticsManager.logPage("mPictureSmileExplanationFragment")
                return mItemsAndRoomsFragment
            }
//            else if (position == 4) {
////                AnalyticsManager.logPage("mSmileIntervalOnBoardingFragment")
////                AnalyticsManager.logLanguage()
//                return mSmileIntervalOnBoardingFragment
//            }
//            else if (position == 5) {
////                AnalyticsManager.logPage("mPermissionsOnboardingFragment")
//                return mPermissionsOnboardingFragment
//            }
            else
            {
                return mClientDetailsFragment
            }
        }

        fun setProgress(progress: Int)
        {
            if (progress > mProgress)
            {
                mProgress = progress
                notifyDataSetChanged()
            }
        }
    }

    override fun onProgress(progress: Int) {
        pagerAdapter.setProgress(progress)
    }

    override fun setCurrent(fragment: Int) {
        viewPager.setCurrentItem(fragment, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        ItemsAndRoomsFragment.mRooms = ArrayList()
        ItemsAndRoomsFragment.inn = null
    }
}

interface ViewPagerNavigation {
    fun onProgress(progress: Int)
    fun setCurrent(fragment: Int)
}