package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.entidades.UsuariosPerfiles;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class Cajas extends AppCompatActivity {

    Perfil perfil;
    FloatingActionButton fabAgregarCaja;
    ConexionSQLiteHelper conn;
    Caja caja,cajaAux;
    ArrayList<Integer> listaIdCajas;
    ArrayList<Caja> listaCajas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajas);
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);

        recibirDatos();
        int cantidadCajas = verificoPerfil();

        //Toast.makeText(getApplicationContext(), "Ingrese con " + cantidadCajas + " Cajas!",Toast.LENGTH_SHORT).show();

        if(cantidadCajas == 0){
            cajasPorDefecto();
        }

        fabAgregarCaja = (FloatingActionButton) findViewById(R.id.fabAgregarCaja);
        fabAgregarCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cajaAux       = new Caja(0,perfil.getIdPerfil().toString(),null,null,null,null);
                Intent intent = new Intent(Cajas.this,ActualizarCaja.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("caja",cajaAux);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Funciona Boton Flotante!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cajasPorDefecto() {
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //--> Cargo Cajas por Defecto
        int defectoCaja = 1;
        ContentValues values = new ContentValues();
        while (defectoCaja < 6){
            values.clear();
            values.put(Utilidades.CAMPO_CAJA_IDPERFIL,perfil.getIdPerfil());
            values.put(Utilidades.CAMPO_PERFIL_NOMBRE,"Caja " + defectoCaja);
            values.put(Utilidades.CAMPO_CAJA_PORCENTAJE,20);
            values.put(Utilidades.CAMPO_CAJA_MONTO,0);
            values.put(Utilidades.CAMPO_CAJA_DESCRIPCION,"");
            Long idResultante = db.insert(Utilidades.TABLA_CAJAS, Utilidades.CAMPO_IDCAJA,values);
            //Toast.makeText(getApplicationContext(),"Id Registro: " + idResultante,Toast.LENGTH_SHORT).show();
            defectoCaja++;
        }
        db.close();
    }

    private int verificoPerfil() {
        SQLiteDatabase db       = conn.getReadableDatabase();
        int cantidadCajasPerfil = 0;
        int idCaja;
        String idPerfil;
        //listaIdCajas = new ArrayList<Integer>();
        listaCajas = new ArrayList<Caja>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CAJAS,null);
            while (cursor.moveToNext()){
                // Cargo el IdPerfil de la relacion
                idCaja   = cursor.getInt(0);
                idPerfil = cursor.getString(1);
                //Toast.makeText(getApplicationContext(),"idCaja: " + idCaja + "\n" + "idPerfil: " + idPerfil,Toast.LENGTH_SHORT).show();
                if(perfil.getIdPerfil() == Integer.parseInt(idPerfil.trim())){
                    //listaIdCajas.add(idCaja);
                    cantidadCajasPerfil++;
                    caja = new Caja(idCaja,idPerfil,cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
                    listaCajas.add(caja);
                }
            }
            db.close();
            //Toast.makeText(getApplicationContext(),"El registro fue exitoso",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en la carga de Cajas!",Toast.LENGTH_SHORT).show();
        }
        return cantidadCajasPerfil;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgPerfil1:
                if(listaCajas.size()>0){
                    /*
                    Toast.makeText(getApplicationContext(), "Ingrese al perfil 1!" + "\n" +
                                    "idCaja: " + listaCajas.get(0).getIdCaja() + "\n" +
                                    "Nombre: " + listaCajas.get(0).getNombre()
                            ,Toast.LENGTH_SHORT).show();

                     */
                    //--> Viajo a panel de Detalle de Cajas!
                    Caja caja1 = listaCajas.get(0);
                    Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                    Bundle bundleDetalleCaja1  = new Bundle();
                    bundleDetalleCaja1.putSerializable("caja",caja1);
                    detalleCaja.putExtras(bundleDetalleCaja1);
                    startActivity(detalleCaja);

                } else {
                    Toast.makeText(getApplicationContext(), "Esta caja no fue ingresada aun, por favor ingresar una nueva caja para poder utilizarla!",Toast.LENGTH_SHORT).show();
                }
                // listaCajas.get(0);
                break;
            case R.id.imgPerfil2:
                if(listaCajas.size()>1){
                    /*
                    Toast.makeText(getApplicationContext(), "Ingrese al perfil 2!" + "\n" +
                                    "idCaja: " + listaCajas.get(1).getIdCaja() + "\n" +
                                    "Nombre: " + listaCajas.get(1).getNombre()
                            ,Toast.LENGTH_SHORT).show();

                     */
                    //--> Viajo a panel de Detalle de Cajas!
                    Caja caja2 = listaCajas.get(1);
                    Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                    Bundle bundleDetalleCaja1  = new Bundle();
                    bundleDetalleCaja1.putSerializable("caja",caja2);
                    detalleCaja.putExtras(bundleDetalleCaja1);
                    startActivity(detalleCaja);

                } else {
                    Toast.makeText(getApplicationContext(), "Esta caja no fue ingresada aun, por favor ingresar una nueva caja para poder utilizarla!",Toast.LENGTH_SHORT).show();
                }
                // listaCajas.get(1);
                //Intent ingresoUsuario = new Intent(Login.this, IngresoUsuario.class);
                //startActivity(ingresoUsuario);
                break;
            case R.id.imgPerfil3:
                if(listaCajas.size()>2){
                    /*
                    Toast.makeText(getApplicationContext(), "Ingrese al perfil 3!" + "\n" +
                                    "idCaja: " + listaCajas.get(2).getIdCaja() + "\n" +
                                    "Nombre: " + listaCajas.get(2).getNombre()
                            ,Toast.LENGTH_SHORT).show();
                     */
                    //--> Viajo a panel de Detalle de Cajas!
                    Caja caja3 = listaCajas.get(2);
                    Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                    Bundle bundleDetalleCaja1  = new Bundle();
                    bundleDetalleCaja1.putSerializable("caja",caja3);
                    detalleCaja.putExtras(bundleDetalleCaja1);
                    startActivity(detalleCaja);

                } else {
                    Toast.makeText(getApplicationContext(), "Esta caja no fue ingresada aun, por favor ingresar una nueva caja para poder utilizarla!",Toast.LENGTH_SHORT).show();
                }
                // listaCajas.get(2);
                //Intent ingresoUsuario = new Intent(Login.this, IngresoUsuario.class);
                //startActivity(ingresoUsuario);
                break;
            case R.id.imgPerfil4:
                if(listaCajas.size()>3){
                    /*
                    Toast.makeText(getApplicationContext(), "Ingrese al perfil 4!" + "\n" +
                                    "idCaja: " + listaCajas.get(3).getIdCaja() + "\n" +
                                    "Nombre: " + listaCajas.get(3).getNombre()
                            ,Toast.LENGTH_SHORT).show();
                     */
                    //--> Viajo a panel de Detalle de Cajas!
                    Caja caja4 = listaCajas.get(3);
                    Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                    Bundle bundleDetalleCaja1  = new Bundle();
                    bundleDetalleCaja1.putSerializable("caja",caja4);
                    detalleCaja.putExtras(bundleDetalleCaja1);
                    startActivity(detalleCaja);

                } else {
                    Toast.makeText(getApplicationContext(), "Esta caja no fue ingresada aun, por favor ingresar una nueva caja para poder utilizarla!",Toast.LENGTH_SHORT).show();
                }
                // listaCajas.get(3);
                //Intent ingresoUsuario = new Intent(Login.this, IngresoUsuario.class);
                //startActivity(ingresoUsuario);
                break;
            case R.id.imgPerfil5:
                if(listaCajas.size()>4){
                    /*
                    Toast.makeText(getApplicationContext(), "Ingrese al perfil 5!" + "\n" +
                                    "idCaja: " + listaCajas.get(4).getIdCaja() + "\n" +
                                    "Nombre: " + listaCajas.get(4).getNombre()
                            ,Toast.LENGTH_SHORT).show();
                     */
                    //--> Viajo a panel de Detalle de Cajas!
                    Caja caja5 = listaCajas.get(4);
                    Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                    Bundle bundleDetalleCaja1  = new Bundle();
                    bundleDetalleCaja1.putSerializable("caja",caja5);
                    detalleCaja.putExtras(bundleDetalleCaja1);
                    startActivity(detalleCaja);

                } else {
                    Toast.makeText(getApplicationContext(), "Esta caja no fue ingresada aun, por favor ingresar una nueva caja para poder utilizarla!",Toast.LENGTH_SHORT).show();
                }
                // listaCajas.get(4);
                //Intent ingresoUsuario = new Intent(Login.this, IngresoUsuario.class);
                //startActivity(ingresoUsuario);
                break;
            case R.id.imgPerfil6:
                if(listaCajas.size()>5){
                    /*
                    Toast.makeText(getApplicationContext(), "Ingrese al perfil 6!" + "\n" +
                                    "idCaja: " + listaCajas.get(5).getIdCaja() + "\n" +
                                    "Nombre: " + listaCajas.get(5).getNombre()
                            ,Toast.LENGTH_SHORT).show();
                     */
                    //--> Viajo a panel de Detalle de Cajas!
                    Caja caja6 = listaCajas.get(5);
                    Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                    Bundle bundleDetalleCaja1  = new Bundle();
                    bundleDetalleCaja1.putSerializable("caja",caja6);
                    detalleCaja.putExtras(bundleDetalleCaja1);
                    startActivity(detalleCaja);

                } else {
                    Toast.makeText(getApplicationContext(), "Esta caja no fue ingresada aun, por favor ingresar una nueva caja para poder utilizarla!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgBalance:
                // Viajo al Panel Balance
                Intent balance = new Intent(Cajas.this, Balance.class);
                Bundle bundleBalance  = new Bundle();
                bundleBalance.putSerializable("perfil",perfil);
                balance.putExtras(bundleBalance);
                startActivity(balance);
                break;
            case R.id.imgCajas:
                // Viajo al Panel de Cajas
                Toast.makeText(getApplicationContext(), "Panel de Cajas!",Toast.LENGTH_SHORT).show();
                Intent cajas  = new Intent(Cajas.this, Cajas.class);
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
    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }
}