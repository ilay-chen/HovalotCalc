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


private const val PERMISSION_OVERLAY_REQUEST_CODE = 10
private const val PERMISSIONS_REQUEST_CODE = 11
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

class AddressAndDateFragment : Fragment() {

    private lateinit var mButtonFinish: Button
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


    private lateinit var mDateEditText: EditText
    private lateinit var mDatePickerDialog: DatePickerDialog
    private lateinit var mHourEditText: EditText
    private lateinit var mHourPickerDialog: TimePickerDialog


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.address_and_date_fragment, container, false)

        mFromAddress = rootView.findViewById(R.id.address_from_edit_text)
        mFromStreet = rootView.findViewById(R.id.street_from_edit_text)
        mFromNumber = rootView.findViewById(R.id.home_number)
        mFromFloor = rootView.findViewById(R.id.home_floor)
        mFromCrane = rootView.findViewById(R.id.crane)
        mFromElevator = rootView.findViewById(R.id.elevator)

        mToAddress = rootView.findViewById(R.id.address_to_edit_text)
        mToStreet = rootView.findViewById(R.id.street_to_edit_text)
        mToNumber = rootView.findViewById(R.id.to_home_number)
        mToFloor = rootView.findViewById(R.id.to_home_floor)
        mToCrane = rootView.findViewById(R.id.to_crane)
        mToElevator = rootView.findViewById(R.id.to_elevator)



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

        mHourEditText = rootView.findViewById(R.id.hour_edit_text)
        mHourEditText.inputType = InputType.TYPE_NULL
        mHourEditText.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openClockPicker()
            }
        })
        mHourEditText.setOnClickListener(View.OnClickListener {
            openClockPicker()
        })
//        mClientName = rootView.findViewById(R.id.editTextTextPersonName)
//        mClientPhone = rootView.findViewById(R.id.editTextTextPersonName)
//        mClientEmail = rootView.findViewById(R.id.editTextTextPersonName)

//        mButtonFinish = rootView.findViewById(R.id.next1)
//        mButtonFinish.setOnClickListener(View.OnClickListener { view ->
//            newOrder.clientName = mClientName.text.toString()
//            newOrder.email = mClientEmail.text.toString()
//            newOrder.phoneNumber = mClientPhone.text.toString()
//
//            (activity as ViewPagerNavigation).onProgress(2)
//        })

//        mButtonPermission = rootView.findViewById(R.id.button_permissions)
//        mButtonPermission.setOnClickListener(View.OnClickListener { view ->
//            getPermissions()
//        })
//
//        mButtonPermissionOverlay = rootView.findViewById(R.id.button_permissions_overlay)
//        mButtonPermissionOverlay.setOnClickListener(View.OnClickListener { view ->
//
//        })
//
//        mButtonPermissionSettings = rootView.findViewById(R.id.button_settings_permission)
//        mButtonPermissionSettings.setOnClickListener(View.OnClickListener { view ->
//
//        })
//

        mButtonFinish = rootView.findViewById(R.id.next1)
        mButtonFinish.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).onProgress(3)
        })

        return rootView
    }

    fun openClockPicker()
    {
        val cldr = Calendar.getInstance()
        val hour = cldr[Calendar.HOUR_OF_DAY]
        val minutes = cldr[Calendar.MINUTE]
        // time picker dialog
        mHourPickerDialog = TimePickerDialog(context,
                OnTimeSetListener { tp, sHour, sMinute -> mHourEditText.setText("$sHour:$sMinute") }, hour, minutes, true)
        mHourPickerDialog.show()

        OrderCreateActivity.newOrder.hour = mHourEditText.text.toString()
    }

    fun openDatePicker()
    {
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        // date picker dialog
        mDatePickerDialog = DatePickerDialog(this!!.context!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth -> mDateEditText.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) }, year, month, day)
        mDatePickerDialog.show()

        OrderCreateActivity.newOrder.date = mDateEditText.text.toString()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * Permission functions
     */

    fun getPermissions() {
        requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
    }

    /** Convenience method used to check if all permissions required by this app are granted */
    fun hasPermissions() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context!!, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.all { grantResult ->  (PackageManager.PERMISSION_GRANTED == grantResult) }){
                // Take the user to the success fragment when permission is granted
                Toast.makeText(activity, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}