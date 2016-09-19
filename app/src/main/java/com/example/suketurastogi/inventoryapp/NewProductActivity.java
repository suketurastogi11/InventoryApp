package com.example.suketurastogi.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewProductActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    EditText productNameEditText;
    EditText productQuantityEditText;
    EditText productPriceEditText;

    Button productImagePathButton;
    Button submitProductDetailsButton;

    ImageView newProductImage;

    Boolean imageStatus = false;

    DatabaseHelper productDb;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        productDb = new DatabaseHelper(this);

        productNameEditText = (EditText) findViewById(R.id.add_new_product_name_edit_text);
        productQuantityEditText = (EditText) findViewById(R.id.add_new_product_quantity_edit_text);
        productPriceEditText = (EditText) findViewById(R.id.add_new_product_price_edit_text);

        productImagePathButton = (Button) findViewById(R.id.add_image_of_product_button);
        submitProductDetailsButton = (Button) findViewById(R.id.submit_product_details_button);

        newProductImage = (ImageView) findViewById(R.id.new_product_image);

        productImagePathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageStatus = false;
                dispatchTakePictureIntent();

            }
        });

        submitProductDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productNameEditText.length() != 0 && productQuantityEditText.length() != 0 &&
                        productPriceEditText.length() != 0 && imageStatus) {

                    String productName = productNameEditText.getText().toString();
                    Integer productQuantity = Integer.parseInt(productQuantityEditText.getText().toString());
                    Integer productPrice = Integer.parseInt(productPriceEditText.getText().toString());
                    Integer productSale = 0;

                    productDb.insertData(productName, productPrice,productQuantity, productSale, imagePath);

                    Intent schemeListIntent = new Intent(NewProductActivity.this, ProductsListActivity.class);
                    startActivity(schemeListIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter All Details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.suketurastogi.inventoryapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED){
            if (requestCode == REQUEST_TAKE_PHOTO && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                newProductImage.setImageBitmap(photo);
            }

            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(image.getAbsolutePath(), Context.MODE_PRIVATE);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        if (!imageStatus){
            imagePath = image.getAbsolutePath();
            imageStatus = true;
        }
        return image;
    }
}

