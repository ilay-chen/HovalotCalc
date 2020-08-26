package com.icstudios.hovalotcalc;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RoomLayout extends LinearLayout{

    ImageButton delete;
    EditText roomName;
    ArrayList<ItemLayout> mItems;
    View view;
    LinearLayout itemList;
    FloatingActionButton addItem;
    RoomLayout thisRoom;

    public RoomLayout(final Context context) {
        super(context);
        thisRoom = this;
        view = inflate(context, R.layout.room_view, this);
        itemList = view.findViewById(R.id.all_items_of_room);
        mItems = new ArrayList<ItemLayout>();

        roomName = findViewById(R.id.room_name);

        addItem = view.findViewById(R.id.add_new_item);
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
    }

    public ArrayList<ItemLayout> getItems()
    {
        return mItems;
    }

    public String getRoomName() {
        return roomName.getText().toString();
    }
}
