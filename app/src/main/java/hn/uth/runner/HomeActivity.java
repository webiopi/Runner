package hn.uth.runner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

class ProviderType {
    public static final String BASICA = "BASICA";
    private final String typeName;

    ProviderType(String typeName) {
        this.typeName = typeName;
    }
    public String getName() {
        return typeName;
    }
}

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ImageView viewContacto;

    TextView viewCorreo;
    TextView viewProveedor;

    EditText txtNombre;
    EditText txtPais;

    Button btGuardar;
    Button btFoto;
    Button btCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewContacto= (ImageView) findViewById(R.id.viewContacto);

        viewCorreo= (TextView) findViewById(R.id.viewCorreo);
        viewProveedor= (TextView) findViewById(R.id.viewProveedor);

        txtNombre= (EditText) findViewById(R.id.txtNombre);
        txtPais= (EditText) findViewById(R.id.txtPais);

        btGuardar= (Button) findViewById(R.id.btGuardar);
        btFoto= (Button) findViewById(R.id.btFoto);
        btCerrar= (Button) findViewById(R.id.btCerrar);

        //Llamar a Setup
        Setup();
    }

    private void Setup() {
        Intent intent = getIntent();
        String correo = intent.getStringExtra("correo");
        String proveedor = intent.getStringExtra("proveedor");

        // Asignar el valor del correo al TextView viewCorreo
        viewCorreo.setText(correo);

        // Asignar el valor del proveedor al TextView viewProveedor
        viewProveedor.setText(proveedor);

        //Empieza Codigo de los Eventos de los Botones
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString().trim();
                String pais = txtPais.getText().toString().trim();

                if (nombre.isEmpty() || pais.isEmpty()) {
                    // Mostrar mensaje de error si los campos están vacíos
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("proveedor", proveedor);
                    data.put("nombre", nombre);
                    data.put("pais", pais);

                    db.collection("usuarios").document(correo).set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Operación exitosa
                                    Toast.makeText(getApplicationContext(), "Datos guardados con éxito", Toast.LENGTH_SHORT).show();

                                    // Limpiar los campos después de guardar exitosamente
                                    txtNombre.setText("");
                                    txtPais.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error al guardar
                                    Toast.makeText(getApplicationContext(), "Error al guardar datos", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });

    }
}