package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileFragment extends Fragment {
    String name,username,password,email,interests;
    TextView tname,tusername,temail,tinterest,ttopname;
    String currentuserid;
    RequestQueue requestQueue;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,null);
        tname=v.findViewById(R.id.name);
        ttopname=v.findViewById(R.id.topname);
        tusername=v.findViewById(R.id.username);
        temail=v.findViewById(R.id.email);
        tinterest=v.findViewById(R.id.interest);
        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestQueue = Volley.newRequestQueue(getContext());
        getUser(currentuserid);

//        ((HomePage)getActivity()).getUser("");
        return v;
    }
    public void getUser(final String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/getuserinformation.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    JSONObject obj2=a1.getJSONObject(0);
                    name=obj2.getString("name");
                    username=obj2.getString("username");
                    email=obj2.getString("email");
                    interests=obj2.getString("interest");
                    ttopname.setText(name);
                    tusername.setText(username);
                    tname.setText(name);
                    temail.setText(email);
                    tinterest.setText(interests);
                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(getContext(), "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                    progressDialog.dismiss();
                Log.d("VAL", ""+error);
                Toast.makeText(getContext(), "Error in connection.", Toast.LENGTH_LONG).show();
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
    }
}
