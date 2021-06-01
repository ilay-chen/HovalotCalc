package com.icstudios.hovalotcalc;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.icstudios.hovalotcalc.appData.allOrders;

public class OrderObject implements Serializable {
    String clientName;
    String phoneNumber;
    String email;
    String fromCity, fromStreet, fromNumber, fromFloor, fromHome;
    Boolean fromElevator, fromCrane, fromPacking;
    String toCity, toStreet, toNumber, toFloor, toHome;
    Boolean toElevator, toCrane, toPacking;
    String Date;
    String Hour;
    String boxes, bags, suitcases;
    String notes;
    //ArrayList<RoomLayout> roomsAndItems;
    ArrayList<roomObject> roomsAndItems;
    int price;
    String id;

    public OrderObject()
    {
        roomsAndItems = new ArrayList<>();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromStreet() {
        return fromStreet;
    }

    public void setFromStreet(String fromStreet) {
        this.fromStreet = fromStreet;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getFromFloor() {
        return fromFloor;
    }

    public void setFromFloor(String fromFloor) {
        this.fromFloor = fromFloor;
    }

    public Boolean getFromElevator() {
        return fromElevator;
    }

    public void setFromElevator(Boolean fromElevator) {
        this.fromElevator = fromElevator;
    }

    public Boolean getFromCrane() {
        return fromCrane;
    }

    public void setFromCrane(Boolean fromCrane) {
        this.fromCrane = fromCrane;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToStreet() {
        return toStreet;
    }

    public void setToStreet(String toStreet) {
        this.toStreet = toStreet;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getToFloor() {
        return toFloor;
    }

    public void setToFloor(String toFloor) {
        this.toFloor = toFloor;
    }

    public Boolean getToElevator() {
        return toElevator;
    }

    public void setToElevator(Boolean toElevator) {
        this.toElevator = toElevator;
    }

    public Boolean getToCrane() {
        return toCrane;
    }

    public void setToCrane(Boolean toCrane) {
        this.toCrane = toCrane;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getBoxes() {
        return boxes;
    }

    public void setBoxes(String boxes) {
        this.boxes = boxes;
    }

    public String getBags() {
        return bags;
    }

    public void setBags(String bags) {
        this.bags = bags;
    }

    public String getSuitcases() {
        return suitcases;
    }

    public void setSuitcases(String suitcases) {
        this.suitcases = suitcases;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<roomObject> getRoomsAndItems() {
        return roomsAndItems;
    }

    public void setRoomsAndItems(ArrayList<roomObject> roomsAndItems) {
        this.roomsAndItems = roomsAndItems;
    }

    public Boolean getFromPacking() {
        return fromPacking;
    }

    public Boolean getToPacking() {
        return toPacking;
    }

    public Boolean isPacking() {
        return fromPacking || toPacking;
    }

    public Boolean isCrane() {
        return fromCrane || toCrane;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean allSet(Context context)
    {
        if(clientName==null||clientName.equals("")) {
            Toast.makeText(context,"אנא מלא שם לקוח!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(fromCity==null||fromCity.equals("")) {
            Toast.makeText(context,"אנא מלא עיר מוצא!", Toast.LENGTH_LONG).show();
            return false;
        }
//        if(fromStreet==null||fromStreet.equals("")) {
//            Toast.makeText(context,"אנא מלא רחוב מוצא!", Toast.LENGTH_LONG).show();
//            return false;
//        }
        if(toCity==null||toCity.equals("")) {
            Toast.makeText(context,"אנא מלא עיר יעד!", Toast.LENGTH_LONG).show();
            return false;
        }
//        if(toStreet==null||toStreet.equals("")) {
//            Toast.makeText(context,"אנא מלא רחוב יעד!", Toast.LENGTH_LONG).show();
//            return false;
//        }
        else if(phoneNumber==null||phoneNumber.equals("")) {
            Toast.makeText(context,"אנא מלא מספר לקוח!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(Date==null||Date.equals("")) {
            Toast.makeText(context,"אנא מלא תאריך!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(Hour==null||Hour.equals("")||Hour.equals("בחר שעה")) {
            Toast.makeText(context,"אנא מלא שעת משלוח!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getAllRoomsDetails(ArrayList<RoomLayout> allRooms)
    {
        roomsAndItems = new ArrayList<>();
        for(RoomLayout room : allRooms) {
            ArrayList<itemObject> itemsList = new ArrayList<>();
            for(ItemLayout item : room.getItems())
                itemsList.add(new itemObject(item.getName(), item.getCounter(), item.getDisassemblyAndAssembly().isChecked()
                ,item.getDisassembly().isChecked(),item.getAssembly().isChecked()));
            roomsAndItems.add(new roomObject(room.getRoomName(),itemsList));
        }
    }

    public java.util.Date getDateTime()
    {
        if(getDate() != null && !getDate().equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = dateFormat.parse(getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    public int getHourInDay() {
        String startHour = this.getHour();

        return Integer.parseInt(startHour.substring(0,2));
    }
}

class roomObject implements Serializable {

    String roomName;
    ArrayList<itemObject> mItems;

    public roomObject()
    {
        mItems = new ArrayList<>();
    }

    public roomObject(String roomName, ArrayList<itemObject> mItems )
    {
        this.roomName = roomName;
        this.mItems = mItems;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<itemObject> getmItems() {
        return mItems;
    }

    public void setmItems(ArrayList<itemObject> mItems) {
        this.mItems = mItems;
    }

    public void addItem(itemObject item)
    {
        mItems.add(item);
    }
}

class itemObject implements Serializable {

    String itemName, itemCounter;
    Boolean DisassemblyAndAssembly, Assembly, Disassembly;

    public itemObject()
    {

    }

    public itemObject(String itemName, String itemCounter, Boolean DisassemblyAndAssembly,
                      Boolean Disassembly,Boolean Assembly)
    {
        this.itemName = itemName;
        this.itemCounter = itemCounter;
        this.DisassemblyAndAssembly = DisassemblyAndAssembly;
        this.Disassembly = Disassembly;
        this.Assembly = Assembly;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCounter() {
        return itemCounter;
    }

    public void setItemCounter(String itemCounter) {
        this.itemCounter = itemCounter;
    }

    public Boolean getDisassemblyAndAssembly() {
        return DisassemblyAndAssembly;
    }

    public void setDisassemblyAndAssembly(Boolean disassemblyAndAssembly) {
        DisassemblyAndAssembly = disassemblyAndAssembly;
    }
}