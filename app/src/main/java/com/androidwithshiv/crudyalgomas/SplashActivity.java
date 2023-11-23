package com.androidwithshiv.crudyalgomas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.media.MediaPlayer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidwithshiv.crudoperation.R;

public class SplashActivity extends AppCompatActivity {

    // Duración de la pantalla de carga en milisegundos
    private static final int SPLASH_SOUND_DURATION = 7200;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Inicializar el MediaPlayer con el sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.splash_sound);

        // Reproducir el sonido
        mediaPlayer.start();

        // Usar un controlador para esperar el tiempo especificado y luego abrir la actividad principal
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Detener el sonido
                mediaPlayer.stop();
                mediaPlayer.release();

                // Crear un Intent para abrir la actividad principal
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // Cerrar la actividad de la pantalla de carga para evitar que el usuario la vea nuevamente al retroceder
                finish();
            }
        }, SPLASH_SOUND_DURATION);
        mostrarNota();
    }

    private void mostrarNota() {
        // Crear un LayoutInflater para inflar el diseño personalizado del Toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_layout_root));

        // Configurar el mensaje en el diseño personalizado
        TextView text = layout.findViewById(R.id.text);
        text.setText("Nueva actualización - " +
                "Se agregó fondo de pantalla en las dos pantallas. " +
                "Botón para abrir mapa (Chillán). " +
                "Sonido de inicio. " +
                "Sonido de salida (desde la segunda pantalla). " +
                "Botón para abrir galería de fotos. " +
                "Botón de salir con sonido de fondo.");

        // Ajustar el tamaño de la letra
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        // Crear y mostrar el Toast personalizado
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG); // Cambiado a LENGTH_LONG
        toast.setView(layout);
        toast.show();

        // Usar un Handler para programar la duración del Toast
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cancelar el Toast después de 15 segundos
                toast.cancel();
            }
        }, 15000);  // 15000 milisegundos = 15 segundos
    }
}