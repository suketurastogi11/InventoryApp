package com.example.suketurastogi.inventoryapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductsListAdapter extends ArrayAdapter<ProductsList> {

    Integer trackSaleQuantity;

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

        final ProductsList currentProduct = getItem(position);

        TextView productName = (TextView)listItemView.findViewById(R.id.products_name_list_item);
        String productNameText = "Product Name : " + currentProduct.getProductName();
        productName.setText(productNameText);

        TextView productPrice = (TextView)listItemView.findViewById(R.id.products_price_list_item);
        String productPriceText = "Price : " + currentProduct.getProductPrice();
        productPrice.setText(productPriceText);

        TextView productQuantity = (TextView)listItemView.findViewById(R.id.products_quantity_list_item);
        String productQuantityText = "Quantity : " + currentProduct.getProductQuantity();
        productQuantity.setText(productQuantityText);

        final TextView productSale = (TextView)listItemView.findViewById(R.id.products_sale_list_item);
        String productSaleText = "Sale Quantity: " + currentProduct.getProductSale();
        productSale.setText(productSaleText);

        trackSaleQuantity = Integer.parseInt(currentProduct.getProductSale());

        final Button trackSaleButton = (Button)listItemView.findViewById(R.id.track_sale_list_item_button);

        trackSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Sale Track","Sale Track");

                if (trackSaleQuantity > 0){

                    DatabaseHelper inventoryDb = new DatabaseHelper(getContext());

                    trackSaleQuantity = trackSaleQuantity - 1;

                    inventoryDb.updateTrackSale(currentProduct.getProductId(),trackSaleQuantity);

                    String productSaleText = "Sale Quantity: " + trackSaleQuantity;
                    productSale.setText(productSaleText);
                }else {
                    Toast.makeText(getContext(),"You cannot reduce Sales beyond Zero",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final View finalListItemView = listItemView;
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentProductId = currentProduct.getProductId();
                String currentProductName = currentProduct.getProductName();
                String currentProductPrice = currentProduct.getProductPrice();
                String currentProductQuantity = currentProduct.getProductQuantity();
                String currentProductImagePath = currentProduct.getProductImagePath();
                String currentProductSale = currentProduct.getProductSale();

                Intent schemeListIntent = new Intent(getContext(), DetailsActivity.class);
                schemeListIntent.putExtra("currentProductId", currentProductId);
                schemeListIntent.putExtra("currentProductName", currentProductName);
                schemeListIntent.putExtra("currentProductPrice", currentProductPrice);
                schemeListIntent.putExtra("currentProductQuantity", currentProductQuantity);
                schemeListIntent.putExtra("currentProductSale", currentProductSale);
                schemeListIntent.putExtra("currentProductImagePath", currentProductImagePath);
                finalListItemView.getContext().startActivity(schemeListIntent);
            }
        });

        return listItemView;
    }
}