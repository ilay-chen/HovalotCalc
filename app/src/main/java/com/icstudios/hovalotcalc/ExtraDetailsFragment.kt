package com.icstudios.hovalotcalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.icstudios.hovalotcalc.R

class ExtraDetailsFragment : Fragment() {

    private lateinit var mBoxes: Spinner
    private lateinit var mSuitcases: Spinner
    private lateinit var mGags: Spinner
    private lateinit var mMoreDetails: EditText
    private lateinit var mButtonNext: Button
    private lateinit var mButtonPrevious: Button


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.extra_details_fragment, container, false)

        mBoxes = rootView.findViewById(R.id.spinner_boxes)
        mBoxes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                OrderCreateActivity.newOrder.boxes = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                OrderCreateActivity.newOrder.boxes = mBoxes.selectedItem.toString()
            }
        }
        if(OrderCreateActivity.newOrder.boxes!=null)
        {
            for (i in 0 until mBoxes.count) {
                if (mBoxes.getItemAtPosition(i).toString().equals(OrderCreateActivity.newOrder.boxes)) {
                    mBoxes.setSelection(i)
                }
            }
        }

        mSuitcases = rootView.findViewById(R.id.spinner_suitcase)
        mSuitcases.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                OrderCreateActivity.newOrder.suitcases = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                OrderCreateActivity.newOrder.suitcases = mSuitcases.selectedItem.toString()
            }

        }
        if(OrderCreateActivity.newOrder.suitcases!=null)
        {
            for (i in 0 until mSuitcases.count) {
                if (mSuitcases.getItemAtPosition(i).toString().equals(OrderCreateActivity.newOrder.suitcases)) {
                    mSuitcases.setSelection(i)
                }
            }
        }

        mGags = rootView.findViewById(R.id.spinner_bags)
        mGags.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                OrderCreateActivity.newOrder.bags = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                OrderCreateActivity.newOrder.bags = mGags.selectedItem.toString()
            }

        }
        if(OrderCreateActivity.newOrder.boxes!=null)
        {
            for (i in 0 until mGags.count) {
                if (mGags.getItemAtPosition(i).toString().equals(OrderCreateActivity.newOrder.bags)) {
                    mGags.setSelection(i)
                }
            }
        }
        //mMoreDetails = rootView.findViewById(R.id.home_floor)

        mMoreDetails = rootView.findViewById(R.id.notes)
        mMoreDetails.setOnFocusChangeListener { v, hasFocus ->
            OrderCreateActivity.newOrder.notes = mMoreDetails.text.toString()
        }
        if(OrderCreateActivity.newOrder.notes!=null)
        {
            mMoreDetails.setText(OrderCreateActivity.newOrder.notes)
        }

        mButtonNext = rootView.findViewById(R.id.next1)
        mButtonNext.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(3)
        })

        mButtonNext.setOnTouchListener { v, event ->
            mButtonPrevious.clearAnimation()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mButtonNext.startAnimation(OrderCreateActivity.zoomin)
                }
                MotionEvent.ACTION_UP -> {
                    mButtonNext.startAnimation(OrderCreateActivity.zoomout)
                }
            }
            false
        }

        mButtonPrevious = rootView.findViewById(R.id.previous)
        mButtonPrevious.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(1)
        })

        mButtonPrevious.setOnTouchListener { v, event ->
            mButtonNext.clearAnimation()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mButtonPrevious.startAnimation(OrderCreateActivity.zoomin)
                }
                MotionEvent.ACTION_UP -> {
                    mButtonPrevious.startAnimation(OrderCreateActivity.zoomout)
                }
            }
            false
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
    }
}