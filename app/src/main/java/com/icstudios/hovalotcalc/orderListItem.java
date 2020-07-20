package com.icstudios.hovalotcalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.icstudios.hovalotcalc.appData.allOrders;

public class orderListItem {

    /**
     * An array of sample (OrderObject) items.
     */
    public static List<OrderItem> ITEMS = new ArrayList<OrderItem>();

    /**
     * A map of sample (OrderItem) items, by ID.
     */
    public static Map<String, OrderItem> ITEM_MAP = new HashMap<String, OrderItem>();

    static {
        for (int i = 0; i < allOrders.size(); i++)
        {
            addItem(createOrderItem(i, allOrders.get(i)));
        }
    }
    public orderListItem() { }

    public static void refreshList()
    {
        ITEMS = new ArrayList<OrderItem>();
        ITEM_MAP = new HashMap<String, OrderItem>();
        for (int i = 0; i < allOrders.size(); i++)
        {
            addItem(createOrderItem(i, allOrders.get(i)));
        }
    }

    private static void addItem(OrderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static OrderItem createOrderItem(int position, OrderObject orderObject) {
        return new OrderItem(String.valueOf(position), orderObject);
    }

    /**
     * A order item representing a piece of content.
     */
    public static class OrderItem {
        public final String id;
        public final String name;
        public final String phone;

        public OrderItem(String id, OrderObject orderObject) {
            this.id = id;
            this.name = orderObject.clientName;
            this.phone = orderObject.phoneNumber;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}