package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitedFragment extends Fragment {
    ListView lv;
    String currentuser;
    RequestQueue requestQueue;
    List<VisitedModule> mydata=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_visited,null);
        lv=v.findViewById(R.id.listview);
        currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestQueue= Volley.newRequestQueue(getContext());
        visited_place(currentuser);
        return v;
    }

    public void visited_place(final String current_uid){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/visited.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jbo1 = new JSONObject(response);
                    JSONArray ja1=jbo1.getJSONArray("user");
                    for(int i=0;i<ja1.length();i++){
                        JSONObject jbo2 =ja1.getJSONObject(i);
                        String pid=jbo2.getString("pid");
                        String name=jbo2.getString("name");
                        String address=jbo2.getString("address");
                        String description=jbo2.getString("description");
                        String type=jbo2.getString("type");
                        String url=jbo2.getString("url");
                        String rating=jbo2.getString("rating");
                        VisitedModule vm=new VisitedModule();
                        vm.setPid(pid);
                        vm.setName(name);
                        vm.setAddress(address);
                        vm.setDescription(description);
                        vm.setType(type);
                        vm.setUrl(url);
                        vm.setRating(rating);
                        mydata.add(vm);

                    }
                }catch (Exception e){

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("id", current_uid);
                return myMap;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {
                lv.setAdapter(new VisitedApapter(getContext(),mydata));
            }
        });

    }
}
