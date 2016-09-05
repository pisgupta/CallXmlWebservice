package com.tech.demo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ContectMenuTest extends ListActivity {

	static String item[] = new String[] { "A", "B", "C", "D", "E", "F", "G" };

	@Override
	public void onCreate(Bundle bd) {
		super.onCreate(bd);
		ArrayAdapter<String> ar = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				item);
		setListAdapter(ar);
		registerForContextMenu(getListView());

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.edit:
			Toast.makeText(getApplicationContext(), "Edit",0).show();
			break;
		case R.id.delete:
			Toast.makeText(getApplicationContext(), "Delete",0).show();
			break;
		case R.id.save:
			Toast.makeText(getApplicationContext(), "Save",0).show();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.contexttest, menu);

	}

}
