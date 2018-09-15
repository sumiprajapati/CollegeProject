package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static java.lang.Math.sqrt;

public class HomeFragment extends Fragment {
    int fetch=0;
    String interests=null;
    int interestedplaces=0;
    String[][] interestedplace=new String[30][7];
    String[][] allplace=new String[30][7];
    int tempcount=0;
    int l=0;
    int noofdata=0;
    String currentid;
    RequestQueue requestQueue,requestQueue1,requestQueue2,requestQueue3;
//    String[][] r=new String[30000][3];
    String[][] data1=new String[2000][35];
    String[][] tempdata=new String[2000][35];
    double[] centered=new double[1200];
    int tempcol=0;
    List<String> placedata=new ArrayList<String>();
    List<PlaceInformationModule> mydata=new ArrayList<>();

    RecyclerView rv,rv1,rv2,rv3,rv4,rv5,rv6;
    String[] list = {"subash","subash"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,null);
        rv = v.findViewById(R.id.recyclerview);
        currentid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue1= Volley.newRequestQueue(getContext());
        requestQueue2= Volley.newRequestQueue(getContext());
        requestQueue3= Volley.newRequestQueue(getContext());
        getAllUser();
        getUser(currentid);
//        rearrange();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        rv1 = v.findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv1.setLayoutManager(layoutManager1);
//        rv1.setAdapter(new CustomAdapter2(getContext(), list));

        rv2 = v.findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv2.setLayoutManager(layoutManager2);
        rv2.setAdapter(new CustomAdapter2(getContext(), list));

        rv3 = v.findViewById(R.id.recyclerview3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv3.setLayoutManager(layoutManager3);
        rv3.setAdapter(new CustomAdapter3(getContext(),list));

        rv4 = v.findViewById(R.id.recyclerview4);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv4.setLayoutManager(layoutManager4);
        rv4.setAdapter(new CustomAdapter4(getContext(), list));

        rv5 = v.findViewById(R.id.recyclerview5);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv5.setLayoutManager(layoutManager5);
        rv5.setAdapter(new CustomAdapter5(getContext(), list));

        return v;
    }
    // from here implementation
        //get all the user
    public void getAllUser() {
        final String[][] ratinginfo = new String[30000][3];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/show.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1 = obj1.getJSONArray("user");
                    for (int i = 0; i < a1.length(); i++) {
                        JSONObject obj2 = a1.getJSONObject(i);
                        String uid = obj2.getString("uid");
                        String pid = obj2.getString("pid");
                        String rating = obj2.getString("rating");
                        ratinginfo[i][0] = uid;
                        ratinginfo[i][1] = pid;
                        ratinginfo[i][2] = rating;
                    }
                    noofdata=a1.length();
//                    rearrange(ratinginfo);

                } catch (Exception e) {

                    Log.d("VAL", "" + e);
                    Toast.makeText(getContext(), "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                    progressDialog.dismiss();
                Toast.makeText(getContext(), "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", "" + error);
            }
        });
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {

               rearrange(ratinginfo);


            }
        });
    }

    public void rearrange(String[][] r) {
        TreeSet<String> userid = new TreeSet<>();
        TreeSet<String> placeid = new TreeSet<>();
        String rating = "";
        Map dataMap = new HashMap<>();
        String userlist;
        String placelist;
        for (int i = 0; i < noofdata; i++) {
            userlist = r[i][0];
            placelist = r[i][1];
            Log.d("BAL", "" + userlist);
            userid.add(userlist);
            placeid.add(placelist);
            rating = r[i][2];
            Map<Object, Object> map = (Map<Object, Object>) dataMap.getOrDefault(
                    userlist, new HashMap<>());
            map.put(placelist, rating);
            dataMap.put(userlist, map);
        }
        int i = 1;
        int j = 0;
//        int temprow=0;
        int a = 1;
        for (String t : placeid) {
            data1[0][a] = t;
            a++;
        }
        data1[0][0] = "uid";
        for (String s : userid) {
            j = 1;
            data1[i][0] = s;
            for (String t : placeid) {
                Map<Object, Object> map = (Map<Object, Object>) dataMap.getOrDefault(s, new HashMap<>());
                Object rate = map.getOrDefault(t, "0");
                data1[i][j] = String.valueOf(rate);
//	                System.out.print("\t"+rate);

                j++;
            }
            i++;
//			System.out.println();
            tempcol = j;
        }

        //--------------------------------------------------
        TreeSet<String> user1id = new TreeSet<>();
        TreeSet<String> place1id = new TreeSet<>();
        String rating1 = "";
        Map dataMap1 = new HashMap<>();
        String userlist1;
        String placelist1;
        for (int ab = 0; ab < noofdata; ab++) {
            userlist1 = r[ab][0];
            placelist1 = r[ab][1];
            Log.d("BAL", "" + userlist1);
            user1id.add(userlist1);
            place1id.add(placelist1);
            rating1 = r[ab][2];
            Map<Object, Object> map1 = (Map<Object, Object>) dataMap1.getOrDefault(
                    userlist1, new HashMap<>());
            map1.put(placelist1, rating1);
            dataMap1.put(userlist1, map1);
        }
        int i1 = 1;
        int j1 = 0;
//        int temprow1=0;
        int a1 = 1;
        for (String t1 : place1id) {
            tempdata[0][a1] = t1;
            a1++;
        }
        tempdata[0][0] = "uid";
        for (String s1 : user1id) {
            j1 = 1;
            tempdata[i1][0] = s1;
            for (String t1 : place1id) {
                Map<Object, Object> map1 = (Map<Object, Object>) dataMap1.getOrDefault(s1, new HashMap<>());
                Object rate1 = map1.getOrDefault(t1, "0");
                tempdata[i1][j1] = String.valueOf(rate1);
//	                System.out.print("\t"+rate1);

                j1++;
            }
            i1++;
//			System.out.println();
            tempcol = j1;
        }


        //------------------------------


//        tempdata=data1;
        Log.e("tempcol",String.valueOf(tempcol));
        //here i is no of user and tempcol is total no of place
        String s = String.valueOf(i);
//        lv.setText(s);// this is for checking value of i
        int qa=0;
        for(int p=0;p<tempcol;p++){

            qa++;
            Log.d("asd","Subash"+tempdata[3][p]);
        }
        Log.d("qa",String.valueOf(qa));



        //from herer we are centering the value of each rating
        int count = 0;
        double sum = 0;
        for (int k = 1; k < i; k++) {
            count = 0;
            sum = 0;
            for (int l = 1; l < tempcol; l++) {
                if (!tempdata[k][l].equals("0")) {
                    count++;
                    sum = sum + Integer.parseInt(tempdata[k][l]);

                }
            }
            for (int l = 1; l < tempcol; l++) {
                if (!tempdata[k][l].equals("0")) {
                    tempdata[k][l] = String.valueOf(Integer.parseInt(tempdata[k][l]) - (sum / count));
                }
            }
        }//done
        //finding index for the current user
        //yedi current user id vetayena vaney 0 huncha
        String current_user_id = currentid;
        Log.e("i=",String.valueOf(i));
        int current_user_index = 0;
        for (int k = 1; k < i; k++) {
            if ((tempdata[k][0]).equals(current_user_id)) {
                current_user_index = k;
                break;
            }
        }

        Log.e("index", String.valueOf(current_user_index));
        ////ya samma android mai thik cha
        //cosine similarity  for a particulat user
        if (current_user_index != 0) {
            for (int m = current_user_index; m <= current_user_index; m++) {
                double result = 0;
                for (int k = 1; k < i; k++) {
                    double dotproduct = 0;
                    double temp1 = 0;
                    double temp2 = 0;
                    for (int l = 1; l < tempcol; l++) {
                        double a11 = Double.parseDouble(tempdata[m][l]);
                        double a2 = Double.parseDouble(tempdata[k][l]);
                        dotproduct = (a11) * (a2) + dotproduct;
                        temp1 = temp1 + (a11 * a11);
                        temp2 = temp2 + (a2 * a2);

                    }
                    if (k == m) {
                        centered[k] = (double) (-2);
                    } else {
                        if (dotproduct != 0 || (sqrt(temp1) * sqrt(temp2)) != 0) {
                            centered[k] = dotproduct / (sqrt(temp1) * sqrt(temp2));
                            ;
                        } else {
                            centered[k] = (double) 0;
                        }
                    }
                }
            }
            //cosine similarity  for a particulat user finished


            //similarity user id start
            int index = 0;
            double max = -1;
            for (int k = 1; k < i; k++) {
                if (max < centered[k]) {
                    max = centered[k];
                    index = k;
                }
            }
            Log.e("silimar user index",String.valueOf(index));
            //similarity user id end
            // findind place with high ranking
            Map<Double, String> map = new TreeMap<Double, String>(Collections.reverseOrder());

            for (int m = 1; m < tempcol; m++) {
                Log.e("temp1",data1[current_user_index][m]);
                if ((data1[current_user_index][m].equals("0")) && (!data1[index][m].equals("0"))) {
                    Double ratings = Double.parseDouble(tempdata[index][m]);
                    String place = tempdata[0][m];
                    map.put(ratings, place);

                }
            }
            Log.e("map size",String.valueOf(map.size()));

            //findind place with high ranking done
            placedata.clear();
            for (Map.Entry m : map.entrySet()) {
                placedata.add((m.getValue()).toString());
            }
            Log.e("No of place",String.valueOf(placedata.size()));
            Log.e("placeid",placedata.get(1));
            //} ya samma ko if check vayo

  //-------------------------------------------------------------
  //-------------------------------------------------------------
//            Log.e("hello",mydata.get(l).getPid());
            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/getplaceinformation.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        PlaceInformationModule pm=new PlaceInformationModule();
                        JSONObject obj11 = new JSONObject(response);
                        JSONArray a11=obj11.getJSONArray("user");

                        for(int z=0;z<a11.length();z++) {
                            JSONObject obj21 = a11.getJSONObject(z);
                            String pid = obj21.getString("pid");
                            String name = obj21.getString("name");
                            String address =obj21.getString("address");
                            String description =obj21.getString("description");
                            String lng =obj21.getString("lng");
                            String ltd =obj21.getString("ltd");
                            String url =obj21.getString("url");
//                           Log.e("pid123",pid);
                            allplace[z][0]=pid;
                            allplace[z][1]=name;
                            allplace[z][2]=address;
                            allplace[z][3]=description;
                            allplace[z][4]=lng;
                            allplace[z][5]=ltd;
                            allplace[z][6]=url;
//                            pm.setPid(pid);
//                            pm.setName(name);
//                            pm.setAddress(address);
//                            pm.setDescription(description);
//                            pm.setLng(lng);
//                            pm.setLtd(ltd);
//                            pm.setUrl(url);
//                            mydata.add(pm);
                            }

//                            Log.e("aayo",mydata.get(2).getName());
//                            fetch++;
                            tempcount++;
//                        rv.setAdapter(new CustomAdapter(getContext(), mydata));
//

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
                    Toast.makeText(getContext(), "Error in connection.", Toast.LENGTH_LONG).show();
                    Log.d("Connection", ""+error);
                }
            });
            requestQueue1.add(stringRequest1);
            requestQueue1.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
                @Override
                public void onRequestFinished(Request<StringRequest> request) {
                       List<PlaceInformationModule> finalplace=new ArrayList<>();
                        Log.e("hello",""+placedata.size());
                        for(int s=0;s<placedata.size();s++){
                            PlaceInformationModule pi=new PlaceInformationModule();
                            for(int t=0;t<30;t++){
                                if((placedata.get(s)).equals(allplace[t][0])){
                                    pi.setPid(allplace[t][0]);
                                    pi.setName(allplace[t][1]);
                            pi.setAddress(allplace[t][2]);
                            pi.setDescription(allplace[t][3]);
                            pi.setLng(allplace[t][4]);
                            pi.setLtd(allplace[t][5]);
                            pi.setUrl(allplace[t][6]);
                            finalplace.add(pi);
                                }
                            }
                        }

                        Log.e("subashh",finalplace.get(1).getUrl());
                    rv.setAdapter(new CustomAdapter(getContext(), finalplace));


                }
            });

            //------------------------------------------------------------------
            //------------------------------------------------------------------

        }//thik cha
        else {
            rv.setAdapter(new CustomAdapterException(getContext(), list));
        }


    }


    public void getUser(final String id){
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/getuserinformation.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    JSONObject obj2=a1.getJSONObject(0);
                    interests=obj2.getString("interest");


                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(getContext(), "second call Faliure", Toast.LENGTH_LONG).show();
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
                myMap.put("id",id);
                return myMap;
            }
        };
        requestQueue2.add(stringRequest2);
        requestQueue2.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                getInterestedPlace(id,interests);
            }
        });
    }


    public void getInterestedPlace(final String userrid,final String userinterest){
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/userplace.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    interestedplaces=a1.length();
                    for(int z=0;z<a1.length();z++) {
                        JSONObject obj2 = a1.getJSONObject(z);
                        String upid = obj2.getString("pid");
                        String upname= obj2.getString("name");
                        String updescription = obj2.getString("description");
                        String utype = obj2.getString("type");
                        String uurl = obj2.getString("url");
                        Log.e("raj",upname);
                        interestedplace[z][0]=upid;
                        interestedplace[z][1]=upname;
                        interestedplace[z][2]=updescription;
                        interestedplace[z][3]=utype;
                        interestedplace[z][4]=uurl;
                    }

                }
                catch(Exception e){

                    Log.d("third", ""+e);
                    Toast.makeText(getContext(), "third call Faliure", Toast.LENGTH_LONG).show();
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
                myMap.put("id", userrid);
                myMap.put("interest", userinterest);
                return myMap;
            }
        };
        requestQueue3.add(stringRequest3);
        requestQueue3.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {
               final List<PlaceInformationModule1> finalplace1=new ArrayList<>();
//                Log.e("hello",""+placedata.size());

                    for(int w=0;w<interestedplaces;w++){
                        PlaceInformationModule1 pi1=new PlaceInformationModule1();
                            pi1.setPid(interestedplace[w][0]);
                            pi1.setName(interestedplace[w][1]);
                            pi1.setDescription(interestedplace[w][2]);
                            pi1.setUrl(interestedplace[w][4]);
//                        Log.e("ram123",interestedplace[w][1]);

                            finalplace1.add(pi1);
                    }
                    Log.e("ram123",finalplace1.get(2).getName());


//                Log.e("subashh",finalplace.get(1).getUrl());
                rv1.setAdapter(new CustomAdapter1(getContext(), finalplace1));


            }
        });


    }





}
