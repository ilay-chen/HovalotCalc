package com.icstudios.hovalotcalc;

import android.content.Context;
import android.widget.Toast;

import com.icstudios.hovalotcalc.ordercreate.RoomLayout;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class OrderObject implements Serializable {
    String clientName;
    String phoneNumber;
    String email;
    String fromCity, fromStreet, fromNumber, fromFloor, fromHome;
    Boolean fromElevator, fromCrane;
    String toCity, toStreet, toNumber, toFloor, toHome;
    Boolean toElevator, toCrane, isPacking;
    String Date;
    String Hour;
    String boxes, bags, suitcases;
    String notes;
    ArrayList<RoomLayout> roomsAndItems;
    int price;
    String id;

    public OrderObject()
    {

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

    public ArrayList<RoomLayout> getRoomsAndItems() {
        return roomsAndItems;
    }

    public void setRoomsAndItems(ArrayList<RoomLayout> roomsAndItems) {
        this.roomsAndItems = roomsAndItems;
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
        else if(phoneNumber==null||phoneNumber.equals("")) {
            Toast.makeText(context,"אנא מלא מספר לקוח!", Toast.LENGTH_LONG).show();
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
}