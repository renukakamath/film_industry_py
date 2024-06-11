package com.example.filmindustry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class SendReviews extends AppCompatActivity implements JsonResponse {
    EditText e1,e2;
    Button b1;

    String re,des;
    SharedPreferences sh;
    public  static String login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reviews);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.review_title);
        e2=(EditText) findViewById(R.id.description);

        b1=(Button) findViewById(R.id.addreviews);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re=e1.getText().toString();
                des=e2.getText().toString();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) SendReviews.this;
                String q ="/send_review?&login_id="+Login.login_id+"&review_title="+re+"&description="+des;
                q = q.replace(" ","%20");
                JR.execute(q);
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}