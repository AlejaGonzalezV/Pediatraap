package com.example.pediatrapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pediatrapp.R;

public class PediatraFragment_ChatList extends Fragment {

    private EditText SearchChatET;
    private ImageButton SearchChatBT;
    private Switch switchDisp;
    private ListView pediatra_ChatList;

    public PediatraFragment_ChatList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pediatrafragment_chatlist, container, false);

        SearchChatET = view.findViewById(R.id.SearchChatET);
        SearchChatBT = view.findViewById(R.id.SearchChatBT);
        switchDisp = view.findViewById(R.id.switchDisp);
        pediatra_ChatList = view.findViewById(R.id.pediatra_ChatList);

        SearchChatBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(">>>", "Buscar");
            }
        });

        return view;
    }
}