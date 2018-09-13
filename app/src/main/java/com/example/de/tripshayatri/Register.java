package com.example.de.tripshayatri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity{
    EditText name,username,email,password,interest;
    Button submit;
    FirebaseAuth auth;
    ProgressDialog dialog;
    RequestQueue requestQueue;
    TextView cancel;
    ImageButton check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        dialog=new ProgressDialog(Register.this);
        dialog.setMessage("Verifying User ...... Please Wait");
        dialog.setCancelable(false);
        auth= FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        check=findViewById(R.id.check);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        interest=findViewById(R.id.interests);
        submit=findViewById(R.id.register);
        cancel=findViewById(R.id.cancel);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkusername=username.getText().toString();
                if (TextUtils.isEmpty(checkusername)){
                    Toast.makeText(Register.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    check(checkusername);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Register.this,Test.class);
                startActivity(i);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myemail=email.getText().toString();
                final String mypassword=password.getText().toString();
                final String myname=name.getText().toString();
                final String myusername=username.getText().toString();
                final String myinterest=interest.getText().toString();
                // intrest ko lagi baki cha//
                if (TextUtils.isEmpty(myemail) || TextUtils.isEmpty(mypassword)|| TextUtils.isEmpty(myname)|| TextUtils.isEmpty(myusername)) {
                    Toast.makeText(Register.this, "Empty Field Detected", Toast.LENGTH_SHORT).show();
                }

                else{
                    dialog.show();
                    auth.createUserWithEmailAndPassword(myemail,mypassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String myid=authResult.getUser().getUid();
                            dialog.dismiss();
                            insert(myid,myemail,mypassword,myname,myusername,myinterest);
//                          Toast.makeText(Register.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                          //ya sql database ko part lekhney
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
    public void insert(final String uid,final String email, final String password, final String name, final String username,final String interests) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/insert.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray o1=new JSONArray(response);
                    JSONObject obj1 = o1.getJSONObject(0);
                    int success = obj1.getInt("code");
                    if(success == 1){
                        dialog.dismiss();
                        Toast.makeText(Register.this, "Data Inserted.", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(Register.this, MainActivity.class);
////                            startActivity(in);


                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(Register.this, "Data insertation failed.", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                   dialog.dismiss();
                    Log.d("VAL", ""+e);
                    Toast.makeText(Register.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
//                    progressDialog.dismiss();
                Log.d("VAL", ""+error);
                Toast.makeText(Register.this, "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("uid", uid);
                myMap.put("username", username);
                myMap.put("password", password);
                myMap.put("name", name);
                myMap.put("email", email);
                myMap.put("interest",interests);
                return myMap;
            }
        };
        requestQueue.add(stringRequest);
    }



    public void check(final String username) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/check.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray o1=new JSONArray(response);
                    JSONObject obj1 = o1.getJSONObject(0);
                    int success = obj1.getInt("code");
                    if(success == 1){
                        dialog.dismiss();
                        Toast.makeText(Register.this, "Username Used", Toast.LENGTH_LONG).show();
//                        Intent in = new Intent(Register.this, MainActivity.class);
////                            startActivity(in);


                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(Register.this, "Username Available.", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    dialog.dismiss();
                    Log.d("VAL", ""+e);
                    Toast.makeText(Register.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
//                    progressDialog.dismiss();
                Log.d("VAL", ""+error);
                Toast.makeText(Register.this, "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("username", username);
                return myMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
