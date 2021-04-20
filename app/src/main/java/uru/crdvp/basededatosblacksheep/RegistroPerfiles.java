package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Usuario;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class RegistroPerfiles extends AppCompatActivity {

    EditText perfil;
    Spinner comboUsuarios,comboPerfiles;
    ArrayList<String> listaPersonas;
    ArrayList<Usuario> personasLista;
    ConexionSQLiteHelper conn;
    Usuario usuarioIngreso;
    boolean registroCorrecto = false;
    TextView usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perfiles);

        recibirDatos();

        perfil = (EditText) findViewById(R.id.tvNombrePerfil);
        //comboUsuarios = (Spinner) findViewById(R.id.SpnUsuario);
        comboPerfiles = (Spinner) findViewById(R.id.SpnPerfiles);
        usuario = (TextView) findViewById(R.id.tvUsuario);
        usuario.setText(usuarioIngreso.getIdUsuario());

        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        
        consultarListaPersonas();

        //ArrayAdapter<CharSequence> adaptador = new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaPersonas);
        //comboUsuarios.setAdapter(adaptador);

        ArrayAdapter<CharSequence> adaptadorPerfiles = ArrayAdapter.createFromResource(this,R.array.perfil_defecto,android.R.layout.simple_spinner_item);
        comboPerfiles.setAdapter(adaptadorPerfiles);
/*
        comboUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

 */
    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        usuarioIngreso = (Usuario) objetoEnviado.getSerializable("usuario");
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
        obtenerLista();
    }

    private void obtenerLista() {
        listaPersonas = new ArrayList<String>();
        for (int i = 0; i< personasLista.size();i++){
            //if(personasLista.get(i).getIdUsuario().toUpperCase().trim().equals(usuarioIngreso.getIdUsuario().toUpperCase().trim())){
                listaPersonas.add(personasLista.get(i).getIdUsuario() + " - "
                                    + personasLista.get(i).getNombre());
            //}
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnConfirmar:
                registrarPerfil();
                if (registroCorrecto){
                    finish();
                }
        }
    }

    private void registrarPerfil() {
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //Utilizo para grabar tabla de perfil
        ContentValues values = new ContentValues();
        int idComboPerfil    = (int) comboPerfiles.getSelectedItemId();
        values.put(Utilidades.CAMPO_IDPERFIL,idComboPerfil);
        values.put(Utilidades.CAMPO_PERFIL_NOMBRE,perfil.getText().toString());

        Toast.makeText(getApplicationContext(),"idComboPerfil " + idComboPerfil + "\n" + "Nombre: " + perfil.getText().toString(),Toast.LENGTH_SHORT).show();
        //int idCombo = (int) comboUsuarios.getSelectedItemId();

        //Utilizo para grabar tabla de relacion perfil - usuario
        ContentValues valuesRelacion = new ContentValues();
        valuesRelacion.put(Utilidades.CAMPO_IDPERFILU,idComboPerfil);
        valuesRelacion.put(Utilidades.CAMPO_IDPERFIL_USUARIO,usuarioIngreso.getIdUsuario());

        try {
            Long idResultante = db.insert(Utilidades.TABLA_PERFILES,Utilidades.CAMPO_IDPERFIL,values);
            Long idResultanteRelacion = db.insert(Utilidades.TABLA_USUARIO_PERFILES,Utilidades.CAMPO_IDPERFIL_USUARIO,valuesRelacion);
            Toast.makeText(getApplicationContext(),"idResultante Perfiles " + idResultante,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"idResultanteRelacion " + idResultanteRelacion,Toast.LENGTH_SHORT).show();
            registroCorrecto = true;
            db.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Verifique el perfil ingresa.",Toast.LENGTH_SHORT).show();
            db.close();
        }
    }
}