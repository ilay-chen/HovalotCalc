package com.icstudios.hovalotcalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class makeOffer extends DialogFragment {

    private static OrderObject orderDetails;
    static Activity currentActivity;

    public makeOffer()
    {

    }

    public static makeOffer newInstance(OrderObject orderDetails, Activity activity) {
        // Required empty public constructor
        makeOffer frag = new makeOffer();
        makeOffer.orderDetails = orderDetails;
        currentActivity = activity;
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        setShowsDialog(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Set an EditText view to get user input
        final EditText input = new EditText(getContext());
        input.setHint("הכנס הצעת מחיר");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(orderDetails.getPrice()!=0)
        {
            input.setText(orderDetails.getPrice()+"");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder
                .setView(input)
                .setMessage("הכנס הצעת מחיר")
                .setPositiveButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        if(input.getText().toString().equals(""))
                            input.setText("0");

                        orderDetails.setPrice(Integer.parseInt(input.getText().toString()));
                        com.icstudios.hovalotcalc.MakePDFOffer PDFMaker = new MakePDFOffer(getContext(), currentActivity);
                        PDFMaker.createPdf(orderDetails);
                    }
                })
                .setNeutralButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dismiss();
                    }
                });

        AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {                    //
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackgroundResource(R.drawable.insert_price);
                positiveButton.setHeight(75);
                positiveButton.setWidth(75);

                Button negativeButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEUTRAL);
                negativeButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.back_price));
            }
        });

        return alert;


    }

}
