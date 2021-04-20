package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.entidades.Usuario;

public class Balance extends AppCompatActivity {
    Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        recibirDatos();


    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresoMov:
                // Viajo al Panel de Ingresos de Movimiento
                Toast.makeText(getApplicationContext(), "Ingrese un movimiento positivo porfavor!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnIngresoGasto:
                // Viajo al Panel de Egresos de Caja
                Toast.makeText(getApplicationContext(), "Ingrese un movimiento negativo porfavor!",Toast.LENGTH_SHORT).show();
                //Intent ingresoUsuario = new Intent(Login.this, IngresoUsuario.class);
                //startActivity(ingresoUsuario);
                break;
            case R.id.imgBalance:
                // Viajo al Panel Balance
                Intent balance = new Intent(Balance.this, Balance.class);
                Bundle bundleBalance  = new Bundle();
                bundleBalance.putSerializable("perfil",perfil);
                balance.putExtras(bundleBalance);
                startActivity(balance);
                break;
            case R.id.imgCajas:
                // Viajo al Panel de Cajas
                Intent cajas  = new Intent(Balance.this, Cajas.class);
                Bundle bundleCajas = new Bundle();
                bundleCajas.putSerializable("perfil",perfil);
                cajas.putExtras(bundleCajas);
                startActivity(cajas);
                break;
            case R.id.imgNotificaciones:
                // Viajo al Panel de Notificaciones
                Toast.makeText(getApplicationContext(), "Panel de Notificaciones Proximamente!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}