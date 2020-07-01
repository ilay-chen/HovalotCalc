package com.icstudios.hovalotcalc.ordercreate

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.icstudios.hovalotcalc.R
import com.icstudios.hovalotcalc.ordercreate.OrderCreateActivity.Companion.newOrder

private const val PERMISSION_OVERLAY_REQUEST_CODE = 10
private const val PERMISSIONS_REQUEST_CODE = 11
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

class ClientDetailsFragment : Fragment() {

    private lateinit var mButtonFinish: Button
    private lateinit var mClientName: EditText
    private lateinit var mClientPhone: EditText
    private lateinit var mClientEmail: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.client_details_layout, container, false)
        mClientName = rootView.findViewById(R.id.editTextTextPersonName)
        mClientPhone = rootView.findViewById(R.id.editTextTextPhone)
        mClientEmail = rootView.findViewById(R.id.editTextTextPersonEmail)

        mButtonFinish = rootView.findViewById(R.id.next1)
        mButtonFinish.setOnClickListener(View.OnClickListener { view ->
            newOrder.clientName = mClientName.text.toString()
            newOrder.email = mClientEmail.text.toString()
            newOrder.phoneNumber = mClientPhone.text.toString()

            (activity as ViewPagerNavigation).setCurrent(1)
        })

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

        return rootView
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