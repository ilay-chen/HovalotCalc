package com.icstudios.hovalotcalc

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
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
        lateinit var zoomin : Animation
        lateinit var zoomout : Animation
    }

    private lateinit var pagerAdapter: OrderCreateActivity.ScreenSlidePagerAdapter
    lateinit var viewPager: ViewPager2
    var popupMenu : PopupWindow? = null
    var page = 0
    var id : String? = null

    val mClientDetailsFragment = ClientDetailsFragment()
    val mAddressAndDateFragment = AddressAndDateFragment()
    val mExtraDetailsFragment = ExtraDetailsFragment()
    val mItemsAndRoomsFragment = ItemsAndRoomsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_create)

        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out)

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

            if(newOrder.getClientName()!=null && !newOrder.getClientName().equals(""))
                appData.saveOrder(newOrder, applicationContext)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val action = data?.action
        if (requestCode == 1) {
            finish();
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

//    override fun onBackPressed() {
//        val ab: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@OrderCreateActivity)
//        ab.setTitle("יציאה מהזמנה")
//        ab.setMessage("אתה בטוח שאתה רוצה לצאת מההזמנה?")
//        ab.setPositiveButton("כן", DialogInterface.OnClickListener { dialog, which ->
//            dialog.dismiss()
//            super.onBackPressed()
//            //if you want to kill app . from other then your main avtivity.(Launcher)
//            Process.killProcess(Process.myPid())
//            System.exit(1)
//
//            //if you want to finish just current activity
//            this@OrderCreateActivity.finish()
//        })
//        ab.setNegativeButton("לא", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
//        ab.show()
//    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onBackPressed() {
        if (popupMenu != null && popupMenu!!.isShowing()) {
            popupMenu!!.dismiss()
        } else {
            val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = layoutInflater.inflate(R.layout.alert_out, null)
            popupMenu = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
            popupMenu!!.setAnimationStyle(R.style.Animation)
            val yes = popupView.findViewById<View>(R.id.yes_button) as Button
            val no = popupView.findViewById<View>(R.id.no_button) as Button

            //Close the popup when touch outside
            popupMenu!!.setOutsideTouchable(false)
            popupMenu!!.setFocusable(false)

//            popupMenu.setTouchModal(false);
            yes.setOnClickListener { // TODO Auto-generated method stub
                super.onBackPressed()
            }
            no.setOnClickListener { // TODO Auto-generated method stub
                popupMenu!!.dismiss()
            }
            popupMenu!!.showAtLocation(popupView, Gravity.CENTER, 0, -200)
        }
    }

}

interface ViewPagerNavigation {
    fun onProgress(progress: Int)
    fun setCurrent(fragment: Int)
}