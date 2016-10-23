package com.example.bobby.cathy;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bobby on 22/10/2016.
 */


    public class servicesadapter extends  RecyclerView.Adapter<servicesadapter.MyViewHolder>{
        private List<services> servicesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView a, b, c;

            public MyViewHolder(View view) {
                super(view);
                a = (TextView) view.findViewById(R.id.a);
                b = (TextView) view.findViewById(R.id.b);
                c = (TextView) view.findViewById(R.id.c);
            }
        }


        public servicesadapter(List<services> servicesList) {
            this.servicesList = servicesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.serviceslist, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            services services = servicesList.get(position);
            holder.a.setText(services.geta());
            holder.b.setText(services.getb());
            if (services.getb().contains("Yes")){
                holder.b.setTextColor(Color.RED);
            }
            holder.c.setText(services.getc());
        }

        @Override
        public int getItemCount() {
            return servicesList.size();
        }
    }


