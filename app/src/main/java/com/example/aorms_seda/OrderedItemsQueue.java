package com.example.aorms_seda;

import java.util.ArrayList;
import java.util.List;

public class OrderedItemsQueue {
    List<OrderItem> orderItemList;
    String orderId;
    String billId;
    String priority;
    String stat;
    private static OrderedItemsQueue single_instance=null;
    int currentno = -1;
    static int tableNum;

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

    static void  emptycart(){
        single_instance = null;
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

    public Boolean isEmpty(){
        return this.orderItemList.size() == 0 ?  true : false;
    }
}
