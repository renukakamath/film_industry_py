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

public class ViewAuditioncalls extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String [] title,required_numbers,venue,date_time,audition_status,value;
    public static String phn,uid ,tlati,tlongi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_auditioncalls);
        l1=(ListView)findViewById(R.id.list);
//        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewAuditioncalls.this;
        String q = "/view_auditions?log_id=" +sh.getString("log_id", "");
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
                required_numbers = new String[ja1.length()];
                venue = new String[ja1.length()];

                date_time = new String[ja1.length()];
                audition_status = new String[ja1.length()];

                value = new String[ja1.length()];





                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    title[i] = ja1.getJSONObject(i).getString("title");

                    required_numbers[i] = ja1.getJSONObject(i).getString("required_numbers");
                    venue[i] = ja1.getJSONObject(i).getString("venue");
                    date_time[i] = ja1.getJSONObject(i).getString("date_time");
                    audition_status[i] = ja1.getJSONObject(i).getString("audition_status");





                    value[i] = "title:" + title[i] + "\nrequired_numbers: " + required_numbers[i] + "\n venue: " + venue[i] + "\n date_time: " + date_time[i]+ "\n audition_status: " + audition_status[i] ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}