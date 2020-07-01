package com.icstudios.hovalotcalc.ordercreate

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.icstudios.hovalotcalc.OrderObject
import com.icstudios.hovalotcalc.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OrderCreateActivity : FragmentActivity(), ViewPagerNavigation {

    companion object {
        @JvmStatic
        var newOrder = OrderObject()
    }

    private lateinit var pagerAdapter: OrderCreateActivity.ScreenSlidePagerAdapter
    lateinit var viewPager: ViewPager2
    var page = 0

    val mClientDetailsFragment = ClientDetailsFragment()
    val mAddressAndDateFragment = AddressAndDateFragment()
    val mExtraDetailsFragment = ExtraDetailsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_create)

        val intent = intent
        page = intent.getIntExtra("jumpTo", 1);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)
        viewPager.isUserInputEnabled = false // until finish cards


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
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        var mProgress = page

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
//            else if (position == 3){
//                //AnalyticsManager.logPage("mPictureSmileExplanationFragment")
//                return mPictureSmileExplanationFragment
//            }
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
        if (progress > 1)
        {
            viewPager.isUserInputEnabled = true //finished cards so can swipe the viewpager
        }
        pagerAdapter.setProgress(progress)
    }
}

interface ViewPagerNavigation {
    fun onProgress(progress: Int)
}