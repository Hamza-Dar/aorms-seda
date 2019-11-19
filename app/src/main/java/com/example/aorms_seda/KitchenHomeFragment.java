package com.example.aorms_seda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class KitchenHomeFragment extends Fragment {

    Button placeOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.kitchen_home_fragment,container,false);

        placeOrder=root.findViewById(R.id.placeOrderbtn);
        return root;

    }

}
