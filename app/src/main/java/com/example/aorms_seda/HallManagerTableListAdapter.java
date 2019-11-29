package com.example.aorms_seda;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
public class HallManagerTableListAdapter extends FirestoreRecyclerAdapter<hall_manager_tablet_model,HallManagerTableListAdapter.TabletListViewHolder>{
    private OnItemClickListener mListener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HallManagerTableListAdapter(@NonNull FirestoreRecyclerOptions<hall_manager_tablet_model> options) {
        super(options);
    }


    @NonNull
    @Override
    public TabletListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_show_tablets,
                parent,false);
        return new TabletListViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull TabletListViewHolder holder, int position, @NonNull hall_manager_tablet_model model) {
        holder.tabId.setText("Tab ID : " + model.getReg());
        holder.tabStatus.setText("Tab Model: "+ model.getModel());
        holder.tableAssigned.setText("Table assigned: "+model.getTable());

    }

    class TabletListViewHolder extends RecyclerView.ViewHolder{
         TextView tabId;
        public TextView tabStatus;
        public TextView tableAssigned;
        public Button assignTable;


        public TabletListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tabId = itemView.findViewById(R.id.tab_id);
            this.tableAssigned=itemView.findViewById(R.id.tab_table_assigned);
            this.tabStatus = itemView.findViewById(R.id.tab_status);
            assignTable = itemView.findViewById(R.id.assign_button);
            assignTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && mListener != null) {
                            mListener.onassignClick(getSnapshots().getSnapshot(position),position);
                        }
                    }


                }
            });

        }
    }

    public interface OnItemClickListener {
        void onassignClick(DocumentSnapshot documentSnapshot,int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
