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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.icstudios.hovalotcalc.R
import com.icstudios.hovalotcalc.ordercreate.OrderCreateActivity.Companion.newOrder

class ItemsAndRoomsFragment : Fragment() , removeRoom{

    private lateinit var addRoom : FloatingActionButton
    private lateinit var mButtonNext: Button
    private lateinit var mButtonPrevious: Button

    companion object {
        @JvmStatic public var mRooms : ArrayList<RoomLayout> = ArrayList()
        @JvmStatic public lateinit var inn : LinearLayout
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.items_and_rooms_fragment, container, false)
        inn = rootView.findViewById<LinearLayout>(R.id.all_items)

        addRoom = rootView.findViewById(R.id.floatingActionButton)
        addRoom.setOnClickListener(View.OnClickListener { view ->
            mRooms.add(RoomLayout(context))
            //container!!.addView(mRooms.get(0))
            inn.addView(mRooms.get(mRooms.size-1))
            mRooms[mRooms.size-1].addItem(ItemLayout(context))
        })

        mButtonNext = rootView.findViewById(R.id.next1)
        mButtonNext.setOnClickListener(View.OnClickListener { view ->

            newOrder.roomsAndItems = mRooms

            val ft: FragmentManager = (activity as FragmentActivity).supportFragmentManager

            val newFragment: DialogFragment = makeOffer.newInstance(newOrder)
            newFragment.isCancelable = false
            newFragment.show(ft, "signIn")
        })

        mButtonPrevious = rootView.findViewById(R.id.previous)
        mButtonPrevious.setOnClickListener(View.OnClickListener { view ->

            (activity as ViewPagerNavigation).setCurrent(2)
        })

        return rootView
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRemove(roomId: Int) {
        inn.removeView(mRooms[roomId])
        mRooms.remove(mRooms[roomId])
    }
}

interface removeRoom {
    fun onRemove(progress: Int)
}