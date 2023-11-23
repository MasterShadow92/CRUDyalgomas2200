package com.androidwithshiv.crudyalgomas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.media.MediaPlayer;
import com.androidwithshiv.crudoperation.R;
import com.androidwithshiv.crudyalgomas.database.RoomDB;
import com.androidwithshiv.crudyalgomas.models.Person;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import android.content.Intent;

public class MainActivity extends AppCompatActivity  {

    private MediaPlayer buttonClickSound;
EditText txtLatitud , txtLongitud;
GoogleMap mMap;

    private Context context;
    private EditText personNameEt, personAgeEt, personIdEt;
    private Button addBt, updateBt, deleteBt, showBt;
    private List<Person> personList;
    RoomDB database;
    private void init(){
        context = MainActivity.this;
        personNameEt = findViewById(R.id.name);
        personAgeEt = findViewById(R.id.age);
        personIdEt = findViewById(R.id.person_id);

        addBt = findViewById(R.id.add);
        updateBt = findViewById(R.id.update);
        deleteBt = findViewById(R.id.delete);
        showBt = findViewById(R.id.show);

        database = RoomDB.getInstance(context);
        personList = database.mainDAO().getAll();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){

// Inicializar el MediaPlayer con el sonido del clic del botón
        buttonClickSound = MediaPlayer.create(this, R.raw.button_click_sound);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button btnAbrirSegundaPantalla = findViewById(R.id.btnAbrirSegundaPantalla);


        btnAbrirSegundaPantalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para iniciar la segunda actividad
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                buttonClickSound.start();
                // Iniciar la segunda actividad
                startActivity(intent);
            }
        });

        init();
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.white));

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personNameEt.getText().length()==0 || personAgeEt.getText().length()==0){
                    showToast("Agregue Nombre and edad...");
                }
                else{
                    Person newPerson = new Person();
                    newPerson.setName(personNameEt.getText().toString());
                    newPerson.setAge(Integer.parseInt(personAgeEt.getText().toString()));
                    database.mainDAO().insert(newPerson);
                    showToast("Agregado exitosamente...");
                    listUpdate();
                }
            }
        });

        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personIdEt.getText().length()==0){
                    showToast("Ingrese Id...");
                }
                else {
                    int deletedId = Integer.parseInt( personIdEt.getText().toString());
                    Person deletedPerson = null;
                    for (Person p : personList){
                        if(p.getId()==deletedId){
                            deletedPerson = p;
                            break;
                        }
                    }
                    if(deletedPerson==null){
                        showToast("No existe...");
                    }
                    else{
                        database.mainDAO().delete(deletedPerson);
                        showToast("Eliminado con Exito...");
                        listUpdate();
                    }
                }
            }
        });

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personNameEt.getText().length()==0 || personAgeEt.getText().length()==0 || personIdEt.getText().length()==0){
                    showToast("agregue nombre ,edad e id...");
                }
                else{
                    int updateId = Integer.parseInt( personIdEt.getText().toString());
                    boolean isThere = false;
                    for (Person p : personList){
                        if(p.getId()==updateId){
                            isThere = true;
                            break;
                        }
                    }
                    if(isThere){
                        String updateName = personNameEt.getText().toString();
                        String updateAge = personAgeEt.getText().toString();
                        database.mainDAO().update(updateId, updateName, Integer.parseInt(updateAge));
                        showToast("Atualizado con Exito....");
                        listUpdate();
                    }else {
                        showToast("Id no entró...");
                    }
                }
            }
        });

        showBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                for(Person person : personList){
                    stringBuilder.append(person.getName()+"  "+person.getAge()+"  "+person.getId() );
                    stringBuilder.append("\n");
                }
                String allData = stringBuilder.toString();
                showToast(allData);
            }
        });
    }
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del MediaPlayer al destruir la actividad
        if (buttonClickSound != null) {
            buttonClickSound.release();
        }
    }
    private void listUpdate(){
        personList.clear();
        personList.addAll(database.mainDAO().getAll());
    }
    private void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }




}

