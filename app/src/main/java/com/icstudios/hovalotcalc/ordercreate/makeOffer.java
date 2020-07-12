package com.icstudios.hovalotcalc.ordercreate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.icstudios.hovalotcalc.OrderObject;
import com.icstudios.hovalotcalc.R;

import org.jetbrains.annotations.Nullable;

public class makeOffer extends DialogFragment {

    private static OrderObject orderDetails;

    public makeOffer()
    {

    }

    public static makeOffer newInstance(OrderObject orderDetails) {
        // Required empty public constructor
        makeOffer frag = new makeOffer();
        makeOffer.orderDetails = orderDetails;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
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
                        MakePDFOffer PDFMaker = new MakePDFOffer(getContext());
                        PDFMaker.createPdf(orderDetails);
                    }
                })
                .setNeutralButton("חזור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dismiss();
                    }
                }).create();


    }
}
