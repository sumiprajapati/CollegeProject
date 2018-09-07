package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test extends AppCompatActivity{
    String[][] data=new String[50][50];
    TextView t;
    RequestQueue requestQueue;
    Button b1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        t=findViewById(R.id.tv);
        b1=findViewById(R.id.click);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
//            String[][] mydata=alldata();
            @Override
            public void onClick(View view) {
                String[][] mydata=alldata();
                t.setText(mydata[1][0]);
            }
        });

    }
    public String[][] alldata(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/show.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    for(int i=0;i<a1.length();i++){
                        JSONObject obj2=a1.getJSONObject(i);
                        data[i][0]= obj2.getString("username");

                    }
//                    t.setText(data[0][0]);
                    Toast.makeText(Test.this, data[0][0], Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(Test.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Log.d("VAL", ""+error);
                Toast.makeText(Test.this, "Error in connection.", Toast.LENGTH_LONG).show();
//                Log.d("VAL", ""+error);
            }
        });
        requestQueue.add(stringRequest);
        return data;
    }
}
