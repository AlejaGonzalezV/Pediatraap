package com.example.pediatrapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pediatrapp.R;
import com.example.pediatrapp.adapter.HijosVacunasAdapter;
import com.example.pediatrapp.model.Hijo;

import java.util.ArrayList;
import java.util.List;

public class VacunasActivity extends AppCompatActivity {

    private ImageButton buscarBtn;
    private EditText buscarET;
    private String nombreHijo;
    private RecyclerView recycler;
    private HijosVacunasAdapter adapter;
    private ArrayList<Hijo> listaHijos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

        buscarBtn = findViewById(R.id.searchHijoVacunaBT);
         buscarET = findViewById(R.id.searchHijoVacunaET);
         nombreHijo = "";
        recycler = findViewById(R.id.recyclerListaHijosVacuna);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listaHijos = new ArrayList<>();

        //Hijo de prueba
        listaHijos.add(new Hijo("", "", "String nacimiento", "Masculino", "Melqui"));

        adapter = new HijosVacunasAdapter(this,listaHijos );

         buscarBtn.setOnClickListener(
                 (v)->{

                     Toast.makeText(this, "Buscar Hijo en Vacunas", Toast.LENGTH_LONG).show();
                     if(buscarET.getText() != null) {
                         nombreHijo = buscarET.getText().toString();
                     }else{

                         Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_LONG).show();

                     }

                 }

         );



    }

    //Método para buscar Hijo en la lsita de hijos Agregados

    public void buscarHijoVacunas(String nombreHijo){


    }


    public ImageButton getBuscarBtn() {
        return buscarBtn;
    }

    public void setBuscarBtn(ImageButton buscarBtn) {
        this.buscarBtn = buscarBtn;
    }

    public EditText getBuscarET() {
        return buscarET;
    }

    public void setBuscarET(EditText buscarET) {
        this.buscarET = buscarET;
    }

    public String getNombreHijo() {
        return nombreHijo;
    }

    public void setNombreHijo(String nombreHijo) {
        this.nombreHijo = nombreHijo;
    }
}
