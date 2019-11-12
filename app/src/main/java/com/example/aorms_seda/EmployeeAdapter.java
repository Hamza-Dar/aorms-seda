package com.example.aorms_seda;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    Context context;
    View v;

    private ArrayList<Employee> data;

    public EmployeeAdapter(ArrayList<Employee> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employee_list_layout, parent, false);
        v = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(data.get(position).name);
        viewHolder.id.setText(data.get(position).id);
        viewHolder.type.setText(data.get(position).emp_type);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EmployeeDetails.class);
                i.putExtra("Employee", data.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, id, type;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.emp_name);
            id = (TextView)itemView.findViewById(R.id.emp_id);
            type = (TextView) itemView.findViewById(R.id.emp_type);
        }
    }
}

