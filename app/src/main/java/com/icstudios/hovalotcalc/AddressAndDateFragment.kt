package com.icstudios.hovalotcalc

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.icstudios.hovalotcalc.OrderCreateActivity.Companion.newOrder
import com.icstudios.hovalotcalc.OrderCreateActivity.Companion.zoomin
import com.icstudios.hovalotcalc.OrderCreateActivity.Companion.zoomout
import java.util.*


class AddressAndDateFragment : Fragment() {

    private lateinit var mButtonNext: Button
    private lateinit var mButtonPrevious: Button
    private lateinit var mFromAddress: EditText
    private lateinit var mToAddress: EditText
    private lateinit var mFromStreet: EditText
    private lateinit var mToStreet: EditText
    private lateinit var mFromFloor: Spinner
    private lateinit var mToFloor: Spinner
    private lateinit var mFromNumber: Spinner
    private lateinit var mToNumber: Spinner
    private lateinit var mFromCrane: CheckBox
    private lateinit var mToCrane: CheckBox
    private lateinit var mFromElevator: CheckBox
    private lateinit var mToElevator: CheckBox
    private lateinit var mFromPacking: CheckBox
    private lateinit var mToPacking: CheckBox


    private lateinit var mDateEditText: EditText
    private lateinit var mDatePickerDialog: DatePickerDialog
    private lateinit var mHourEditText: Spinner
    private lateinit var mHourPickerDialog: TimePickerDialog


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.address_and_date_fragment, container, false)

        mFromAddress = rootView.findViewById(R.id.address_from_edit_text)
        mFromAddress.setOnFocusChangeListener { v, hasFocus ->
            newOrder.fromCity = mFromAddress.text.toString()
        }
        if(newOrder.fromCity!=null)
        {
            mFromAddress.setText(newOrder.fromCity)
        }

        mFromStreet = rootView.findViewById(R.id.street_from_edit_text)
        mFromStreet.setOnFocusChangeListener { v, hasFocus ->
            newOrder.fromStreet = mFromStreet.text.toString()
        }
        if(newOrder.fromStreet!=null)
        {
            mFromStreet.setText(newOrder.fromStreet)
        }

        mFromNumber = rootView.findViewById(R.id.home_number)
        mFromNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                newOrder.fromNumber = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newOrder.fromNumber = mFromNumber.selectedItem.toString()
            }

        }
        if(newOrder.fromNumber!=null)
        {
            mFromNumber.setSelection(Integer.parseInt(newOrder.fromNumber))
        }

        mFromFloor = rootView.findViewById(R.id.home_floor)
        mFromFloor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                newOrder.fromFloor = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newOrder.fromFloor = mFromFloor.selectedItem.toString()
            }

        }
        if(newOrder.fromFloor!=null)
        {
            mFromFloor.setSelection(Integer.parseInt(newOrder.fromFloor))
        }

        mFromCrane = rootView.findViewById(R.id.crane)
        mFromCrane.setOnCheckedChangeListener { buttonView, isChecked ->
            newOrder.fromCrane = isChecked
        }
        if(newOrder.fromCrane!=null)
        {
            mFromCrane.isChecked = newOrder.fromCrane
        }
        else {
            newOrder.fromCrane = false
        }

        mFromElevator = rootView.findViewById(R.id.elevator)
        mFromElevator.setOnCheckedChangeListener { buttonView, isChecked ->
            newOrder.fromElevator = isChecked
        }
        if(newOrder.fromElevator!=null)
        {
            mFromElevator.isChecked = newOrder.fromElevator
        }
        else {
            newOrder.fromElevator = false
        }

        mFromPacking = rootView.findViewById(R.id.from_packing)
        mFromPacking.setOnCheckedChangeListener { buttonView, isChecked ->
            OrderCreateActivity.newOrder.fromPacking = isChecked
        }
        if(newOrder.fromPacking!=null)
        {
            mFromPacking.isChecked = newOrder.fromPacking
        }
        else {
            OrderCreateActivity.newOrder.fromPacking = false
        }

        mToAddress = rootView.findViewById(R.id.address_to_edit_text)
        mToAddress.setOnFocusChangeListener { v, hasFocus ->
            newOrder.toCity = mToAddress.text.toString()
        }
        if(newOrder.toCity!=null)
        {
            mToAddress.setText(newOrder.toCity)
        }

        mToStreet = rootView.findViewById(R.id.street_to_edit_text)
        mToStreet.setOnFocusChangeListener { v, hasFocus ->
            newOrder.toStreet = mToStreet.text.toString()
        }
        if(newOrder.toStreet!=null)
        {
            mToStreet.setText(newOrder.toStreet)
        }

        mToNumber = rootView.findViewById(R.id.to_home_number)
        mToNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                newOrder.toNumber = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newOrder.toNumber = mToNumber.selectedItem.toString();
            }

        }
        if(newOrder.toNumber!=null)
        {
            mToNumber.setSelection(Integer.parseInt(newOrder.toNumber))
        }

        mToFloor = rootView.findViewById(R.id.to_home_floor)
        mToFloor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                newOrder.toFloor = "0"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newOrder.toFloor = mToFloor.selectedItem.toString()
            }

        }
        if(newOrder.toFloor!=null)
        {
            mToFloor.setSelection(Integer.parseInt(newOrder.toFloor))
        }

        mToCrane = rootView.findViewById(R.id.to_crane)
        mToCrane.setOnCheckedChangeListener { buttonView, isChecked ->
            newOrder.toCrane = isChecked
        }
        if(newOrder.toCrane!=null)
        {
            mToCrane.isChecked = newOrder.toCrane
        }
        else {
            newOrder.toCrane = false
        }

        mToElevator = rootView.findViewById(R.id.to_elevator)
        mToElevator.setOnCheckedChangeListener { buttonView, isChecked ->
            OrderCreateActivity.newOrder.toElevator = isChecked
        }
        if(newOrder.toElevator!=null)
        {
            mToElevator.isChecked = newOrder.toElevator
        }
        else {
            OrderCreateActivity.newOrder.toElevator = false
        }

        mToPacking = rootView.findViewById(R.id.to_packing)
        mToPacking.setOnCheckedChangeListener { buttonView, isChecked ->
            OrderCreateActivity.newOrder.toPacking = isChecked
        }
        if(newOrder.toPacking!=null)
        {
            mToPacking.isChecked = newOrder.toPacking
        }
        else {
            OrderCreateActivity.newOrder.toPacking = false
        }


        mDateEditText = rootView.findViewById(R.id.date_edit_text)
        mDateEditText.setInputType(InputType.TYPE_NULL)
        mDateEditText.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openDatePicker()
            }
        })
        mDateEditText.setOnClickListener(View.OnClickListener {
            openDatePicker()
        })
        if(newOrder.date!=null)
        {
            mDateEditText.setText(newOrder.date)
        }


        mHourEditText = rootView.findViewById(R.id.hour_edit_text)
        mHourEditText.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, arg2: Int, arg3: Long) {
                val items: String = mHourEditText.getSelectedItem().toString()
                newOrder.hour = items
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                val items: String = mHourEditText.getSelectedItem().toString()
                newOrder.hour = items
            }
        })
        if(newOrder.hour!=null)
        {
            mHourEditText.setSelection((mHourEditText.getAdapter() as ArrayAdapter<String?>).getPosition(newOrder.hour))
        }

//        mHourEditText.inputType = InputType.TYPE_NULL
//        mHourEditText.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                openClockPicker()
//            }
//        })
//        mHourEditText.setOnClickListener(View.OnClickListener {
//            openClockPicker()
//        })
//        if(newOrder.hour!=null)
//        {
//            mHourEditText.setText(newOrder.hour)
//        }

        mButtonNext = rootView.findViewById(R.id.next1)
        mButtonNext.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(2)
        })

        mButtonNext.setOnTouchListener { v, event ->
            mButtonPrevious.clearAnimation()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mButtonNext.startAnimation(zoomin)
                }
                MotionEvent.ACTION_UP -> {
                    mButtonNext.startAnimation(zoomout)
                }
            }
            false
        }

        mButtonPrevious = rootView.findViewById(R.id.previous)
        mButtonPrevious.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(0)
        })

        mButtonPrevious.setOnTouchListener { v, event ->
            mButtonNext.clearAnimation()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mButtonPrevious.startAnimation(zoomin)
                }
                MotionEvent.ACTION_UP -> {
                    mButtonPrevious.startAnimation(zoomout)
                }
            }
            false
        }

        return rootView
    }

//    fun openClockPicker()
//    {
//        val cldr = Calendar.getInstance()
//        val hour = cldr[Calendar.HOUR_OF_DAY]
//        val minutes = cldr[Calendar.MINUTE]
//        // time picker dialog
//        mHourPickerDialog = TimePickerDialog(context,
//                OnTimeSetListener {
//                    tp, sHour, sMinute -> mHourEditText.setText("$sHour:$sMinute")
//                    newOrder.hour = mHourEditText.text.toString()
//                }, hour, minutes, true)
//        mHourPickerDialog.show()
//    }

    fun openDatePicker()
    {
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        // date picker dialog
        mDatePickerDialog = DatePickerDialog(this!!.context!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    mDateEditText.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    newOrder.date = mDateEditText.text.toString()
                }, year, month, day)
        mDatePickerDialog.show()
    }

    override fun onResume() {
        super.onResume()
    }
}