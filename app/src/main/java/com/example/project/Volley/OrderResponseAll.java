package com.example.project.Volley;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponseAll {
    private String message;

    @SerializedName("order")
    private List<Order> orderList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
