package com.tech.demo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayData extends Activity {

	private static final String NAMESPACE = "http://test.se.com/";
	private static String URL = "http://localhost:8080/MyWebService/MyFirstServiceService?WSDL";
	private static String URL1 = "http://10.0.2.2:8080/MyWebService/MyFirstServiceService?WSDL";
	private static final String METHOD_NAME = "dataRetrive";
	private static final String SOAP_ACTION = "http://test.se.com/dataRetrive";

	String data[];

	@Override
	public void onCreate(Bundle bd) {
		super.onCreate(bd);
		setContentView(R.layout.display);
		// ------------------------------------------------
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());

		// ------------------------------------------------

		final Button disp = (Button) findViewById(R.id.btndisplay);
		disp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
					PropertyInfo propInfo = new PropertyInfo();

					propInfo.name = "name";
					propInfo.type = PropertyInfo.STRING_CLASS;

					request.addProperty(propInfo, "abc");

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							URL1);
					androidHttpTransport.call(SOAP_ACTION, envelope);
					SoapObject response = (SoapObject) envelope.bodyIn;

					int count = response.getPropertyCount();

					data = new String[count];
					for (int i = 0; i < count; i++) {
						data[i] = response.getProperty(i).toString();
					}

				} catch (Exception ex) {
					Log.d("My Error", ex.getMessage());
				}

				ListView lv = (ListView) findViewById(R.id.listView1);
				ArrayAdapter<String> ad = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, data);
				lv.setAdapter(ad);
			}
		});

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.next:
			Toast.makeText(getApplicationContext(), "Next", 0).show();
			Intent it = new Intent(this, ContectMenuTest.class);
			startActivity(it);
			break;
		case R.id.previous:
			Toast.makeText(getApplicationContext(), "Previous", 0).show();
			break;

		case R.id.list:
			Toast.makeText(getApplicationContext(), "LIst", 0).show();
			break;

		default:
			break;
		}

		return true;
	}

}
