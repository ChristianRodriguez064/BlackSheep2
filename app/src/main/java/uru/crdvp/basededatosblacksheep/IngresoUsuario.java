package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class IngresoUsuario extends AppCompatActivity {

    EditText tvUsuarioLog,tvContraseñaLog,tvNombreLog,tvFechaNacimiento,tvPaisLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_usuario);

        tvUsuarioLog = (EditText) findViewById(R.id.tvUsuarioLog);
        tvContraseñaLog = (EditText) findViewById(R.id.tvContraseñaLog);
        tvNombreLog = (EditText) findViewById(R.id.tvNombreLog);
        //tvFechaNacimiento = (EditText) findViewById(R.id.tvFechaNacimiento);
        tvPaisLog = (EditText) findViewById(R.id.tvPaisLog);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);

        Button Confirmar = (Button) findViewById(R.id.BtnConfirmar);
        Confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Toast.makeText(getApplicationContext(),"Funciona el boton",Toast.LENGTH_SHORT).show();
                // Usar registrarUsuario() y no registrarUsuarioSql() --> Falta insert
                registrarUsuario();
                //registrarUsuarioSql();
            }
        });
    }

    private void registrarUsuarioSql() {
        //--> Falta la ejecucion del inser!!!
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Insert into usuario (idUsuario,contraseña,nombre,fechaNacimiento,pais) values ('UsuarioADM','1234','Administrador','01/01/2021','Uruguay')

        String insert = "INSERT INTO " + Utilidades.TABLA_USUARIO + " ("
                +Utilidades.CAMPO_IDUSUARIO + ","
                +Utilidades.CAMPO_CONTRASEÑA + ", "
                +Utilidades.CAMPO_NOMBRE + ", "
                +Utilidades.CAMPO_FECHANACIMIENTO + ", "
                +Utilidades.CAMPO_PAIS + ")"
                +" VALUES ('" +tvUsuarioLog.getText().toString()+"', '" + tvContraseñaLog.getText().toString()+"', '"+ tvNombreLog.getText().toString()+"', "+null+", '"+ tvPaisLog.getText().toString()+"')";

        db.close();

        tvUsuarioLog.setText("");
        tvContraseñaLog.setText("");
        tvNombreLog.setText("");
        tvPaisLog.setText("");
        Toast.makeText(getApplicationContext(),"El registro fue exitoso",Toast.LENGTH_SHORT).show();

    }

    private void registrarUsuario() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_IDUSUARIO,tvUsuarioLog.getText().toString());
        values.put(Utilidades.CAMPO_CONTRASEÑA,tvContraseñaLog.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,tvNombreLog.getText().toString());
        //values.put(Utilidades.CAMPO_FECHANACIMIENTO,tvFechaNacimiento.getText().toString());
        values.put(Utilidades.CAMPO_PAIS,tvPaisLog.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_IDUSUARIO,values);

        Toast.makeText(getApplicationContext(),"Id Registro" + idResultante,Toast.LENGTH_SHORT).show();
        db.close();

        tvUsuarioLog.setText("");
        tvContraseñaLog.setText("");
        tvNombreLog.setText("");
        tvPaisLog.setText("");
        Toast.makeText(getApplicationContext(),"El registro fue exitoso",Toast.LENGTH_SHORT).show();
    }
}