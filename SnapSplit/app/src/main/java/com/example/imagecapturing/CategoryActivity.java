package com.example.imagecapturing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ImageView imgView;
    Button uploadBtn;
    Uri uri;
    TextView pText1, pText2, pText3, pText4;

    String[] responsesArray = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        pText1 = findViewById(R.id.predictedvalue1);
        pText2 = findViewById(R.id.predictedvalue2);
        pText3 = findViewById(R.id.predictedvalue3);
        pText4 = findViewById(R.id.predictedvalue4);

        //Fetching Uploaded Image Uri from previous Activity
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            Uri imageUri = Uri.parse(extras.getString("KEY"));
            uri = imageUri;
            imgView = findViewById(R.id.imageViewCnt2);
            imgView.setImageURI(imageUri);
        }

        uploadBtn = findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ArrayList<Bitmap> imageParts =  splitImage(imgView, 4);

                String base64Uri1 = Base64.encodeToString(getBitmapToByte(imageParts.get(0)), Base64.DEFAULT);
                String base64Uri2 = Base64.encodeToString(getBitmapToByte(imageParts.get(1)), Base64.DEFAULT);
                String base64Uri3 = Base64.encodeToString(getBitmapToByte(imageParts.get(2)), Base64.DEFAULT);
                String base64Uri4 = Base64.encodeToString(getBitmapToByte(imageParts.get(3)), Base64.DEFAULT);


                String getRealUri = getRealPathFromURI(uri);

                //REQUEST RESPONSE FOR PART 1 OF SPLIT IMAGE
                try
                {
                    if (base64Uri1 !=null)
                    {
                        OkHttpClient okHttpClient1 = new OkHttpClient();

                        //Attaching ImageUri to Request head
                        RequestBody formBody = new FormBody.Builder().add("imageuri", base64Uri1).build();

                        //Calling Flask URL to trigger the upload function
                        Request request = new Request.Builder().url("http://192.168.0.141:5000/upload2").post(formBody).build();

                        //Creating Http Request to Flask Server
                        okHttpClient1.newCall(request).enqueue(new Callback()
                        {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e)
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Toast toast = Toast.makeText(CategoryActivity.this,"Network Not Found",Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                            }

                            //Actions after receiving Response from the Flask Server
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {
                                            final String responseData1 = response.body().string();
                                            pText1.setText("Image Part 1 Category Predicted :"+ responseData1);
                                            pText1.setVisibility(View.VISIBLE);
                                            final Toast toast = Toast.makeText(CategoryActivity.this,"Image Part 1 Category Predicted "+ responseData1,Toast.LENGTH_SHORT);
                                            toast.show();
                                            responsesArray[0] = responseData1;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //REQUEST RESPONSE FOR PART 2 OF SPLIT IMAGE
                try
                {
                    if (base64Uri2 !=null)
                    {
                        OkHttpClient okHttpClient2 = new OkHttpClient();

                        //Attaching ImageUri to Request head
                        RequestBody formBody = new FormBody.Builder().add("imageuri", base64Uri2).build();

                        //Calling Flask URL to trigger the upload function
                        Request request = new Request.Builder().url("http://192.168.0.226:5001/upload2").post(formBody).build();

                        //Creating Http Request to Flask Server
                        okHttpClient2.newCall(request).enqueue(new Callback()
                        {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e)
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Toast toast = Toast.makeText(CategoryActivity.this,"Network Not Found",Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                            }

                            //Actions after receiving Response from the Flask Server
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {
                                            final String responseData2 = response.body().string();
                                            pText2.setText("Image Part 2 Category Predicted :"+ responseData2);
                                            pText2.setVisibility(View.VISIBLE);
                                            final Toast toast = Toast.makeText(CategoryActivity.this,"Image Part 2 Category Predicted "+ responseData2,Toast.LENGTH_SHORT);
                                            toast.show();
                                            responsesArray[1] = responseData2;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //REQUEST RESPONSE FOR PART 3 OF SPLIT IMAGE
                try
                {
                    if (base64Uri3 !=null)
                    {
                        OkHttpClient okHttpClient3 = new OkHttpClient();

                        //Attaching ImageUri to Request head
                        RequestBody formBody = new FormBody.Builder().add("imageuri", base64Uri3).build();

                        //Calling Flask URL to trigger the upload function
                        Request request = new Request.Builder().url("http://192.168.0.78:5002/upload2").post(formBody).build();

                        //Creating Http Request to Flask Server
                        okHttpClient3.newCall(request).enqueue(new Callback()
                        {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e)
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Toast toast = Toast.makeText(CategoryActivity.this,"Network Not Found",Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                            }

                            //Actions after receiving Response from the Flask Server
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {
                                            final String responseData3 = response.body().string();
                                            pText3.setText("Image Part 3 Category Predicted :"+ responseData3);
                                            pText3.setVisibility(View.VISIBLE);
                                            final Toast toast = Toast.makeText(CategoryActivity.this,"Image Part 3 Category Predicted "+ responseData3,Toast.LENGTH_SHORT);
                                            toast.show();
                                            responsesArray[3] = responseData3;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //REQUEST RESPONSE FOR PART 4 OF SPLIT IMAGE
                try
                {
                    if (base64Uri4 !=null)
                    {
                        OkHttpClient okHttpClient4 = new OkHttpClient();

                        //Attaching ImageUri to Request head
                        RequestBody formBody = new FormBody.Builder().add("imageuri", base64Uri4).build();

                        //Calling Flask URL to trigger the upload function
                        Request request = new Request.Builder().url("http://192.168.0.141:5003/upload2").post(formBody).build();

                        //Creating Http Request to Flask Server
                        okHttpClient4.newCall(request).enqueue(new Callback()
                        {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e)
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Toast toast = Toast.makeText(CategoryActivity.this,"Network Not Found",Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                            }

                            //Actions after receiving Response from the Flask Server
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        try {
                                            final String responseData4 = response.body().string();
                                            pText4.setText("Image Part 4 Category Predicted :"+ responseData4);
                                            pText4.setVisibility(View.VISIBLE);
                                            final Toast toast = Toast.makeText(CategoryActivity.this,"Image Part 4 Category Predicted "+ responseData4,Toast.LENGTH_SHORT);
                                            toast.show();
                                            responsesArray[4] = responseData4;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //imgView.setImageDrawable(getResources().getDrawable(R.drawable.dummyimage));
                                    }
                                });
                            }
                        });
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Bitmap bm = BitmapFactory.decodeFile(getRealUri);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                String predictedNumber = modOfStringArray(responsesArray);
                String nameOfFile = predictedNumber+"_"+currentDateandTime;
                createDirectoryAndSaveFile(bm, nameOfFile, predictedNumber);
                System.out.println("Responses Array : "+responsesArray[0]+","+responsesArray[1]+","+responsesArray[2]+","+responsesArray[3]);

                Toast toast = Toast.makeText(CategoryActivity.this,"Image Saved to Gallery Successfully",Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }

    //Function to determine Modulus from Array of Responses
    private String modOfStringArray(String[] responsesArray) {
        String output = null;
        int maxOutput = 0;
        HashMap<String, Integer> rMap = new HashMap<>();
        for (int i=0; i<responsesArray.length; i++) {
            if(rMap.get(responsesArray[i]) != null) {
                rMap.put(responsesArray[i], rMap.get(responsesArray[i]) + 1);
            } else {
                rMap.put(responsesArray[i], 1);
            }
        }
        for (Map.Entry<String, Integer> entry : rMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value > maxOutput) {
                maxOutput = value;
                output = key;
            }
        }
        return output;
    }

    //Function to Save Image in External Storage of Main Mobile
    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName, String folderPath) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/PredictedCategories/"+folderPath);

        if (!direct.exists()) {
            File imageDirectory = new File(Environment.getExternalStorageDirectory() + "/PredictedCategories/"+folderPath);
            imageDirectory.mkdirs();
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/PredictedCategories/"+folderPath, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//Function to Split Image into Parts and create a Bitmap of each part
    private ArrayList<Bitmap> splitImage(ImageView imgView, int i) {

        int rows,columns;
        int cHeight,cWidth;

        ArrayList<Bitmap> chunkImages = new ArrayList<Bitmap>(i);

        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        rows = columns = (int) Math.sqrt(i);
        cHeight = bitmap.getHeight() / rows;
        cWidth = bitmap.getWidth() / columns;

        int yCoord = 0;
        for(int x = 0; x < rows; x++) {
            int xCoord = 0;
            for(int y = 0; y < columns; y++) {
                chunkImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, cWidth, cHeight));
                xCoord += cWidth;
            }
            yCoord += cHeight;
        }

        return chunkImages;
    }

    // Function for Extraction of Real Path of the file
    private String getRealPathFromURI(Uri uri)
    {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idxRes = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idxRes);
            cursor.close();
        }
        return result;
    }

    // Creation of Bitmap Object for File Transfer
    private byte[] getFileToByte(String uri)
    {
        Bitmap bm = BitmapFactory.decodeFile(uri);
        ByteArrayOutputStream baOs = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baOs);
        byte[] bytes = baOs.toByteArray();
        return bytes;
    }

    // Creation of Bytes from Bitmap
    private byte[] getBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baOs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baOs);
        byte[] bytes = baOs.toByteArray();
        return bytes;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}