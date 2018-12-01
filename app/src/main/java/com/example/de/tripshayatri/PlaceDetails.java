package com.example.de.tripshayatri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import 	java.lang.Math.*;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlaceDetails extends AppCompatActivity{
    String pid,name,address,description,image_url;
    ImageView place_image;
    TextView place_description;
    RatingBar ratingBar;
    Button rate;
    FirebaseAuth auth;
    RequestQueue requestQueue;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_placedetails);
        place_image=findViewById(R.id.placeimage);
        place_description=findViewById(R.id.placedescription);
        ratingBar=findViewById(R.id.ratingBar);
        rate=findViewById(R.id.rate);

        pid=getIntent().getExtras().getString("pid");
        name=getIntent().getExtras().getString("name");
        address=getIntent().getExtras().getString("address");
        description=getIntent().getExtras().getString("description");
        image_url=getIntent().getExtras().getString("url");
        final String currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Glide
                .with(PlaceDetails.this)
                .load(image_url)
                .into(place_image);
        place_description.setText(description);
        dialog=new ProgressDialog(PlaceDetails.this);
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ratingValue= (int) Math.ceil(ratingBar.getRating());
                if(ratingValue<=0){
                    Toast.makeText(PlaceDetails.this, "No rating", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.show();
                    Toast.makeText(PlaceDetails.this, ""+ratingValue, Toast.LENGTH_SHORT).show();
                    //database ko logic lekhne
                    insert(currentuserid,pid,String.valueOf(ratingValue));
                }

            }
        });






    }

    public void insert(final String uid, final String pid, final String rating){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/ratinginsert.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray o1=new JSONArray(response);
                    JSONObject obj1 = o1.getJSONObject(0);
                    int success = obj1.getInt("code");
                    if(success == 1){
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sucessfully Rated.", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(PlaceDetails.this, HomePage.class);
                        startActivity(in);


                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(PlaceDetails.this, "Rating failed.", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    dialog.dismiss();
                    Log.d("VAL", ""+e);
                    Toast.makeText(PlaceDetails.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
//                    progressDialog.dismiss();
                Log.d("VAL", ""+error);
                Toast.makeText(PlaceDetails.this, "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("uid", uid);
                myMap.put("pid", pid);
                myMap.put("rating", rating);

                return myMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}
