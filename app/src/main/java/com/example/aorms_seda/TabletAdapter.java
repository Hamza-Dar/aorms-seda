package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TabletAdapter extends RecyclerView.Adapter<TabletAdapter.ViewHolder>{

    private ArrayList<Tablet> data;

    public TabletAdapter(ArrayList<Tablet> data){
            this.data = data;
    }

    @Override
    public TabletAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.tablet_list_layout, parent, false);
            return new TabletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TabletAdapter.ViewHolder viewHolder, final int position) {

            viewHolder.id.setText(data.get(position).id);
            viewHolder.model.setText(data.get(position).model);
            //viewHolder.purchaseDate.setText("" + data.get(position).purchaseDate);
            viewHolder.purchaseDate.setText("23 November 2019");
            viewHolder.regNo.setText("" + data.get(position).regNo);
    }

    @Override
    public int getItemCount() {
            return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView id, model, purchaseDate, regNo;

        public ViewHolder(View itemView) {
            super(itemView);
            model = (TextView)itemView.findViewById(R.id.tablet_model);
            id = (TextView)itemView.findViewById(R.id.tablet_id);
            purchaseDate = (TextView) itemView.findViewById(R.id.tablet_purchaseDate);
            regNo = (TextView) itemView.findViewById(R.id.tablet_regNo);
        }
    }
}
