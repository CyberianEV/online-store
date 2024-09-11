package org.store.api;

public class OrderDetails {
    private String deliveryAddress;
    private String phoneNumber;

    public OrderDetails() {
    }

    public OrderDetails(String deliveryAddress, String phoneNumber) {
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
