package com.example.filmindustry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewVacancies extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String [] member_id,type_id,position_vacant,details,no_of_vaccancy,vaccancy_status,date_time,type_name,value,vaccancy_id;
    public static String vid,mid ,tlati,tlongi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vacancies);
        l1=(ListView)findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewVacancies.this;
        String q = "/view_vacancies?log_id=" +sh.getString("log_id", "");
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
                member_id = new String[ja1.length()];
                type_id = new String[ja1.length()];
                position_vacant = new String[ja1.length()];
                details = new String[ja1.length()];
                no_of_vaccancy = new String[ja1.length()];
                vaccancy_status = new String[ja1.length()];
                date_time = new String[ja1.length()];
                type_name = new String[ja1.length()];
                vaccancy_id = new String[ja1.length()];

                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    member_id[i] = ja1.getJSONObject(i).getString("member_id");

                    type_id[i] = ja1.getJSONObject(i).getString("type_id");
                    vaccancy_id[i] = ja1.getJSONObject(i).getString("vaccancy_id");
                    position_vacant[i] = ja1.getJSONObject(i).getString("position_vacant");
                    details[i] = ja1.getJSONObject(i).getString("details");

                    no_of_vaccancy[i] = ja1.getJSONObject(i).getString("no_of_vaccancy");
                    vaccancy_status[i] = ja1.getJSONObject(i).getString("vaccancy_status");
                    date_time[i] = ja1.getJSONObject(i).getString("date_time");

                    type_name[i] = ja1.getJSONObject(i).getString("type_name");





                    value[i] = "position_vacant:" + position_vacant[i] + "\ndetails: " + details[i] + "\n no_of_vaccancy: " + no_of_vaccancy[i]  + "\n vaccancy_status: " + vaccancy_status[i] + "\n date_time: " + date_time[i]+ "\n type_name: " + type_name[i];

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        vid=vaccancy_id[i];
        mid=member_id[i];

        final CharSequence[] items = {"Send Application","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewVacancies.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Send Application")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) ViewVacancies.this;
                    String q = "/apply_vacancy?login_id=" +sh.getString("log_id", "")+"&vacancy_id=" +vid +"&mid="+mid ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(), "successfully Apply", Toast.LENGTH_LONG).show();




                }



                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}