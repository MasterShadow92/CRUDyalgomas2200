
package com.androidwithshiv.crudyalgomas;

import android.content.DialogInterface;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.provider.MediaStore;
import java.util.Random;
import android.widget.ImageView;
import android.content.Intent;  // Importación necesaria
import android.net.Uri;  // Importación necesaria
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.androidwithshiv.crudoperation.R;


public class SecondActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 1;
    private int numeroAdivinado;
    private EditText numeroEditText;
    private Button adivinarButton;
    private TextView resultTextView;
    private MediaPlayer mediaPlayer;
    private ImageView felicitacionesImageView; // Declarar la ImageView
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
// Inicializar el MediaPlayer con el sonido de salir
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_salir);
        Button salirButton = findViewById(R.id.salirButton);
        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();

                showExitDialog();

                // Cancelar el Handler si ya está en ejecución
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }



                // Utilizar un Handler para esperar 3 segundos antes de cerrar la aplicación
                handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Lógica para salir de la aplicación después de 3 segundos
                        finishAffinity();
                    }
                }, 10000); // 3000 milisegundos (3 segundos)
            }
        });



        numeroAdivinado = generarNumeroAleatorio();


        numeroEditText = findViewById(R.id.numeroEditText);
        adivinarButton = findViewById(R.id.adivinarButton);
        resultTextView = findViewById(R.id.resultTextView);

        adivinarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtén la suposición del usuario
                String suposicionStr = numeroEditText.getText().toString();

                if (!suposicionStr.isEmpty()) {
                    int suposicion = Integer.parseInt(suposicionStr);

                    // Compara la suposición con el número adivinado
                    if (suposicion == numeroAdivinado) {
                        resultTextView.setText("¡Correcto! Has adivinado el número.");
                        ImageView felicitacionesImageView = findViewById(R.id.felicitacionesImageView);
                        felicitacionesImageView.setVisibility(View.VISIBLE);
                    } else if (suposicion < numeroAdivinado) {
                        resultTextView.setText("Intenta con un número más grande.");
                    } else {
                        resultTextView.setText("Intenta con un número más pequeño.");
                    }
                }


            }

        });





        Button abrirOtraPantallaButton = findViewById(R.id.abrirOtraPantallaButton);
        abrirOtraPantallaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para iniciar la nueva actividad
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });


        // Configurar el botón Virginio Gómez
        Button virginioGomezButton = findViewById(R.id.virginioGomezButton);

        virginioGomezButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define la URL que deseas abrir
                String url = "https://www.virginiogomez.cl";

                // Crea un Intent para abrir el navegador web
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Inicia el navegador web
                startActivity(intent);
            }
        });


        Button googleMapsButton = findViewById(R.id.googleMapsButton);

        googleMapsButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                // Define la dirección que deseas mostrar en Google Maps
                String direccion = "Virginio Gómez, Chillán";

                // Reemplaza los espacios en blanco con signos de adición (+) para formatear la URL correctamente
                direccion = direccion.replace(" ", "+");

                // Crea una URL para abrir Google Maps con la dirección especificada
                String url = "https://www.google.com/maps/search/" + direccion;

                // Crea un Intent para abrir Google Maps
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Inicia Google Maps
                startActivity(intent);
            }
        });

        Button openGalleryButton = findViewById(R.id.openGalleryButton);

        openGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la aplicación de la galería
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                // Inicia la actividad de la galería
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });



    }

    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hasta luego")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Lógica para salir de la aplicación
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancelar el cuadro de diálogo y detener el sonido
                        mediaPlayer.stop();
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false) // Evita que se cierre al tocar fuera del cuadro de diálogo
                .show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // El usuario ha seleccionado una imagen
            Uri selectedImageUri = data.getData();

            // Aquí puedes realizar acciones adicionales, como mostrar la imagen o procesarla de alguna manera
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
    private int generarNumeroAleatorio() {
        // Genera un número aleatorio entre 1 y 100
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}
