package com.icstudios.hovalotcalc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.icstudios.hovalotcalc.appData.allOrders;
import static com.icstudios.hovalotcalc.appData.removeOrder;
import static com.icstudios.hovalotcalc.appData.sortOrders;

/**
 * A fragment representing a list of Items.
 */
public class OrderFragment extends Fragment implements MyOrderFragmentRecyclerViewAdapter.ItemClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int PERMISSIONS_REQUEST_PHONE_CALL = 1001;
    private int posClick = -1;
    RecyclerView recyclerView;
    MyOrderFragmentRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OrderFragment newInstance(int columnCount) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView = view.findViewById(R.id.list);
        SearchView searchView = view.findViewById(R.id.search_box);

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            final Context context = view.getContext();
            //RecyclerView recyclerView = (RecyclerView) recyclerView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            orderListItem.refreshList();

//            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(context, orderListItem.ITEMS);
//            adapter.setClickListener(this);
//            recyclerView.setAdapter(adapter);

            adapter = new MyOrderFragmentRecyclerViewAdapter(orderListItem.ITEMS);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.filter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return true;
                }
            });

            //recyclerView.setAdapter(new MyOrderFragmentRecyclerViewAdapter(orderListItem.ITEMS));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

//            View innView = inflater.inflate(R.layout.fragment_item, container, false);
//            ImageButton menu = innView.findViewById(R.id.menu_item);
//            menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showPopup(context, posClick);
//                }
//            });

//            recyclerView.addOnItemTouchListener(
//                    new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override public void onItemClick(View view, int position) {
//                                // do whatever
//                            //showPopup(context, position);
//                            if(view.getTag() != null)
//                            if(view.getTag().toString().equals("menu_item"))
//                            {
//                                showPopup(context, position);
//                            }
////                            String aa = view.getTag().toString();
////                            posClick = position;
////                            if(aa.equals("d"))
////                            {
////
////                            }
//                        }
//
//                        @Override public void onLongItemClick(View view, int position) {
//                            // do whatever
//                        }
//                    })
//            );
        }
        return view;
    }

    private void showPopup(final Context context,final int position){
        //Button btn_closepopup=(Button)layout.findViewById(R.id.btn_closePoppup);
        final LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        Button call, sendMessage, watchFile, watchPic, watchVideo, editOrder, deleteOrder, makeEvent;

        View popupView = layoutInflater.inflate(R.layout.popup_menu, null);
        final PopupWindow popupMenu = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
        popupMenu.setAnimationStyle(R.style.Animation);

        makeEvent = popupView.findViewById(R.id.event);
        makeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path1 = appData.getFilePath(allOrders.get(position)) + appData.pdfFileName;
                Uri pdf = Uri.parse(path1);
                File f = new File(path1);
                String det = "";

                String fromAddress = allOrders.get(position).fromCity;

                if(allOrders.get(position).fromStreet != null &&
                        allOrders.get(position).fromStreet != "" &&
                        allOrders.get(position).fromStreet !="רחוב")
                {
                    fromAddress += ", " + allOrders.get(position).fromStreet;
                    if(allOrders.get(position).fromNumber != null &&
                            allOrders.get(position).fromNumber != "" &&
                            !allOrders.get(position).fromNumber.equals("מס"))
                    {
                        fromAddress += ", " + allOrders.get(position).fromNumber;
                    }
                }

                String toAddress = allOrders.get(position).toCity;
                if(allOrders.get(position).toStreet != null &&
                        allOrders.get(position).toStreet != "" &&
                        allOrders.get(position).toStreet !="רחוב")
                {
                    toAddress += ", " + allOrders.get(position).toStreet;
                    if(allOrders.get(position).toNumber != null &&
                            allOrders.get(position).toNumber != "" &&
                            !allOrders.get(position).toNumber.equals("מס"))
                    {
                        toAddress += ", " + allOrders.get(position).toNumber;
                    }
                }

                Date OrderDate= allOrders.get(position).getDateTime();

                det += "כתובת מוצא: " + "\n" + fromAddress + ".\n" + "כתובת יעד: " + "\n" + toAddress + ".\n";

                        det += "\n" + "רשימת פריטים:" + "\n";
                for(int i = 0; i < allOrders.get(position).getRoomsAndItems().size(); i++) {
                    det += "\n";
                    if(!allOrders.get(position).getRoomsAndItems().get(i).roomName.equals(""))
                        det += allOrders.get(position).getRoomsAndItems().get(i).roomName + ":\n\n";
                    for (int j = 0; j < allOrders.get(position).getRoomsAndItems().get(i).mItems.size(); j++) {
                        itemObject thisItem = allOrders.get(position).getRoomsAndItems().get(i).mItems.get(j);
                        det += "- " + thisItem.itemName + " ";

                        String extraDetails = "";
                        if(Integer.parseInt(thisItem.itemCounter) > 1)
                            extraDetails += "(כמות: " + thisItem.itemCounter;

                        if(thisItem.DisassemblyAndAssembly)
                        {
                            if(extraDetails.length() !=0) extraDetails += " ,";
                            else extraDetails += "(";
                            extraDetails += "פירוק והרכבה";
                        }
                        if(thisItem.Disassembly)
                        {
                            if(extraDetails.length() !=0) extraDetails += " ,";
                            else extraDetails += "(";
                            extraDetails += "פירוק";
                        }

                        if(thisItem.Assembly)
                        {
                            if(extraDetails.length() !=0) extraDetails += " ,";
                            else extraDetails += "(";
                            extraDetails += "הרכבה";
                        }

                        if (extraDetails.length()>0) extraDetails += ")";

                        det += extraDetails  + "\n";
                    }
                }

                det += "\n" + "ארגזים: " + allOrders.get(position).boxes + "\n";
                det += "שקיות: " + allOrders.get(position).bags + "\n";
                det += "מזוודות: " + allOrders.get(position).suitcases + "\n" + "\n";

                if (allOrders.get(position).notes != null)
                    det += "הערות: " + "\n" + allOrders.get(position).notes + "\n" + "\n";

                det += "מחיר: " + allOrders.get(position).price + " שקלים." + "\n";

                Calendar beginTime = Calendar.getInstance();
                beginTime.setTime(OrderDate);

                int startHour = 8;
                try {
                    startHour = allOrders.get(position).getHourInDay();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                beginTime.set(Calendar.HOUR_OF_DAY, startHour);
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(OrderDate);
                endTime.set(Calendar.HOUR_OF_DAY, startHour+1);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE,  "הובלה, " + allOrders.get(position).getPhoneNumber())
                        .putExtra(CalendarContract.Events.DESCRIPTION, det)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, fromAddress);
//                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
//                        .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                startActivity(intent);
            }
        });

        call = popupView.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Check the SDK version and whether the permission is already granted or not.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                } else {
                    //Open call function
                    String phone = allOrders.get(position).getPhoneNumber();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            }
        });

        sendMessage = popupView.findViewById(R.id.send_message);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone="+allOrders.get(position).getPhoneNumber().replaceFirst("0","972");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        watchFile = popupView.findViewById(R.id.watch_file);
        watchFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = layoutInflater.inflate(R.layout.image_popup, null);
                final PopupWindow popupImage = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
                PhotoView image = popupView.findViewById(R.id.imageView);
//                String path = appData.getFilePath(allOrders.get(position)) + appData.picFileName;
//                File renderFile = new File(path);
//                image.setImageBitmap(BitmapFactory.decodeFile(renderFile.getAbsolutePath()));
                popupImage.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                try {
                    int REQ_WIDTH = 1;
                    int REQ_HEIGHT = 1;
                    REQ_WIDTH = 600;
                    REQ_HEIGHT = 850;

                    Bitmap bitmap = Bitmap.createBitmap(REQ_WIDTH, REQ_HEIGHT, Bitmap.Config.ARGB_8888);

                    String path1 = appData.getFilePath(allOrders.get(position)) + appData.pdfFileName;

                    File file = new File(path1);

                    PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));

//                    if (currentPage < 0) {
//                        currentPage = 0;
//                    } else if (currentPage > renderer.getPageCount()) {
//                        currentPage = renderer.getPageCount() - 1;
//                    }

                    Matrix matrix = image.getImageMatrix();
                    Rect rect = new Rect(0, 0, REQ_WIDTH, REQ_HEIGHT);

                    renderer.openPage(0).render(bitmap, rect, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                    image.setImageMatrix(matrix);
                    image.setImageBitmap(bitmap);
                    image.invalidate();
                    renderer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        watchPic = popupView.findViewById(R.id.watch_photos);
        watchPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        watchVideo = popupView.findViewById(R.id.watch_videos);
        watchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editOrder = popupView.findViewById(R.id.edit);
        editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click" + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, OrderCreateActivity.class);
                i.putExtra("id", allOrders.get(position).getId());
                startActivity(i);
                popupMenu.dismiss();
            }
        });


        deleteOrder = popupView.findViewById(R.id.delete);
        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete " + position, Toast.LENGTH_SHORT).show();

                removeOrder(allOrders.get(position));

                sortOrders();

                appData.saveData(context);

                orderListItem.refreshList();

                recyclerView.removeViewAt(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, orderListItem.ITEMS.size());

                adapter.notifyDataSetChanged();

//                adapter = new MyOrderFragmentRecyclerViewAdapter(orderListItem.ITEMS);
//                recyclerView.setAdapter(adapter);

                popupMenu.dismiss();
            }
        });


        sendMessage = popupView.findViewById(R.id.send_message);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone="+allOrders.get(position).getPhoneNumber().replaceFirst("0","972");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        popupMenu.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        //chartContainer1.addView(mChart);
//        btn_closepopup.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                pwindo.dismiss();
//            }
//        });
    }

    private void render() {

    }

    @Override
    public void onItemClick(View view, int position) {
        showPopup(this.getContext(), position);
    }
}