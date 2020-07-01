package com.icstudios.hovalotcalc.ordercreate

import android.Manifest
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
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
        mBoxes.setOnItemClickListener { parent, view, position, id ->
            OrderCreateActivity.newOrder.boxes = mBoxes.getChildAt(position).toString()
        }

        mSuitcases = rootView.findViewById(R.id.spinner_suitcase)
        mSuitcases.setOnItemClickListener { parent, view, position, id ->
            OrderCreateActivity.newOrder.suitcases = mSuitcases.getChildAt(position).toString()
        }

        mGags = rootView.findViewById(R.id.spinner_bags)
        mGags.setOnItemClickListener { parent, view, position, id ->
            OrderCreateActivity.newOrder.bags = mGags.getChildAt(position).toString()
        }
        //mMoreDetails = rootView.findViewById(R.id.home_floor)

        mButtonNext = rootView.findViewById(R.id.next1)
        mButtonNext.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(3)
        })

        mButtonPrevious = rootView.findViewById(R.id.previous)
        mButtonPrevious.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(1)
        })

        return rootView
    }

    override fun onResume() {
        super.onResume()
    }
}