package com.example.suketurastogi.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductsListActivity extends AppCompatActivity {

    //List View in which all the products names will be visible.
    private ListView productsList;
    TextView productsListEmptyTextView;

    //Array list in which all products names are saved.
    final ArrayList<ProductsList> productsArrayList = new ArrayList<>();

    //Custom Adapter to show Branch list.
    private ProductsListAdapter productsListAdapter;

    //Initializing SQLite Database
    DatabaseHelper productsListDb;

    private String currentProductId;
    private String currentProductName;
    private String currentProductPrice;
    private String currentProductQuantity;
    private String currentProductSale;
    private String currentProductImagePath;
    private Button addNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list);

        productsListDb = new DatabaseHelper(this);

        productsList = (ListView) findViewById(R.id.products_list);
        productsListEmptyTextView = (TextView)findViewById(R.id.no_products_text_view);
        productsList.setEmptyView(productsListEmptyTextView);

        productsListAdapter = new ProductsListAdapter(ProductsListActivity.this, productsArrayList);

        addNewProduct = (Button)findViewById(R.id.add_new_product_button);

        generateProductsList();

        addNewProductOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateProductsList();
    }

    private void generateProductsList(){

        clearingOldListsFirst();

        //Generating Data From SQLite DataBase

        Log.e("Proceeding Offline...", "Proceeding Offline...");

        Cursor res = productsListDb.getAllData();

        if (res.getCount() != 0) {

            while (res.moveToNext()) {

                String productId = res.getString(0);
                String productName = res.getString(1);
                String productPrice = res.getString(2);
                String productQuantity = res.getString(3);
                String productSale = res.getString(4);
                String productImagePath = res.getString(5);

                productsArrayList.add(new ProductsList(productId, productName, productPrice, productQuantity, productSale, productImagePath));

                productsList.setAdapter(productsListAdapter);

                OnProductItemSelection();
            }
        }
    }

    private void clearingOldListsFirst() {

        productsArrayList.clear();
        productsListAdapter.notifyDataSetChanged();
    }

    private void OnProductItemSelection(){

        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductsList product = productsArrayList.get(position);
                currentProductId = product.getProductId();
                currentProductName = product.getProductName();
                currentProductPrice = product.getProductPrice();
                currentProductQuantity = product.getProductQuantity();
                currentProductSale = product.getProductSale();
                currentProductImagePath = product.getProductImagePath();

                Intent schemeListIntent = new Intent(ProductsListActivity.this, DetailsActivity.class);
                schemeListIntent.putExtra("currentProductId", currentProductId);
                schemeListIntent.putExtra("currentProductName", currentProductName);
                schemeListIntent.putExtra("currentProductPrice", currentProductPrice);
                schemeListIntent.putExtra("currentProductQuantity", currentProductQuantity);
                schemeListIntent.putExtra("currentProductSale", currentProductSale);
                schemeListIntent.putExtra("currentProductImagePath", currentProductImagePath);
                startActivity(schemeListIntent);
            }
        });
    }

    private void addNewProductOnClick(){

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewProductIntent = new Intent(ProductsListActivity.this, NewProductActivity.class);
                startActivity(addNewProductIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
