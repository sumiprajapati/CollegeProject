package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    RecyclerView rv,rv1,rv2,rv3,rv4,rv5,rv6;
    String[] list = {};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,null);
        rv = v.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new CustomAdapter(getContext(), list));



        rv1 = v.findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv1.setLayoutManager(layoutManager1);
        rv1.setAdapter(new CustomAdapter1(getContext(), list));

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
}
