package com.icstudios.hovalotcalc;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icstudios.hovalotcalc.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ItemLayout extends LinearLayout {

    EditText itemName;
    Spinner itemsCounter;
    CheckBox DisassemblyAndAssembly, Assembly, Disassembly;
    Button addSameItem, subtractSameItem;
    ImageButton menu;
    View popupView;

    public ItemLayout(final Context context) {
        super(context);
        inflate(context, R.layout.item_view, this);

        menu = findViewById(R.id.menu_item);

        final LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        popupView = layoutInflater.inflate(R.layout.item_menu_popup, null);
//        final PopupWindow popupMenu = new PopupWindow(popupView, 730,LayoutParams.WRAP_CONTENT,true);
        if(width >= 1200) {
            width = (int)(width*0.40);
        }
        else width = 730;

        final PopupWindow popupMenu = new PopupWindow(popupView,width,LayoutParams.WRAP_CONTENT,true);
        popupMenu.setAnimationStyle(R.style.Animation);

        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];

//                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.RESULT_UNCHANGED_HIDDEN);

                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                menu.getLocationInWindow(location);
                popupMenu.showAtLocation(popupView, Gravity.NO_GRAVITY, location[0] + 25, location[1] + 75);
            }
        });

        itemsCounter = popupView.findViewById(R.id.item_counter);
//        itemsCounter.setText(1+"");

        itemName = findViewById(R.id.item_name);

        itemName.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_NEXT
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            Toast.makeText(context, "נוסף פריט בחדר",Toast.LENGTH_SHORT).show();
                            RoomLayout.addItem.performClick();
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        DisassemblyAndAssembly = popupView.findViewById(R.id.disassembly_and_assembly);
        Disassembly = popupView.findViewById(R.id.disassembly);
        Assembly = popupView.findViewById(R.id.assembly);


//        itemsCounter.OnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int counter = Integer.parseInt(itemsCounter.getSelectedItem().toString());
////                itemsCounter.setText(counter+"");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//                (object : OnItemSelectedListener {
//            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, arg2: Int, arg3: Long) {
//                val items: String = mHourEditText.getSelectedItem().toString()
//                newOrder.hour = items
//            }
//
//            override fun onNothingSelected(arg0: AdapterView<*>?) {
//                val items: String = mHourEditText.getSelectedItem().toString()
//                newOrder.hour = items
//            }
//        })

//        addSameItem = popupView.findViewById(R.id.add_item_num);
//        addSameItem.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(itemsCounter.getText() == null || itemsCounter.getText().toString().equals("")) itemsCounter.setText(0+"");
//                int counter = Integer.parseInt(itemsCounter.getText().toString())+1;
//                itemsCounter.setText(counter+"");
//            }
//        });
//
//        subtractSameItem = popupView.findViewById(R.id.subtract_item_num);
//        subtractSameItem.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(itemsCounter.getText() == null || itemsCounter.getText().toString().equals("")) itemsCounter.setText(0+"");
//                int counter = Integer.parseInt(itemsCounter.getText().toString())-1;
//                if(counter<=0){
//                    counter=0;
//                    //remove item
//                }
//                itemsCounter.setText(counter+"");
//            }
//        });
    }

    public EditText getItemName() {
        return itemName;
    }

    public void setItemName(EditText itemName) {
        this.itemName = itemName;
    }

    public Spinner getItemsCounter() {
        return itemsCounter;
    }

    public void setItemsCounter(Spinner itemsCounter) {
        this.itemsCounter = itemsCounter;
    }

    public CheckBox getDisassemblyAndAssembly() {
        return DisassemblyAndAssembly;
    }

    public void setDisassemblyAndAssembly(CheckBox disassemblyAndAssembly) {
        DisassemblyAndAssembly = disassemblyAndAssembly;
    }

    public CheckBox getAssembly() {
        return Assembly;
    }

    public void setAssembly(CheckBox Assembly) {
        this.Assembly = Assembly;
    }

    public CheckBox getDisassembly() {
        return Disassembly;
    }

    public void setDisassembly(CheckBox disassembly) {
        Disassembly = disassembly;
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

    public String getName()
    {
        return itemName.getText().toString();
    }

    public String getCounter()
    {
        return itemsCounter.getSelectedItem().toString();
    }
}
