package com.example.suketurastogi.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    String productId;
    String productName;
    String productPrice;
    String productQuantity;
    String productSale;
    String productImagePath;

    TextView productNameTextView;
    TextView productQuantityTextView;
    TextView productPriceTextView;
    TextView productSaleTextView;

    ImageView productImageView;

    Button productTrackSaleButton;
    Button productReceivedShipmentButton;
    Button productOrderFromSupplierButton;
    Button productDeleteRecord;

    Integer productCurrentQuantity;
    Integer productCurrentSale;

    DatabaseHelper productDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        productDb = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        productId = bundle.getString("currentProductId");
        productName = bundle.getString("currentProductName");
        productPrice = bundle.getString("currentProductPrice");
        productQuantity = bundle.getString("currentProductQuantity");
        productSale = bundle.getString("currentProductSale");
        productImagePath = bundle.getString("currentProductImagePath");

        productCurrentQuantity = Integer.parseInt(productQuantity);
        productCurrentSale = Integer.parseInt(productSale);

        productNameTextView = (TextView) findViewById(R.id.product_name_text_view);
        productQuantityTextView = (TextView) findViewById(R.id.products_quantity_text_view);
        productPriceTextView = (TextView) findViewById(R.id.products_price_text_view);
        productSaleTextView = (TextView) findViewById(R.id.products_sale_text_view);

        productImageView = (ImageView) findViewById(R.id.product_image);

        productTrackSaleButton = (Button) findViewById(R.id.track_sale_button);
        productReceivedShipmentButton = (Button) findViewById(R.id.received_shipment_button);
        productOrderFromSupplierButton = (Button) findViewById(R.id.order_supplier_button);
        productDeleteRecord = (Button) findViewById(R.id.delete_entire_product_record_button);

        String productNameText = "Product Name : " + productName;
        String productPriceText = "Price : " + productPrice;
        String productQuantityText = "Quantity : " + productQuantity;
        String productSaleText = "Sale Quantity: " + productSale;

        productNameTextView.setText(productNameText);
        productQuantityTextView.setText(productQuantityText);
        productPriceTextView.setText(productPriceText);
        productSaleTextView.setText(productSaleText);

        setPic();

        productTrackSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productCurrentQuantity > 0) {
                    productCurrentQuantity = productCurrentQuantity - 1;
                    String productQuantityText = "Quantity : " + productCurrentQuantity;
                    productQuantityTextView.setText(productQuantityText);

                    productCurrentSale = productCurrentSale + 1;
                    String productSaleText = "Sale Quantity: " + productCurrentSale;
                    productSaleTextView.setText(productSaleText);

                    productDb.updateProductSale(productId,productCurrentQuantity,productCurrentSale);
                } else {
                    Toast.makeText(getApplicationContext(), "No Quantity for Sale", Toast.LENGTH_LONG).show();
                }
            }
        });

        productReceivedShipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productCurrentQuantity = productCurrentQuantity + 1;
                String productQuantityText = "Quantity : " + productCurrentQuantity;
                productQuantityTextView.setText(productQuantityText);

                productDb.updateProductQuantity(productId,productCurrentQuantity);
            }
        });

        productOrderFromSupplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"Supplier@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Want To order Product " + productName);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        productDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteEntireRecordAlertDialog = new AlertDialog.Builder(DetailsActivity.this);
                deleteEntireRecordAlertDialog.setTitle("Are You Sure...??? ");
                deleteEntireRecordAlertDialog.setMessage
                        ("You want to delete this Product");

                deleteEntireRecordAlertDialog.setPositiveButton("Yes.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        productDb.deleteData(productId);
                        Intent Intent = new Intent(DetailsActivity.this, ProductsListActivity.class);
                        startActivity(Intent);
                    }
                });

                deleteEntireRecordAlertDialog.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                deleteEntireRecordAlertDialog.setCancelable(true);
                deleteEntireRecordAlertDialog.show();

            }
        });
    }

    private void setPic() {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(productImagePath, bmOptions);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(productImagePath, bmOptions);
        productImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent Intent = new Intent(DetailsActivity.this, ProductsListActivity.class);
        startActivity(Intent);
    }
}
