package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{

    private ArrayList<Table> data;

    public TableAdapter(ArrayList<Table> data){
        this.data = data;
    }

    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_list_layout, parent, false);
        return new TableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TableAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.id.setText(data.get(position).id);
        viewHolder.no.setText("" + data.get(position).no);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView id, no;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.table_id);
            no = (TextView) itemView.findViewById(R.id.table_no);
        }
    }
}