package com.example.de.tripshayatri;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter1 extends RecyclerView.Adapter <CustomAdapter1.MyViewHolder>{
    Context c;
    List<PlaceInformationModule1> mydata=new ArrayList<>();
    public CustomAdapter1(Context context, List<PlaceInformationModule1> list) {
        c=context;
        mydata=list;
    }

    @NonNull
    @Override
    public CustomAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(c).inflate(R.layout.myview1,parent,false);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(c,Naya.class);
                c.startActivity(i);
            }
        });
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter1.MyViewHolder holder, int position) {
        String Pid=mydata.get(position).getPid();
        String name=mydata.get(position).getName();
//        String address=mydata.get(position).getAddress();
        String description=mydata.get(position).getDescription();
//        String lng=mydata.get(position).getLng();
//        String ltd=mydata.get(position).getLtd();
        String url=mydata.get(position).getUrl();

        Glide
                .with(c)
                .load(url)
                .apply(new RequestOptions().override(800, 500))
                .into(holder.tv);
        holder.tv1.setText(name);

    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView tv;
        TextView tv1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.image);
            tv1=itemView.findViewById(R.id.placename);
        }
    }
}
