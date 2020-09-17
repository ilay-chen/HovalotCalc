package com.icstudios.hovalotcalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.icstudios.hovalotcalc.OrderCreateActivity.Companion.newOrder

class ItemsAndRoomsFragment : Fragment() , RemoveRoom{

    private lateinit var addRoom : FloatingActionButton
    private lateinit var mButtonNext: Button
    private lateinit var mButtonPrevious: Button

    companion object {
        @JvmStatic public var mRooms : ArrayList<RoomLayout> = ArrayList()
        @JvmStatic public var inn : LinearLayout? = null
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.items_and_rooms_fragment, container, false)
        inn = rootView.findViewById(R.id.all_items)

        addRoom = rootView.findViewById(R.id.floatingActionButton)
        addRoom.setOnClickListener(View.OnClickListener { view ->
            mRooms.add(RoomLayout(context))
            //container!!.addView(mRooms.get(0))
            inn?.addView(mRooms.get(mRooms.size-1).view)
            mRooms[mRooms.size-1].addItem(ItemLayout(context))

//            mRooms[mRooms.size-1].items
//            if(myEditText.requestFocus()) {
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//            }
        })
        if(newOrder.roomsAndItems!=null)
        {
            for(room in newOrder.roomsAndItems) {
                mRooms.add(RoomLayout(context))
                mRooms.get(mRooms.size-1).roomName.setText(room.roomName)
                for(item in room.getmItems()) {
                    var newItem = ItemLayout(context)
                    newItem.itemName.setText(item.itemName)
                    newItem.itemsCounter.setText(item.itemCounter)
                    newItem.DisassemblyAndAssembly.isChecked = item.DisassemblyAndAssembly

                    mRooms.get(mRooms.size - 1).addItem(newItem)
                }
                inn?.addView(mRooms.get(mRooms.size-1).view)
            }
        }

        mButtonNext = rootView.findViewById(R.id.next1)
        mButtonNext.setOnClickListener(View.OnClickListener { view ->

            newOrder.getAllRoomsDetails(mRooms)

            if(newOrder.allSet(context)) {
                val ft: FragmentManager = (activity as FragmentActivity).supportFragmentManager

                val newFragment: DialogFragment = makeOffer.newInstance(newOrder, activity)
                newFragment.isCancelable = false
                newFragment.show(ft, "signIn")
            }
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

            (activity as ViewPagerNavigation).setCurrent(2)
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

    override fun onRemove(roomId: Int) {
        inn?.removeView(mRooms[roomId].view)
        mRooms.remove(mRooms[roomId])
    }
}

interface RemoveRoom {
    fun onRemove(progress: Int)
}