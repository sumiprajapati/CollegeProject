package com.example.de.tripshayatri;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder>{
    Context c;
    List<PlaceInformationModule> mydata=new ArrayList<>();
    public CustomAdapter(Context context, List<PlaceInformationModule> list) {
        c=context;
        mydata=list;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(c).inflate(R.layout.myview,parent,false);

        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
       final String Pid=mydata.get(position).getPid();
        final String name=mydata.get(position).getName();
        final String address=mydata.get(position).getAddress();
        final String description=mydata.get(position).getDescription();
        String lng=mydata.get(position).getLng();
        String ltd=mydata.get(position).getLtd();
        final String url=mydata.get(position).getUrl();

        Glide
                .with(c)
                .load(url)
                .apply(new RequestOptions().override(800, 500))
                .into(holder.tv);
        holder.tv1.setText(name);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(c,PlaceDetails.class);
                Bundle b=new Bundle();
                b.putString("pid",Pid);
                b.putString("name",name);
                b.putString("address",address);
                b.putString("description",description);
                b.putString("url",url);
                i.putExtras(b);
                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView tv;
        TextView tv1;
        LinearLayout parentLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.image);
            tv1=itemView.findViewById(R.id.placename);
            parentLayout=itemView.findViewById(R.id.mylayouts);
        }
    }
}
