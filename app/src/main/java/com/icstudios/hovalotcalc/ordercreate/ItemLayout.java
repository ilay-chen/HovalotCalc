package com.icstudios.hovalotcalc.ordercreate;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.icstudios.hovalotcalc.R;

public class ItemLayout extends LinearLayout {

    EditText itemName, itemsCounter;
    CheckBox DisassemblyAndAssembly;
    Button addSameItem, subtractSameItem;

    public ItemLayout(Context context) {
        super(context);
        inflate(context, R.layout.item_view, this);

        itemsCounter = findViewById(R.id.item_counter);
        itemsCounter.setText(0+"");

        itemName = findViewById(R.id.item_name);

        DisassemblyAndAssembly = findViewById(R.id.disassembly_and_assembly);

        addSameItem = findViewById(R.id.add_item_num);
        addSameItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsCounter.getText() == null || itemsCounter.getText().toString().equals("")) itemsCounter.setText(0+"");
                int counter = Integer.parseInt(itemsCounter.getText().toString())+1;
                itemsCounter.setText(counter+"");
            }
        });

        subtractSameItem = findViewById(R.id.subtract_item_num);
        subtractSameItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsCounter.getText() == null || itemsCounter.getText().toString().equals("")) itemsCounter.setText(0+"");
                int counter = Integer.parseInt(itemsCounter.getText().toString())-1;
                if(counter<0) counter=0;
                itemsCounter.setText(counter+"");
            }
        });
    }

    public EditText getItemName() {
        return itemName;
    }

    public void setItemName(EditText itemName) {
        this.itemName = itemName;
    }

    public EditText getItemsCounter() {
        return itemsCounter;
    }

    public void setItemsCounter(EditText itemsCounter) {
        this.itemsCounter = itemsCounter;
    }

    public CheckBox getDisassemblyAndAssembly() {
        return DisassemblyAndAssembly;
    }

    public void setDisassemblyAndAssembly(CheckBox disassemblyAndAssembly) {
        DisassemblyAndAssembly = disassemblyAndAssembly;
    }

    public Button getAddSameItem() {
        return addSameItem;
    }

    public void setAddSameItem(Button addSameItem) {
        this.addSameItem = addSameItem;
    }

    public Button getSubtractSameItem() {
        return subtractSameItem;
    }

    public void setSubtractSameItem(Button subtractSameItem) {
        this.subtractSameItem = subtractSameItem;
    }
}
