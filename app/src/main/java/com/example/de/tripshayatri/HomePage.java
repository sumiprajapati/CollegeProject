package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
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

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout mainFrame;
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;
    VisitedFragment visitedFragment;
    TextView textView;
    String currentuserid,name;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        textView=findViewById(R.id.welcomview);
        mainFrame=findViewById(R.id.mainframe);
        bottomNavigationView =findViewById(R.id.navigation);
        homeFragment=new HomeFragment();
        profileFragment=new ProfileFragment();
        searchFragment=new SearchFragment();
        visitedFragment=new VisitedFragment();
        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        getUser(currentuserid);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.search:
                        setFragment(searchFragment);
                        return true;
                    case R.id.visited:
                        setFragment(visitedFragment);
                        return true;
                    case R.id.profile:
                        setFragment(profileFragment);
                        return true;
                        default:
                            return  false;
                }

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fragment);
        fragmentTransaction.commit();
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
                    textView.setText("Welcome "+name);
                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(getApplicationContext(), "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                    progressDialog.dismiss();
                Log.d("VAL", ""+error);
                Toast.makeText(getApplicationContext(), "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("id", currentuserid);
                return myMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
