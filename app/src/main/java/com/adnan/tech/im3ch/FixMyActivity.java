package com.adnan.tech.im3ch;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Model.ModelAddress;
import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.Api;
import com.adnan.tech.im3ch.Util.BackgroundToast;
import com.adnan.tech.im3ch.Util.GSON_Module;
import com.adnan.tech.im3ch.Util.MyPrefs;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class FixMyActivity extends AppCompatActivity {
    EditText et_location, et_make, et_model, et_year, et_budget, et_description;
    MyPrefs prefs;
    ImageView img_location, img_item;
    String address, lat_long,lat,adlong;
    Button btn_submit;
    String fileuri;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_my);
        context = this;
        prefs = new MyPrefs(this);
        new Anim().Entry(this);
        et_location = findViewById(R.id.et_location);
        et_make = findViewById(R.id.tv_car_makr);
        et_model = findViewById(R.id.tv_car_model);
        et_year = findViewById(R.id.tv_car_year);
        et_budget = findViewById(R.id.et_budget);
        et_description = findViewById(R.id.description);
        img_location = findViewById(R.id.img_location);
        btn_submit = findViewById(R.id.btn_Next);
        img_item = findViewById(R.id.img_item);
        OkHttpClient client = new OkHttpClient();
        apiService = new Retrofit.Builder().baseUrl(new Api().URL + "upload/").client(client).build().create(ApiService.class);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location, make, model, year, budget, description;
                location = et_location.getText().toString();
                make = et_make.getText().toString();
                model = et_model.getText().toString();
                year = et_year.getText().toString();
                budget = et_budget.getText().toString();
                description = et_description.getText().toString();
                //multipartImageUpload();
                if (!(location.isEmpty() && make.isEmpty() && model.isEmpty() && year.isEmpty() && budget.isEmpty() && description.isEmpty())) {
                    multipartImageUpload();
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        jsonObject.put("make", make);
                        jsonObject.put("model", model);
                        jsonObject.put("year", year);
                        jsonObject.put("budget", budget);
                        jsonObject.put("latitude", lat);
                        jsonObject.put("longitude", adlong);
                        jsonObject.put("description", description);
                        jsonObject.put("customerid", prefs.get_Val("id"));//"60cc25b2f40fbb2e8c215ccb"
                        jsonObject.put("name", prefs.get_Val("name"));//"60cc25b2f40fbb2e8c215ccb"
                        jsonObject.put("phone", prefs.get_Val("phone"));//"60cc25b2f40fbb2e8c215ccb"
                        jsonObject.put("dent_type", "indoor");
                        jsonObject.put("Time", timeStamp);
                        jsonObject.put("pics", "");
                        Log.e("test", jsonObject.toString());
                        // jsonObject.put("image", "suzuki");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                    Request request = new Request.Builder()
                            .url(new Api().URL + "car")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    client.newCall(request).enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    new BackgroundToast().showDialog(context,
                                            "Error",
                                            e.getMessage());
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                                                    //loading.dismiss();
                                    try {
                                        //loading.dismiss();
                                        ResponseBody rebody = response.body();
                                        String responseb = rebody.string();

                                        Log.e("test", responseb);
                                        JSONObject json = new JSONObject(responseb);
                                        System.out.println(json.toString());
                                        String message = json.getString("message");
                                        if (message.equalsIgnoreCase("your request Submitted successfully")) {
                                            new BackgroundToast().showDialog(context,
                                                    "Message",
                                                    message);
                                        } else {
                                            new BackgroundToast().showDialog(context,
                                                    "Message",
                                                    message);
                                        }
                                    } catch (Exception ex) {
                                        new BackgroundToast().showDialog(context,
                                                "Error",
                                                ex.getMessage());
                                    }
                                }
                            });
                }else{
                    Toast.makeText(context,"Enter Values",Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_item.setOnClickListener(v -> {
            try {
                mGetContent.launch("image/*");
            } catch (Exception ex) {

            }
        });
        img_location.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("lat_long", lat_long);
            startActivityForResult(intent, 2);
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String val = data.getStringExtra("val");
                GSON_Module gson = new GSON_Module();
                ArrayList<ModelAddress> lst_address = gson._get_address(val);
                address = lst_address.get(0).getAddress();
                lat_long = lst_address.get(0).getLat_long();
                lat = lst_address.get(0).getLat();
                adlong = lst_address.get(0).getlong();
                et_location.setText(address);

            }

        }
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    try {
                        // Handle the returned Uri
                        String picturePath = convertMediaUriToPath(uri);
                        mBitmap = (BitmapFactory.decodeFile(picturePath));
                        fileuri = picturePath;
                        img_item.setImageURI(uri);
                        //img_item.setImageBitmap(mBitmap);
                    }catch (Exception ex){
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

    String convertMediaUriToPath(Uri selectedImage ) {
        String[] filePath = { MediaStore.Images.Media.DATA };
        String[] bits = selectedImage.toString().split("/");
        String filename = new Date().getTime() + bits[bits.length - 1];
        String picturePath=selectedImage.toString();
        if (filename.contains("%3A")) {
            String id = filename.split("%3A")[1];
            // val type=filename.split("%3a", ignoreCase = true).get(0)
            Uri t = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor =context.getContentResolver().query(
                    t,
                    filePath, sel, new String[]{id}, null
            );
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst())
                picturePath = cursor.getString(column_index);
            cursor.close();
        } else {

            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(MediaStore.Images.Media.DATA);
             picturePath = c.getString(columnIndex);
            c.close();
            Log.w("path of image gallery", picturePath + "");
        }
        return picturePath;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
    ApiService apiService;
    Bitmap mBitmap;
    private void multipartImageUpload() {
        try {
            if(mBitmap!=null) {
                File filesDir = getApplicationContext().getFilesDir();
                File file = new File(filesDir, "image" + ".png");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();


                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

                retrofit2.Call<ResponseBody> req = apiService.postImage(body, name);
                req.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }


                });

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int uploadFile(String sourceFileUri) {

        int serverResponseCode=0;
        String[] bits = sourceFileUri.split("/");
        String fileName = new Date().getTime() + bits[bits.length - 1];


        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            //dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    + sourceFileUri + "" + fileName);


            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(new Api().URL + "upload");

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=" + fileName + ";filename=" + fileName);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                 serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    Toast.makeText(FixMyActivity.this, "File Upload Complete.",
                            Toast.LENGTH_SHORT).show();
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                //dialog.dismiss();
                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Upload file to server", "Exception : "
                        + e.getMessage(), e);
            }

            return serverResponseCode;

        } // End else block
    }
}