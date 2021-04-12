package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Usuario;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class Login extends AppCompatActivity {

    EditText edtLogin,edtPass;
    ArrayList<Usuario> personasLista;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPass = (EditText) findViewById(R.id.edtPass);
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = null;
        personasLista = new ArrayList<Usuario>();

        // select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO,null);
        while (cursor.moveToNext()){
            usuario = new Usuario(null,null,null,null,null);
            usuario.setIdUsuario(cursor.getString(0));
            usuario.setContraseña(cursor.getString(1));
            usuario.setNombre(cursor.getString(2));
            usuario.setPais(cursor.getString(4));

            personasLista.add(usuario);
        }
        boolean usuarioValido = false;
        usuarioValido = obtenerLista(usuarioValido);

        if(obtenerLista(usuarioValido)){
            Toast.makeText(getApplicationContext(),"Verifico correctamente",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Usuario o Contraseña no valida!",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean obtenerLista(boolean usuarioValido) {

        for (int i = 0; i< personasLista.size();i++){
            String usuarioAux    = personasLista.get(i).getIdUsuario().toUpperCase();
            String contraseñaAux = personasLista.get(i).getContraseña().toUpperCase();

            if (usuarioAux.equals(edtLogin.getText().toString().toUpperCase()) && contraseñaAux.equals(edtPass.getText().toString().toUpperCase())){
                usuarioValido = true;
            }
        }
        return usuarioValido;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresar: consultarListaPersonas();
        }
    }
}