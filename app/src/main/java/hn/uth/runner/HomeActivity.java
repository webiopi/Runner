package hn.uth.runner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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
    TextView viewCorreo;
    TextView viewProveedor;
    Button btCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewCorreo= (TextView) findViewById(R.id.viewCorreo);
        viewProveedor= (TextView) findViewById(R.id.viewProveedor);
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

        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });
    }
}