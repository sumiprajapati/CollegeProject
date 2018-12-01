package com.example.de.tripshayatri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class VisitedApapter extends BaseAdapter{
    ImageView iv;
    TextView name,address,rating;
    List<VisitedModule> mydata =new ArrayList<>();
    Context c;
    public VisitedApapter(Context context, List<VisitedModule> mydata) {
        c=context;
        this.mydata=mydata;
    }

    @Override
    public int getCount() {
        return this.mydata.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(c).inflate(R.layout.visited_fragment,null);
        name=view.findViewById(R.id.placename);
        address=view.findViewById(R.id.placeaddress);
        rating=view.findViewById(R.id.placerating);
        iv=view.findViewById(R.id.image);
        String rename=mydata.get(i).getName();
        String readdress=mydata.get(i).getAddress();
        String rerating=mydata.get(i).getRating();
        String reurl=mydata.get(i).getUrl();

        address.setText(readdress);name.setText(rename);
        rating.setText(rerating);
        Glide
                .with(c)
                .load(reurl)
                .apply(new RequestOptions().override(800, 500))
                .into(iv);
        return view;
    }
}
