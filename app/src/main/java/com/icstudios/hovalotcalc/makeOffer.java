package com.icstudios.hovalotcalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
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

        AlertDialog alert = builder.create();

        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundResource(R.drawable.back_price);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundResource(R.drawable.insert_price);

        builder
                .setView(input)
                .setMessage("הכנס הצעת מחיר")
//                .setNegativeButton(R.string.close_validation, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        getActivity().finishAffinity();
//                    }
//                })

                .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        if(input.getText().toString().equals(""))
                            input.setText("0");

                        orderDetails.setPrice(Integer.parseInt(input.getText().toString()));
                        com.icstudios.hovalotcalc.MakePDFOffer PDFMaker = new MakePDFOffer(getContext(), currentActivity);
                        PDFMaker.createPdf(orderDetails);
                    }
                })
                .setNeutralButton("חזור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dismiss();
                    }
                });

        return alert;

//        return builder
//                .setView(input)
//                .setMessage("הכנס הצעת מחיר")
////                .setNegativeButton(R.string.close_validation, new DialogInterface.OnClickListener() {
////
////                    @Override
////                    public void onClick(DialogInterface arg0, int arg1) {
////                        getActivity().finishAffinity();
////                    }
////                })
//
//                .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                        if(input.getText().toString().equals(""))
//                            input.setText("0");
//
//                        orderDetails.setPrice(Integer.parseInt(input.getText().toString()));
//                        com.icstudios.hovalotcalc.MakePDFOffer PDFMaker = new MakePDFOffer(getContext(), currentActivity);
//                        PDFMaker.createPdf(orderDetails);
//                    }
//                })
//                .setNeutralButton("חזור", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        dismiss();
//                    }
//                }).create();


    }

}
