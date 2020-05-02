package com.example.pediatrapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.pediatrapp.R;
import com.example.pediatrapp.adapter.OnDataSubmitted;

public class ParentRegisterFragment extends Fragment {

    private View view;
    private EditText name, id, email, password, address, phone;
    private Button next, back;
    private OnDataSubmitted listener;

    public void setListener(OnDataSubmitted listener){

        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_parent_register,container,false);

        name = view.findViewById(R.id.name);
        id = view.findViewById(R.id.id);
        email = view.findViewById(R.id.date);
        password = view.findViewById(R.id.password);
        address = view.findViewById(R.id.idDoctor);
        phone = view.findViewById(R.id.phone);
        next = view.findViewById(R.id.next);
        back = view.findViewById(R.id.back);

        next.setOnClickListener(

                (v) -> {

                    if(listener != null){


                        listener.onData(this, "next", name.getText().toString(), id.getText().toString(), email.getText().toString(), password.getText().toString(), address.getText().toString(), phone.getText().toString());

                    }


                }

        );

        back.setOnClickListener(

                (v) -> {

                    if(listener != null){

                        listener.onData(this, "back", null);

                    }


                }

        );

        return view;
    }

}