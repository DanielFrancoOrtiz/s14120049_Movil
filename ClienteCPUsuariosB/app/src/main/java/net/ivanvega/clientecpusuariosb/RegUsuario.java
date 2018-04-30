package net.ivanvega.clientecpusuariosb;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegUsuario extends Activity implements OnClickListener {
	EditText txtN, txtE, txtC;
	TextView lblID;
	Button btnG,btnC;
	Intent intentAccion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_usuario);

		lblID = (TextView)findViewById(R.id.lblID);

		txtN = (EditText)findViewById(R.id.txtNombre);
		txtE = (EditText)findViewById(R.id.txtEmail);
		txtC = (EditText)findViewById(R.id.txtPass);

		btnG = (Button)findViewById(R.id.btnGuardar); btnG.setOnClickListener(this);
		btnC = (Button)findViewById(R.id.btnCancelar);btnC.setOnClickListener(this);

		intentAccion =  getIntent();
		personalizarAccion(intentAccion.getStringExtra("accion"));
	}

	public void personalizarAccion(String  accion) {
		btnC.setText("Cancelar");
		if (accion.equals("insert")){
			this.setTitle("Registrar Usuario");
			btnG.setText("Guardar");
		}

		if (accion.equals("edit")){
			this.setTitle("Editar Usuario");
			btnG.setText("Actualizar");
			cargarUsuario(Integer.parseInt(intentAccion.getStringExtra("_id")));
		}
	}

	private void cargarUsuario(int id) {
		Cursor c = getContentResolver().query(Uri.withAppendedPath(Uri.parse(UsuarioContrato.CONTENT_URI),String.valueOf(id)),
				null, null,
				null, null );
		if (c.moveToFirst()){
			lblID.setText(String.valueOf( c.getInt(0)));
			txtN.setText(c.getString(1));
			txtE.setText(c.getString(2));
			txtC.setText(c.getString(3));
		}else{
			Toast.makeText(this,"No Cargo el usuario",Toast.LENGTH_LONG).show();
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reg_usuario, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(btnG)){
			if(intentAccion.getStringExtra("accion").equals("insert")){
				insert();
			}else{
				update();
			}
		}

		if (v.equals(btnC)){
			finish();
		}
	}


	private void update() {
		ContentValues cv = new ContentValues();
		cv.put(UsuarioContrato.FIELD_ID,lblID.getText().toString());
		cv.put(UsuarioContrato.FIELD_NAME, txtN.getText().toString());
		cv.put(UsuarioContrato.FIELD_EMAIL,txtE.getText().toString());
		cv.put(UsuarioContrato.FIELD_PASS, txtC.getText().toString());

		int row = getContentResolver().update(Uri.withAppendedPath(
				Uri.parse(UsuarioContrato.CONTENT_URI),String.valueOf(lblID.getText())),
				cv,null,null);
		try {
			if(row!=0){
				Toast.makeText(getBaseContext(), "USUARIO EDITADO",
						Toast.LENGTH_LONG).show();
				setResult(RESULT_OK, null);
				finish();
			}else {
				Toast.makeText(getBaseContext(), "FALLO EDITAR USUARIO",
						Toast.LENGTH_LONG).show();
				setResult(RESULT_CANCELED, null);
				finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("UPDATE",e.getMessage());
			finish();
		}
	}


	private void insert() {
		ContentValues cv = new ContentValues();
		cv.put(UsuarioContrato.FIELD_NAME, txtN.getText().toString());
		cv.put(UsuarioContrato.FIELD_EMAIL,txtE.getText().toString());
		cv.put(UsuarioContrato.FIELD_PASS, txtC.getText().toString());
		Uri u = getContentResolver().insert( Uri.parse(  UsuarioContrato.CONTENT_URI),

				cv
		)   ;
		try {
			if( u!=null){
				Toast.makeText(getBaseContext(), "USUARIO INSERTADO",
						Toast.LENGTH_LONG).show();
				setResult(RESULT_OK, null);
				finish();
			}else {
				Toast.makeText(getBaseContext(), "FALLO INSERTAR USUARIO",
						Toast.LENGTH_LONG).show();
				setResult(RESULT_CANCELED, null);
				finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("INSERT",e.getMessage());
			finish();
		}
	}

}
