package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.utils.DB;

public class ListActivity extends android.app.ListActivity {

    DB db = new DB(this);
    private String[] blocklist_array = new String[0];
    
    @Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		blocklist_array = db.listBlockedNumbers();
		setListAdapter(new ListAdapter(this, blocklist_array));
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
    	super.onListItemClick(l, v, position, id);
    	Object o = this.getListAdapter().getItem(position);
    	String[] keyword = o.toString().split(";");
    	String contactNumber = keyword[0];
    	db.deleteEntry(contactNumber);
    	Toast.makeText(this, contactNumber + " " +"Eliminado", Toast.LENGTH_LONG).show();
		blocklist_array = db.listBlockedNumbers();
		setListAdapter(new ListAdapter(this, blocklist_array));
    }

}
