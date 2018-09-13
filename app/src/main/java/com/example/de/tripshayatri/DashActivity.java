package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashActivity extends AppCompatActivity {
    TextView t1;
    String currentuserid;
    String[] uname=new String[100];
    String id,name,username;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashlayout);
        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        uname=getUser(currentuserid);
        t1=findViewById(R.id.view);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        uname=getUser(id);
        t1.setText("Welcome "+uname[0]);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setText("Welcome "+uname[0]+"Your name is "+uname[1]);
            }
        });
    }
// from here fetching data from database
    public String[] getUser(final String id){
        final String[] uname=new String[100];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/getuserinformation.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    JSONObject obj2=a1.getJSONObject(0);
                    name=obj2.getString("name");
                     username=obj2.getString("username");
                     uname[0] =username;
                     uname[1]=name;
//                    t1.setText("Welcome "+uname[0]);// ya bata set garda chai display vayo
                    Toast.makeText(DashActivity.this, username, Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(DashActivity.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                    progressDialog.dismiss();
                Log.d("VAL", ""+error);
                Toast.makeText(DashActivity.this, "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("id", id);
                return myMap;
            }
        };
        requestQueue.add(stringRequest);
        return uname;//returning array of all the user
    }


}

