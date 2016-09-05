package com.tech.demo;

import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://192.9.203.45/andrdwbst/Service.asmx";
	private static String URL1 = "http://10.0.2.2:8080/MyWebService/MyFirstServiceService?WSDL";
	private static final String METHOD_NAME = "btninsert_Click";
	private static final String SOAP_ACTION = "http://tempuri.org/btninsert_Click";

	// ------------------------------------------------------------------

	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 2;
	private EditText dateDisplay;
	private Button pickDate;
	private int year, month, day;
	private EditText timeDisplay;
	private Button pickTime;
	private int hours, min;
	private Button submit;
	private EditText name;
	private Spinner state;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ============================NetWork==========================
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		// ======================================================

		dateDisplay = (EditText) findViewById(R.id.editdate);
		timeDisplay = (EditText) findViewById(R.id.edittime);
		name = (EditText) findViewById(R.id.editname);
		state = (Spinner) findViewById(R.id.spinner1);

		pickDate = (Button) findViewById(R.id.btndate);
		pickTime = (Button) findViewById(R.id.btntime);

		final Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);

		pickDate.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});

		pickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID);
			}
		});

		submit = (Button) findViewById(R.id.btnsubmit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nm = name.getText().toString().trim();
				String st = state.getSelectedItem().toString();
				String dt = dateDisplay.getText().toString().trim();
				String tm = timeDisplay.getText().toString().trim();

				try {

					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

					 PropertyInfo pi = new PropertyInfo();
					 pi.setName("name");
					 pi.setValue(nm);
					 pi.setType(String.class);
					 request.addProperty(pi);
					
					 pi = new PropertyInfo();
					 pi.setName("State");
					 pi.setValue(st);
					 pi.setType(String.class);
					 request.addProperty(pi);
					
					 pi = new PropertyInfo();
					 pi.setName("Date");
					 pi.setValue(dt);
					 pi.setType(String.class);
					 request.addProperty(pi);
					
					 pi = new PropertyInfo();
					 pi.setName("time");
					 pi.setValue(tm);
					 pi.setType(String.class);
					 request.addProperty(pi);

//					PropertyInfo propInfo = new PropertyInfo();
//					PropertyInfo propInfost = new PropertyInfo();
//					PropertyInfo propInfodt = new PropertyInfo();
//					PropertyInfo propInfotm = new PropertyInfo();
//
//					propInfo.name = "name";
//					propInfo.type = PropertyInfo.STRING_CLASS;
//
//					propInfost.name = "state";
//					propInfost.type = PropertyInfo.STRING_CLASS;
//
//					propInfodt.name = "date";
//					propInfodt.type = PropertyInfo.STRING_CLASS;
//
//					propInfotm.name = "time";
//					propInfotm.type = PropertyInfo.STRING_CLASS;
//
//					request.addProperty("arg0", nm);
//					request.addProperty("arg1", st);
//					request.addProperty("arg2", dt);
//					request.addProperty("arg3", tm);

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					//envelope.bodyOut = request;
					
					
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;

					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							URL);

					androidHttpTransport.call(SOAP_ACTION, envelope);
					// SoapObject result = (SoapObject) envelope.getResponse();
					name.setText(envelope.getResponse().toString());

				} catch (Exception ex) {
					Log.e("My Errorrrrrrrrrr", ex.getMessage());
				}

			}
		});

		hours = cal.get(Calendar.HOUR);
		min = cal.get(Calendar.MINUTE);

		updateDate();
		updateTime();

		Button btnshow = (Button) findViewById(R.id.btnshowdata);
		btnshow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, DisplayData.class);
				startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int yr, int monthOfYear,
				int dayOfMonth) {
			year = yr;
			month = monthOfYear;
			day = dayOfMonth;
			updateDate();
		}
	};
	private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hours = hourOfDay;
			min = minute;
			updateTime();
		}
	};

	private void updateTime() {
		timeDisplay.setText(new StringBuilder().append(hours).append(':')
				.append(min));
	}

	private void updateDate() {
		dateDisplay.setText(new StringBuilder().append(day).append('-')
				.append(month + 1).append('-').append(year));
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timeListener, hours, min, true);
		}
		return null;
	}
}
