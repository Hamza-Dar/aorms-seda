package com.example.aorms_seda;

import java.util.ArrayList;
import java.util.List;

public class OrderedItemsQueue {
    List<OrderItem> orderItemList;

    private static OrderedItemsQueue single_instance=null;



    private OrderedItemsQueue()
    {
        orderItemList = new ArrayList<OrderItem>();
    }



    private OrderedItemsQueue(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }


    public static OrderedItemsQueue Singleton()
    {
        if (single_instance == null)
        {
            single_instance = new OrderedItemsQueue();
        }
        return single_instance;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItemList.add(orderItem);
    }
}
