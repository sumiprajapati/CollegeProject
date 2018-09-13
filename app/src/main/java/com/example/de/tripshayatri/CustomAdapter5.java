package com.example.de.tripshayatri;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomAdapter5 extends RecyclerView.Adapter <CustomAdapter5.MyViewHolder>{
    Context c;
    String[] mydata;
    public CustomAdapter5(Context context, String[] list) {
        c=context;
        mydata=list;
    }

    @NonNull
    @Override
    public CustomAdapter5.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(c).inflate(R.layout.myview5,parent,false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter5.MyViewHolder holder, int position) {
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
            tv=itemView.findViewById(R.id.name5);
        }
    }
}
