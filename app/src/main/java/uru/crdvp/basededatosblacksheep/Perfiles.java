package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Currency;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Usuario;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class Perfiles extends AppCompatActivity {

    ListView listViewPerfiles;
    ConexionSQLiteHelper conn;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_BlackSheep",null,1);

        listViewPerfiles = (ListView) findViewById(R.id.listViewPerfiles);
        consultarListaPersonas();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listViewPerfiles.setAdapter(adaptador);

        listViewPerfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion = "Usuario: " + listaUsuario.get(position).getIdUsuario()+"\n";
                informacion += "Nombre: " + listaUsuario.get(position).getNombre()+"\n";
                informacion += "Pais: " + listaUsuario.get(position).getPais()+"\n";
                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_SHORT).show();

                Usuario user = listaUsuario.get(position);
                Intent intent = new Intent(Perfiles.this,DetallePerfil.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = null;
        listaUsuario = new ArrayList<Usuario>();
        // select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO,null);
        while (cursor.moveToNext()){
            usuario = new Usuario(null,null,null,null,null);
            usuario.setIdUsuario(cursor.getString(0));
            usuario.setContraseña(cursor.getString(1));
            usuario.setNombre(cursor.getString(2));
            usuario.setPais(cursor.getString(4));

            listaUsuario.add(usuario);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i = 0; i< listaUsuario.size();i++){
            listaInformacion.add(listaUsuario.get(i).getIdUsuario() + " - "
                    + listaUsuario.get(i).getNombre());
        }
    }
}