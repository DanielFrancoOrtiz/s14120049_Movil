package net.ivanvega.clientecpusuariosb;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_INSERT = 0;
    private static final int REQUEST_CODE_EDIT = 1;
    ListView lst ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = (ListView)findViewById(R.id.lst);
        lst.setOnCreateContextMenuListener(this);
    }

    public void btnCCP_click(View v){
        cargarLista();


    }

    public void cargarLista(){
        Cursor c = getContentResolver().query(Uri.parse(   UsuarioContrato.CONTENT_URI),
                null, null,
                null, null );

        Log.d("CPU", String.valueOf(c.getCount()));

        SimpleCursorAdapter sca =
                new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,
                        c, new String[]{
                        UsuarioContrato.FIELD_ID, UsuarioContrato.FIELD_NAME
                },
                        new int[]{android.R.id.text1, android.R.id.text2},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                );


        lst.setAdapter(sca);
    }


    public void eliminar(int id){
        int a = getContentResolver().delete(Uri.withAppendedPath(Uri.parse(UsuarioContrato.CONTENT_URI),String.valueOf(id)),
                null,new String[]{String.valueOf(id)});
        if (a!=0){
            Toast.makeText(this,"Usuario Eliminado",Toast.LENGTH_LONG).show();
            cargarLista();
        }else{
            Toast.makeText(this,"No se pudo eliminar",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, 0, 0, "Agregar").setIcon(R.drawable.ic_launcher);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId()==0){
            Intent i = new Intent(getApplicationContext(), RegUsuario.class);
            i.putExtra("accion", "insert");
            startActivityForResult(i, REQUEST_CODE_INSERT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        menu.add(0, 0, 0, "editar").setIcon(R.drawable.ic_launcher);
        menu.add(0,1,1,"eliminar"); menu.getItem(1).setIcon(R.drawable.ic_launcher);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo acmi =  (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();


        //editar
        if (item.getItemId()==0){
            Intent i = new Intent(this, RegUsuario.class);
            i.putExtra("accion", "edit");
            i.putExtra("_id",String.valueOf(acmi.id));
            Log.e("ACMIFFFFFFFFFFFFFFFFFID", "" + acmi.id) ;
            startActivityForResult(i, REQUEST_CODE_EDIT);

        }

        //eliminar
        if (item.getItemId()==1){
            this.eliminar(Integer.parseInt(acmi.id+""));
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode== REQUEST_CODE_INSERT){
            if (resultCode==RESULT_OK){
                cargarLista();
            }
        }

        if(requestCode==REQUEST_CODE_EDIT){
            if(resultCode==RESULT_OK){
                cargarLista();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }





}

