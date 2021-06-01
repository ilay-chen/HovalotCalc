package com.icstudios.hovalotcalc;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RoomLayout extends LinearLayout{

    Button delete;
    EditText roomName;
    ArrayList<ItemLayout> mItems;
    View view;
    LinearLayout itemList;
    static Button addItem;
    RoomLayout thisRoom;

    public RoomLayout(final Context context) {
        super(context);
        thisRoom = this;
        view = inflate(context, R.layout.room_view, this);
        itemList = view.findViewById(R.id.all_items_of_room);
        mItems = new ArrayList<ItemLayout>();

        roomName = findViewById(R.id.room_name);

        addItem = view.findViewById(R.id.add_new_item);
//        addItem.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        addItem.startAnimation(OrderCreateActivity.zoomin);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        addItem.startAnimation(OrderCreateActivity.zoomout);
//                        break;
//                }
//                return false;
//            }
//        });

        addItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(new ItemLayout(context));
            }
        });

        delete = view.findViewById(R.id.remove_room);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemsAndRoomsFragment.Companion.getInn().removeView(thisRoom.view);
                ItemsAndRoomsFragment.Companion.getMRooms().remove(thisRoom);
            }
        });
    }

    public void addItem(ItemLayout newItem)
    {
        mItems.add(newItem);
        itemList.addView(newItem);

        newItem.itemName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(newItem.itemName, InputMethodManager.SHOW_IMPLICIT);

//        if(newItem.itemName.requestFocus()) {
//            ((Activity)getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
    }

    public ArrayList<ItemLayout> getItems()
    {
        return mItems;
    }

    public String getRoomName() {
        return roomName.getText().toString();
    }
}
