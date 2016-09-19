package com.example.suketurastogi.inventoryapp;

public class ProductsList {

    private String mProductId;
    private String mProductName;
    private String mProductPrice;
    private String mProductQuantity;
    private String mProductSale;
    private String mProductImagePath;

    public ProductsList(String productId,String productName, String productPrice, String productQuantity, String productSale, String productImagePath) {

        mProductId = productId;
        mProductName = productName;
        mProductPrice = productPrice;
        mProductQuantity = productQuantity;
        mProductSale = productSale;
        mProductImagePath = productImagePath;
    }

    public String getProductId() {
        return mProductId;
    }
    public String getProductName() {
        return mProductName;
    }
    public String getProductPrice() {
        return mProductPrice;
    }
    public String getProductQuantity() {
        return mProductQuantity;
    }
    public String getProductSale() {
        return mProductSale;
    }
    public String getProductImagePath() {
        return mProductImagePath;
    }


}
