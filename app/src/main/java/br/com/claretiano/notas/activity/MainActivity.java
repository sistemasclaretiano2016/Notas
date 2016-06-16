package br.com.claretiano.notas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import br.com.claretiano.notas.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLancarNota = (Button) findViewById(R.id.btn_lancar_nota);
        if (btnLancarNota != null)
            btnLancarNota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LancamentoActivity.class);
                    startActivity(intent);
                }
            });


        Button btnListarNotas = (Button) findViewById(R.id.btn_listar_notas);
        if (btnListarNotas != null)
            btnListarNotas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ListaNotasActivity.class);
                    startActivity(intent);
                }
            });
    }
}
