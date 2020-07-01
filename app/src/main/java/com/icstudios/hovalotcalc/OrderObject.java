package com.icstudios.hovalotcalc;

import java.util.ArrayList;

public class OrderObject {
    String clientName;
    String phoneNumber;
    String email;
    String fromCity, fromStreet, fromNumber, fromFloor;
    Boolean fromElevator, fromCrane;
    String toCity, toStreet, toNumber, toFloor;
    Boolean toElevator, toCrane;
    String Date;
    String Hour;
    int boxes, bags, suitcases;
    String notes;
    ArrayList<roomItems> roomsAndItems;

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

    public int getBoxes() {
        return boxes;
    }

    public void setBoxes(int boxes) {
        this.boxes = boxes;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public int getSuitcases() {
        return suitcases;
    }

    public void setSuitcases(int suitcases) {
        this.suitcases = suitcases;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<roomItems> getRoomsAndItems() {
        return roomsAndItems;
    }

    public void setRoomsAndItems(ArrayList<roomItems> roomsAndItems) {
        this.roomsAndItems = roomsAndItems;
    }
}

 class roomItems {
     ArrayList<String> items;
     String roomName;

     public roomItems()
     {

     }

     public ArrayList<String> getItems() {
         return items;
     }

     public void setItems(ArrayList<String> items) {
         this.items = items;
     }

     public String getRoomName() {
         return roomName;
     }

     public void setRoomName(String roomName) {
         this.roomName = roomName;
     }
}
