package com.machine.test.model;

/**
 * Created by DELL on 11/29/2016.
 */

public class Product {

    private String productname;
    private String price;
    private String vendorname;
    private String vendoraddress;
    private String productImg;
    private String phoneNumber;

    public Product(String productname, String price, String vendorname, String vendoraddress, String productImg, String phoneNumber)
    {

        this.productname= productname;
        this.price=price;
        this.vendorname=vendorname;
        this.vendoraddress=vendoraddress;
        this.productImg=productImg;
        this.phoneNumber=phoneNumber;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getVendoraddress() {
        return vendoraddress;
    }

    public void setVendoraddress(String vendoraddress) {
        this.vendoraddress = vendoraddress;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



}
