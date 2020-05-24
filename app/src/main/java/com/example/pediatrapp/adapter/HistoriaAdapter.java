package com.example.pediatrapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pediatrapp.R;
import com.example.pediatrapp.fragments.HistoriaPediatraFragment;
import com.example.pediatrapp.model.Diagnostico;
import com.example.pediatrapp.view.HistoriaClinicaActivity;

import java.util.ArrayList;

public class HistoriaAdapter extends BaseAdapter {

    private ArrayList<Diagnostico> diagnosticos;
    private HistoriaPediatraFragment historiaFrag;

    public HistoriaAdapter() {
        diagnosticos = new ArrayList<>();

    }

    @Override
    public int getCount() {
        return diagnosticos.size();
    }

    @Override
    public Object getItem(int position) {
        return diagnosticos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.historia_row, null, false);
        TextView titulo = row.findViewById(R.id.titulo);
        TextView fecha = row.findViewById(R.id.fecha);
        titulo.setText(diagnosticos.get(position).getTitulo());
        fecha.setText(diagnosticos.get(position).getFecha());



        return row;
    }

    public void addDiagnostico(Diagnostico diag){

        diagnosticos.add(diag);
        notifyDataSetChanged();

    }
}
