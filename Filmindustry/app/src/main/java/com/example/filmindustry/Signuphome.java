package com.example.filmindustry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Signuphome extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e7,e8,e9,e10,e11,e12;
    RadioButton r1,r2;
    Button b1;
    String fname,lname,photo,address,place,pincode,gender,dob,phone,email,username,password;
    ImageButton i1;
    private int mYear, mMonth, mDay, mHour, mMinute;

    String amount,quantity,total;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuphome);
        e1 = (EditText) findViewById(R.id.firstname);
        e2 = (EditText) findViewById(R.id.lastname);

        i1 = (ImageButton) findViewById(R.id.imageButton);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageOption();
            }
        });

        e3 = (EditText) findViewById(R.id.address);
        e4 = (EditText) findViewById(R.id.place);

        e7 = (EditText) findViewById(R.id.pincode);
        r1 = (RadioButton) findViewById(R.id.male);
        r2 = (RadioButton) findViewById(R.id.female);

        e8 = (EditText) findViewById(R.id.dob);
        e9 = (EditText) findViewById(R.id.phone);
        e10 = (EditText) findViewById(R.id.email);
        e11 = (EditText) findViewById(R.id.username);
        e12 = (EditText) findViewById(R.id.password);


        b1 = (Button) findViewById(R.id.registration);



        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        e8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(Signuphome.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        e8.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = e1.getText().toString();
                lname = e2.getText().toString();

                address = e3.getText().toString();
                place = e4.getText().toString();
                if (r1.isChecked()) {
                    gender = "male";
                } else {
                    gender = "female";
                }
                pincode = e7.getText().toString();
                phone = e9.getText().toString();
                email = e10.getText().toString();
                username = e11.getText().toString();
                password = e12.getText().toString();
                dob=e8.getText().toString();
                sendAttach();


//                JsonReq JR = new JsonReq();
//                JR.json_response = (JsonResponse) Signuphome.this;
//                String q = "/Registration?fname=" + fname + "&lname=" + lname + "&housename=" + address + "&place=" + place + "&pincode=" + pincode + "&phone=" + phone + "&email=" + email + "&username=" + username + "&password=" + password;
//                q = q.replace(" ", "%20");
//                JR.execute(q);

            }
        });
    }
        private void sendAttach () {

            try {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String q = "http://" + Ipsettings.ip + "/api/register";

                Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray, Toast.LENGTH_LONG).show();


                Map<String, byte[]> aa = new HashMap<>();
                aa.put("image", byteArray);
                aa.put("fname", fname.getBytes());
                aa.put("lname", lname.getBytes());
                aa.put("address", address.getBytes());
                aa.put("place", place.getBytes());
                aa.put("gender", gender.getBytes());
                aa.put("pincode", pincode.getBytes());
//                aa.put("pid", pid.getBytes());
                aa.put("dob", dob.getBytes());
                aa.put("phone", phone.getBytes());
                aa.put("email", email.getBytes());
                aa.put("username", username.getBytes());
                aa.put("password", password.getBytes());

//                aa.put("log_id",sh.getString("log_id","").getBytes());
//            aa.put("house",house.getBytes());

                FileUploadAsync fua = new FileUploadAsync(q);
                fua.json_response = (JsonResponse) Signuphome.this;
                fua.execute(aa);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
            }
        }


        private void selectImageOption () {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

            final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Signuphome.this);
            builder.setTitle("Take Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Capture Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        startActivityForResult(intent, CAMERA_PIC_REQUEST);

                    } else if (items[item].equals("Choose from Gallery")) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, GALLERY_CODE);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }


    @Override
    public void response(JSONObject jo) {
        try {
            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Signuphome.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            }else if (status.equalsIgnoreCase("already")) {
                Toast.makeText(getApplicationContext(), "Username Or Password ALREADY EXIST", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Signuphome.class));

            }else {
                startActivity(new Intent(getApplicationContext(), Signuphome.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
//                path = FileUtils.getPath(this, uri);
                path=FileUtils.getPath(this,uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                i1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                i1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}
