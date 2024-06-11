package com.example.filmindustry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_my_portfolio extends AppCompatActivity implements JsonResponse{
    ListView l1;
    SharedPreferences sh;
    String [] title,description,image,value;
    public static String phn,uid ,tlati,tlongi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_portfolio);
        l1=(ListView)findViewById(R.id.list);
//        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_my_portfolio.this;
        String q = "/View_my_portfolio?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                title = new String[ja1.length()];
                description = new String[ja1.length()];


                image = new String[ja1.length()];


                value = new String[ja1.length()];





                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    title[i] = ja1.getJSONObject(i).getString("title");

                    description[i] = ja1.getJSONObject(i).getString("descriptions");
                    image[i] = ja1.getJSONObject(i).getString("image_path");






                    value[i] = "title:" + title[i] + "\ndescription: " + description[i]  ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);
                Custimage a=new Custimage(this,title,description,image);
                l1.setAdapter(a);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }

    }
}