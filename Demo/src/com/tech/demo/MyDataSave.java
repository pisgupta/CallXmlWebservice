package com.tech.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyDataSave extends Activity {
	EditText name, age, alldata;
	Button save, retrive;
	FileWriter fw;
	FileReader fr;
	BufferedReader br;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydatasave);
		name = (EditText) findViewById(R.id.editname);
		age = (EditText) findViewById(R.id.editage);

		alldata = (EditText) findViewById(R.id.btnsave);

		save = (Button) findViewById(R.id.btnretrive);
		retrive = (Button) findViewById(R.id.btnretrive);

		String sdcard = Environment.getExternalStorageDirectory().getName();
		String myfolder = "/mydir";
		File dir = new File(sdcard + myfolder);
		dir.mkdir();
		final File fname = new File(dir, "emp.txt");
		if (!fname.exists()) {
			try {
				fname.createNewFile();
				Toast.makeText(getApplicationContext(), "File Create", 0)
						.show();
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("Exception", e.getMessage());
			}
		} else {
			Toast.makeText(getApplicationContext(), "File Exists", 0).show();
		}
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					fw = new FileWriter(fname, true);
					fw.write(name.getText().toString().trim() + "\t");
					fw.write(age.getText().toString().trim() + "\n");
					fw.flush();
					fw.close();
					reftesh();
					Toast.makeText(getApplicationContext(), "Data Save", 0)
							.show();
				} catch (Exception e) {
					// TODO: handle exception
					Log.d("MyException", e.toString());
				}

			}
		});

		retrive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					fr = new FileReader(fname);
					br = new BufferedReader(fr);
					String str = br.readLine();
					while (str != null) {

						alldata.setText(str);
						Toast.makeText(getApplicationContext(), str, 1).show();
						str = br.readLine();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.d("Exception", e.getMessage());
				}
			}
		});

	}

	public void reftesh() {
		name.setText("");
		age.setText("");
	}
}
