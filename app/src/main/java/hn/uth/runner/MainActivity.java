package hn.uth.runner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";
    EditText Correo;
    EditText Contraseña;

    Button Sesion;
    Button Registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        Correo= (EditText) findViewById(R.id.txtCorreo);
        Contraseña= (EditText) findViewById(R.id.txtContraseña);

        Sesion= (Button) findViewById(R.id.btSesion);
        Registro= (Button) findViewById(R.id.btRegistro);


        mAuth = FirebaseAuth.getInstance();
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Correo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El correo no puede estar vacio", Toast.LENGTH_LONG).show();
                } else if (Contraseña.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "La Contraseña no puede estar vacia", Toast.LENGTH_LONG).show();
                } else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(Correo.getText().toString(), Contraseña.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                ProviderType proveedor = new ProviderType("BASICA");
                                MostrarHome(Correo.getText().toString(), proveedor);
                                Toast.makeText(getApplicationContext(), "El registro se ha creado con exito", Toast.LENGTH_LONG).show();
                                final FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "Correo De Verificacion Enviado A: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                    ProviderType proveedor = new ProviderType("BASICA");
                                                    //MostrarHome(Correo.getText().toString(), proveedor);
                                                    Toast.makeText(getApplicationContext(), "El registro se ha creado con exito", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                                    Toast.makeText(MainActivity.this, "Error Al Enviar La Verificacion De Correo.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else {
                                Toast.makeText(getApplicationContext(), "Se ha producido un error al REGISTRAR al usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        Sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Correo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "El correo no puede estar vacio", Toast.LENGTH_LONG).show();
                } else if (Contraseña.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "La Contraseña no puede estar vacia", Toast.LENGTH_LONG).show();
                } else{
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(Correo.getText().toString(), Contraseña.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (mAuth.getCurrentUser().isEmailVerified()){
                                    ProviderType proveedor = new ProviderType("BASICA");
                                    MostrarHome(Correo.getText().toString(), proveedor);
                                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Porfavor Verifique Su Correo Electronico", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Se ha producido un error al AUTENTICAR al usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void MostrarHome(String correo, ProviderType proveedor) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("correo", correo);
        intent.putExtra("proveedor", proveedor.getName());
        startActivity(intent);
    }
}