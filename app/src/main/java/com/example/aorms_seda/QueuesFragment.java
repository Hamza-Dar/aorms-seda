package com.example.aorms_seda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QueuesFragment extends Fragment {

    RecyclerView parentRV;
    ParentAdapter parentAdapter;
    ArrayList<VerticalModel> arrayList;
    ArrayList<Dish> dishes;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.queues,container,false);

        parentRV=root.findViewById(R.id.MainRV);



        setData();
        return root;
    }

    public void setData()
    {
        dishes=new ArrayList<Dish>();
        dishes.add(new Dish('1',"Pasta","12:00","12","Waiting","Italian"));
        dishes.add(new Dish('1',"Pizza","12:00","12","Waiting","Italian "));
        arrayList=new ArrayList<>();

        arrayList.add(new VerticalModel("Cook1",dishes));
        arrayList.add(new VerticalModel("Cook2",dishes));

        parentRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));

        parentAdapter=new ParentAdapter(getActivity(),arrayList);
        parentRV.setAdapter((parentAdapter));

    }
}
