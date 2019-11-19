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
    QueueParentAdapter parentAdapter;
    ArrayList<VerticalModel> arrayList;
    ArrayList<Dish> dishes;
    ArrayList<Cook> chefList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.kitchen_queues,container,false);
        parentRV=root.findViewById(R.id.MainRV);
        dishes=new ArrayList<Dish>();
        chefList=new ArrayList<>();
        dishes= (ArrayList<Dish>) getArguments().getSerializable("dishes");
        chefList= (ArrayList<Cook>) getArguments().getSerializable("chefList");
        setData();
        return root;
    }

    public void setData()
    {

        arrayList=new ArrayList<>();
        //get chef list and dishes list;
        for(int i=0;i<chefList.size();i++)
        {
            ArrayList<Dish>chefDishes=new ArrayList<>();
            for(int j=0;j<dishes.size();j++)
            {
                if(dishes.get(j).cookId==chefList.get(i).cookId)
                {
                    chefDishes.add((dishes.get(j)));
                }
            }
            arrayList.add(new VerticalModel(String.valueOf(chefList.get(i).cookId),chefDishes));
        }
        parentRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        parentAdapter=new QueueParentAdapter(getActivity(),arrayList);
        parentRV.setAdapter((parentAdapter));

    }
}
