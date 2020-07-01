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
import java.util.*

class ExtraDetailsFragment : Fragment() {

    private lateinit var mButtonFinish: Button
    private lateinit var mBoxes: Spinner
    private lateinit var mSuitcases: Spinner
    private lateinit var mGags: Spinner
    private lateinit var mMoreDetails: EditText


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.extra_details_fragment, container, false)

        mBoxes = rootView.findViewById(R.id.spinner_boxes)
        mSuitcases = rootView.findViewById(R.id.spinner_suitcase)
        mGags = rootView.findViewById(R.id.spinner_bags)
        //mMoreDetails = rootView.findViewById(R.id.home_floor)

        mButtonFinish = rootView.findViewById(R.id.next1)
        mButtonFinish.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).onProgress(3)
        })

        return rootView
    }

    override fun onResume() {
        super.onResume()
    }
}