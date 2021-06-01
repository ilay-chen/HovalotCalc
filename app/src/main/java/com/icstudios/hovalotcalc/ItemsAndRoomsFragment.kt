package com.icstudios.hovalotcalc

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.icstudios.hovalotcalc.OrderCreateActivity.Companion.newOrder
import org.spongycastle.util.Integers

class ItemsAndRoomsFragment : Fragment() , RemoveRoom{

    private lateinit var addRoom : Button
    private lateinit var mButtonNext: Button
    private lateinit var mButtonPrevious: Button
    lateinit var rootView : View

    companion object {
        @JvmStatic public var mRooms : ArrayList<RoomLayout> = ArrayList()
        @JvmStatic public var inn : LinearLayout? = null
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.items_and_rooms_fragment, container, false)
        inn = rootView.findViewById(R.id.all_items)

        addRoom = rootView.findViewById(R.id.floatingActionButton)

        addRoom.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    addRoom.startAnimation(OrderCreateActivity.zoomin)
                }
                MotionEvent.ACTION_UP -> {
                    addRoom.startAnimation(OrderCreateActivity.zoomout)
                }
            }
            false
        }

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
                    newItem.itemsCounter.setSelection(Integer.parseInt(item.itemCounter)-1)
                    newItem.DisassemblyAndAssembly.isChecked = item.DisassemblyAndAssembly
                    newItem.Disassembly.isChecked = item.Disassembly
                    newItem.Assembly.isChecked = item.Assembly

                    mRooms.get(mRooms.size - 1).addItem(newItem)
                }
                inn?.addView(mRooms.get(mRooms.size-1).view)
            }
        }

        mButtonNext = rootView.findViewById(R.id.next1)
        mButtonNext.setOnClickListener(View.OnClickListener { view ->

            newOrder.getAllRoomsDetails(mRooms)

            if(newOrder.allSet(context)) {


//        currentActivity.finish();
                val Ok: Button
                val price : EditText

                val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                val popupView = layoutInflater.inflate(R.layout.set_price_alert, null)
                val popupMenu = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
                popupMenu.animationStyle = R.style.Animation
                popupMenu.showAtLocation(popupView, Gravity.CENTER, 0, -300)

                Ok = popupView.findViewById(R.id.price_button)
                price = popupView.findViewById(R.id.price)

                if (newOrder.getPrice() != 0) {
                    price.setText(newOrder.getPrice().toString() + "")
                }

                fun hideKeyboard(view : View) {
                    var inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

//            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
                }

                rootView.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                    override fun onFocusChange(view: View?, hasFocus: Boolean) {
                        if (hasFocus) {
                            hideKeyboard(view!!)
                        }
                    }
                })


                Ok.setOnClickListener {
//                    val inputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

//                    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)

                    if (price.getText().toString() == "") price.setText("0")

                    newOrder.setPrice(price.getText().toString().toInt())
                    val PDFMaker = MakePDFOffer(context, makeOffer.currentActivity)
                    PDFMaker.createPdf(newOrder)

                    popupMenu.dismiss()

                    val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }


//                val ft: FragmentManager = (activity as FragmentActivity).supportFragmentManager
//
//                val newFragment: DialogFragment = makeOffer.newInstance(newOrder, activity)
//                newFragment.isCancelable = false
//                newFragment.show(ft, "signIn")
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

            if(newOrder.getClientName()!=null && !newOrder.getClientName().equals(""))
                appData.saveOrder(newOrder, context)

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