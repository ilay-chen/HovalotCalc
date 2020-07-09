package com.icstudios.hovalotcalc.ordercreate;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.icstudios.hovalotcalc.R;

import java.util.ArrayList;

public class RoomLayout extends LinearLayout{

    Button delete;
    EditText roomName;
    ArrayList<ItemLayout> mItems;
    View view;
    LinearLayout itemList;
    FloatingActionButton addItem;
    RoomLayout t;
    int roomId;

    public RoomLayout(final Context context) {
        super(context);
        t = this;
        view = inflate(context, R.layout.room_view, this);
        itemList = view.findViewById(R.id.all_items_of_room);
        mItems = new ArrayList<ItemLayout>();

        delete = findViewById(R.id.remove_room);
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
                //RemoveItem(t);
                //(ItemsAndRoomsFragment as removeRoom).onProgress(5)
            }
        });
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void addItem(ItemLayout newItem)
    {
        mItems.add(newItem);
        itemList.addView(newItem);
    }

    public void RemoveItem(RoomLayout roomLayout)
    {
        mItems.remove(roomLayout);
        itemList.removeView(roomLayout);
    }

    public ArrayList<ItemLayout> getItems()
    {
       return mItems;
    }
}
