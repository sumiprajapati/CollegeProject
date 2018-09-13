package com.example.de.tripshayatri;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomAdapter3 extends RecyclerView.Adapter <CustomAdapter3.MyViewHolder>{
    Context c;
    String[] mydata;
    public CustomAdapter3(Context context, String[] list) {
        c=context;
        mydata=list;
    }

    @NonNull
    @Override
    public CustomAdapter3.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(c).inflate(R.layout.myview3,parent,false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter3.MyViewHolder holder, int position) {
        holder.tv.setText(mydata[position]);

    }

    @Override
    public int getItemCount() {
        return mydata.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.name3);
        }
    }
}
