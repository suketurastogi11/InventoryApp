package com.example.suketurastogi.inventoryapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductsListAdapter extends ArrayAdapter<ProductsList> {

    public ProductsListAdapter(Activity context, ArrayList<ProductsList> product) {
        super(context, 0, product);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.products_list_item, parent, false);
        }

        ProductsList currentProduct = getItem(position);

        TextView productName = (TextView)listItemView.findViewById(R.id.products_name_list_item);
        String productNameText = "Product Name : " + currentProduct.getProductName();
        productName.setText(productNameText);

        TextView productPrice = (TextView)listItemView.findViewById(R.id.products_price_list_item);
        String productPriceText = "Price : " + currentProduct.getProductPrice();
        productPrice.setText(productPriceText);

        TextView productQuantity = (TextView)listItemView.findViewById(R.id.products_quantity_list_item);
        String productQuantityText = "Quantity : " + currentProduct.getProductQuantity();
        productQuantity.setText(productQuantityText);

        TextView productSale = (TextView)listItemView.findViewById(R.id.products_sale_list_item);
        String productSaleText = "Sale Quantity: " + currentProduct.getProductSale();
        productSale.setText(productSaleText);

        return listItemView;
    }
}