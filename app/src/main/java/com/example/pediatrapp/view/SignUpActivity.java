package com.example.pediatrapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.pediatrapp.R;
import com.example.pediatrapp.adapter.OnDataSubmitted;
import com.example.pediatrapp.fragments.ChildRegisterFragment;
import com.example.pediatrapp.fragments.DoctorPhotoFragment;
import com.example.pediatrapp.fragments.DoctorRegisterFragment;
import com.example.pediatrapp.fragments.ParentRegisterFragment;
import com.example.pediatrapp.fragments.RolFragment;
import com.example.pediatrapp.model.Chat;
import com.example.pediatrapp.model.Hijo;
import com.example.pediatrapp.model.Padre;
import com.example.pediatrapp.model.Pediatra;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.UUID;


public class SignUpActivity extends AppCompatActivity implements OnDataSubmitted {

    private Fragment rolFragment,parentRegisterFragment, childRegisterFragment, doctorRegisterFragment, doctorPhotoFragment;
    private LinearLayout layout;
    private String datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        layout = findViewById(R.id.layout);

        rolFragment = new RolFragment();
        ((RolFragment) rolFragment).setListener(this);
        parentRegisterFragment = new ParentRegisterFragment();
        ((ParentRegisterFragment) parentRegisterFragment).setListener(this);
        childRegisterFragment = new ChildRegisterFragment();
        ((ChildRegisterFragment) childRegisterFragment).setListener(this);
        doctorRegisterFragment = new DoctorRegisterFragment();
        ((DoctorRegisterFragment) doctorRegisterFragment).setListener(this);
        doctorPhotoFragment = new DoctorPhotoFragment();
        ((DoctorPhotoFragment) doctorPhotoFragment).setListener(this);


        showFragment(rolFragment);


    }

    public void showFragment(Fragment fragment){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout, fragment);
        transaction.commit();


    }

    public String getDatos(){

        return datos;

    }

    @Override
    public void onData(Fragment fragment, String type, String... args) {

        if(fragment.equals(rolFragment)){

            if(type.equals("padre")){

                datos = "";
                showFragment(parentRegisterFragment);


            } else if(type.equals("pediatra")){

                datos = "";
                showFragment(doctorRegisterFragment);

            } else if(type.equals("back")){

                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, 0);

            }

        } else if(fragment.equals(parentRegisterFragment)){

            if(type.equals("next")){

                showFragment(childRegisterFragment);
                for(int j=0; j<args.length; j++){

                    datos += args[j] + ",";

                }


            } else if(type.equals("back")){

                datos = null;
                showFragment(rolFragment);

            }


        } else if(fragment.equals(childRegisterFragment)){

            if(type.equals("next")){

                //Se hace launch a la nueva actividad donde esté el chat
                for(int j=0; j<args.length; j++){

                    datos += args[j] + ",";

                }

                createUserParent();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } else if(type.equals("back")){


                showFragment(parentRegisterFragment);

            }


        } else if(fragment.equals(doctorRegisterFragment)){

            if(type.equals("next")){

                showFragment(doctorPhotoFragment);
                for(int j=0; j<args.length; j++){

                    datos += args[j] + ",";

                }

            } else if(type.equals("back")){

                datos = null;
                showFragment(rolFragment);

            }


        } else if(fragment.equals(doctorPhotoFragment)){


            if(type.equals("next")){

                for(int j=0; j<args.length; j++){

                    datos += args[j] + ",";

                }

                createUserDoctor();
                Intent intent = new Intent(this, ActivityMainPediatra.class);
                startActivity(intent);

            }else if(type.equals("back")){

                showFragment(doctorRegisterFragment);

            }

        }

    }

    public void createUserParent(){

            String[] str = datos.split(",");
            String nombre = str[0];
            String cedula = str[1];
            String email = str[2];
            String password = str[3];
            String direccion = str[4];
            String cel = str[5];
            String nombreH = str[6];
            String identH = str[7];
            String fechaH = str[8];
            String generoH = str[9];
            String idDoc = str[10];


            //Registro en firebase
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                FirebaseUser user = auth.getCurrentUser();
                String id = user.getUid();

                String idH = FirebaseDatabase.getInstance().getReference().child("Padres").child(id).child("Hijos").push().getKey();
                Hijo hijo = new Hijo(idH, identH, fechaH, generoH, nombreH);

                HashMap<String, Hijo> hijos = new HashMap<>();
                hijos.put(idH, hijo);

                HashMap<String,String> pediatrasAsig = new HashMap<>();
                pediatrasAsig.put(idDoc, idDoc);

                Uri uriP = Uri.parse("android.resource://" + this.getPackageName()
                        + "/" + R.drawable.user);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                storage.getReference().child("Padre").child(id).putFile(uriP);
                String foto= id;

                //Padre padre = new Padre(id,cedula,nombre,email,password,direccion,cel,foto,pediatrasAsig, hijos);

                //FirebaseDatabase.getInstance().getReference().child("Padres").child(id).setValue(padre);
                //FirebaseDatabase.getInstance().getReference().child("Pediatras").child(idDoc).child("Padres_asignados").child(id).setValue(id);



                Query query = FirebaseDatabase.getInstance().getReference().child("Pediatras");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot child: dataSnapshot.getChildren()){

                            Pediatra ped = child.getValue(Pediatra.class);

                            if(ped.getId().equals(idDoc)){

                                String idc = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                                Chat chat = new Chat(foto, ped.getFoto(), nombre, ped.getNombre(), id, idDoc, idc);
                                HashMap<String, String> hashChat = new HashMap<>();
                                hashChat.put(idc,idc);
                                Padre padre = new Padre(id,cedula,nombre,email,password,direccion,cel,foto,pediatrasAsig, hijos, hashChat);
                                FirebaseDatabase.getInstance().getReference().child("Padres").child(id).setValue(padre);
                                FirebaseDatabase.getInstance().getReference().child("Pediatras").child(idDoc).child("Padres_asignados").child(id).setValue(id);
                                FirebaseDatabase.getInstance().getReference().child("Pediatras").child(idDoc).child("chats").child(idc).setValue(idc);
                                FirebaseDatabase.getInstance().getReference().child("chat").child(idc).setValue(chat);

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });


    }

    public void createUserDoctor(){

            String[] str = datos.split(",");
            String nombre = str[0];
            String cedula = str[1];
            String email = str[2];
            String password = str[3];
            String idV = str[4];

            //Registrar en firebase
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                FirebaseUser user = auth.getCurrentUser();
                String id = user.getUid();

                String foto = str[5];
                String firma = str[6];

                Uri uriP = Uri.parse(foto);
                Uri uriF = Uri.parse(firma);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                storage.getReference().child("Doctor").child(id+"*"+"Foto").putFile(uriP);
                storage.getReference().child("Doctor").child(id+"*"+"Firma").putFile(uriF);
                foto= id+"*"+"Foto";
                firma= id+"*"+"Firma";

                Pediatra pediatra = new Pediatra(id,nombre,cedula,email,password,idV,firma,foto);

                //Escribir en la base de datos

                FirebaseDatabase.getInstance().getReference().child("Pediatras").child(id).setValue(pediatra);


            }).addOnFailureListener(e -> {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            });

    }


}
