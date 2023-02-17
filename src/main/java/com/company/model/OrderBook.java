package com.company.model;

import java.util.Objects;

public class OrderBook {

    private int price;
    private int size;
    private String type;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  "price=" + price +
                ", size=" + size +
                ", type='" + type + '\'' ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBook orderBook = (OrderBook) o;
        return price == orderBook.price && size == orderBook.size && Objects.equals(type, orderBook.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, size, type);
    }
}
