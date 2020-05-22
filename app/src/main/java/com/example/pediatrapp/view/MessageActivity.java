package com.example.pediatrapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pediatrapp.R;
import com.example.pediatrapp.model.Padre;
import com.example.pediatrapp.model.Pediatra;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView profile_image;
    private TextView username;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private EditText text_send;
    private ImageButton btn_send;
    private ImageButton btn_media;
    private String type;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        type = intent.getStringExtra("type");

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //CREO QUE AQUI ES DONDE SE PUEDE DECIR QUE ABRA LA VISTA DEL PERFIL DEL USUARIO CON EL QUE HABLA
            }
        });

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);
        btn_media = findViewById(R.id.btn_media);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = text_send.getText().toString();
                if(!body.equals("")){
                    sendMessage(body, null, fuser.getUid());
                }
                text_send.setText("");
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if(type.equals("padre")){
            reference = FirebaseDatabase.getInstance().getReference().child("Padres").child(userid);
        }else {
            reference = FirebaseDatabase.getInstance().getReference().child("Pediatras").child(userid);
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(type.equals("padre")){
                    Padre padre = dataSnapshot.getValue(Padre.class);
                    username.setText(padre.getNombre());

                    storage.getReference().child("Padre").child(padre.getFoto()).getDownloadUrl().addOnSuccessListener(
                            uri -> {
                                Glide.with(MessageActivity.this).load(uri).centerCrop().into(profile_image);
                            }
                    );
                }else{
                    Pediatra pediatra = dataSnapshot.getValue(Pediatra.class);
                    username.setText(pediatra.getNombre());

                    storage.getReference().child("Padre").child(pediatra.getFoto()).getDownloadUrl().addOnSuccessListener(
                            uri -> {
                                Glide.with(MessageActivity.this).load(uri).centerCrop().into(profile_image);
                            }
                    );

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void sendMessage(String body, String roomChat, String idUser){
        //MIN 6:38
        //Generar ID
        //MIRAR SESION QUE NO SE CIERRA



    }
}
