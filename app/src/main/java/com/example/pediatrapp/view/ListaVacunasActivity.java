package com.example.pediatrapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pediatrapp.R;
import com.example.pediatrapp.adapter.ListaVacunasAdpater;
import com.example.pediatrapp.model.Vacuna;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaVacunasActivity extends AppCompatActivity implements Serializable {

    private Button agregarVaunaBTN,  backBTN;
    private TextView nombreHijo;
    private RecyclerView recyclerView;
    private ArrayList<Vacuna> listaVacunas;
    private ListaVacunasAdpater adapter;

    public static final int request_code = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacunas);

       agregarVaunaBTN = findViewById(R.id.agregarVacunaLista);
       nombreHijo = findViewById(R.id.nombreHijoVaList);
       recyclerView = findViewById(R.id.listaVacunasAgregadas);
       recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
       backBTN = findViewById(R.id.backVacunasLista);
       listaVacunas = new ArrayList<>() ;
       //Recibe nombre del Hijo
        nombreHijo.setText(getIntent().getStringExtra("elnombre"));

         listaVacunas.add(new Vacuna("res","12","hoy ","Jair","Ejmeplo","hoy"));

        // Con este método se cargan todas las vacunas de la base de datos
        cargarVacunas();

        //Actualiza la lista de vacunas cuando se agraga una nueva
        actualizarVacunas();

       agregarVaunaBTN.setOnClickListener(

               (v)->{

                  // AddVacunaActivity añadir = new AddVacunaActivity(this);

                   Intent i= new Intent(this, AddVacunaActivity.class);
                   startActivityForResult(i, 11);

               }

       );
        backBTN.setOnClickListener(
                (v)->{
                    this.finish();
                }

        );



    }

    //Aquí se recibe la nueva vacuna que va a ser agragada a la lista
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11 && resultCode == RESULT_OK) {

            if (data != null) {

                agregarVacunaALista(data);
            }
        }
    }

    // Método para Cargar Vacunas de base de datos

    public void cargarVacunas(){




    }

    public void actualizarVacunas(){

       /// listaVacunas.add(new Vacuna("res","12","Asmet","Jair","AlgoSerio",null));

        if(listaVacunas.size() != 0) {

            adapter = new ListaVacunasAdpater(listaVacunas, this);
            recyclerView.setAdapter(adapter);
        }


    }

    //Este método agraga una nueva vacuna a la lista.

    public void agregarVacunaALista(Intent data){

        //Vacuna vacuna =  (Vacuna) data.getExtras().getSerializable("nuevaVacuna");

        Vacuna vacuna  =   (Vacuna) data.getExtras().getSerializable("marcador");

        if(vacuna != null) {
            listaVacunas.add(vacuna);
           ListaVacunasAdpater  adapter2 = new ListaVacunasAdpater( listaVacunas,this);
           setAdapter(adapter2);
            actualizarVacunas();
            Toast.makeText(this,"Se añadióZZZZZZ: "+ vacuna.getNombre_vacuna(), Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this," Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void setListaVacunas(ArrayList<Vacuna> listaVacunas) {
        this.listaVacunas = listaVacunas;
    }

    public void setAdapter(ListaVacunasAdpater adapter) {
        this.adapter = adapter;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public Button getAgregarVaunaBTN() {
        return agregarVaunaBTN;
    }

    public void setAgregarVaunaBTN(Button agregarVaunaBTN) {
        this.agregarVaunaBTN = agregarVaunaBTN;
    }
    public TextView getNombreHijo() {
        return nombreHijo;
    }

    public void setNombreHijo(TextView nombreHijo) {
        this.nombreHijo = nombreHijo;
    }
}