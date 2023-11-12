package hn.uth.runner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperacion extends AppCompatActivity{

    private static final String TAG = "Recuperacion";

    private FirebaseAuth mAuth;
    EditText Correo;
    Button Reestablecer;
    Button Inicio;
    private ProgressDialog mDialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);

        Reestablecer = (Button) findViewById(R.id.btnRestab);
        Correo = (EditText) findViewById(R.id.EtCorreo);
        Inicio = (Button) findViewById(R.id.Inicio);

        mDialog = new ProgressDialog(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        Intent intento = new Intent(this, MainActivity.class);


        Reestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = Correo.getText().toString();

                if (!Email.isEmpty()) {
                    mDialog.setMessage("Espere Un Momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();

                } else{

                    Toast.makeText(getApplicationContext(), "El Campo de Correo Esta Vacio", Toast.LENGTH_LONG).show();

                }
            }
        });



        Inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intento);
            }
        });

    }

    private void resetPassword() {
       // mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Recuperacion.this, "Correo Enviado Para Reestablecer Contraseña", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(Recuperacion.this, "No Se Pudo Enviar El Correo De Reestablecer Contraseña", Toast.LENGTH_LONG).show();
                        }
                        mDialog.dismiss();
                    }
                });

    }

}