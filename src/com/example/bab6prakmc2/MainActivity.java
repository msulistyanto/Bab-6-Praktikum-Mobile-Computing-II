package com.example.bab6prakmc2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import Activity;

public class MainActivity extends ListActivity {
	/** Called when the activity is first created. */
	String[] menu = new String[] { "Tambah Data", "Tampilkan Data", "Exit" };
	ListView listView;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Create an array of Strings, that will be put to our ListActivity

		// Create an ArrayAdapter, that will actually make the Strings above
		// appear in the ListView
		// Menset nilai array ke dalam list adapter sehingga data pada array
		// akan dimunculkan dalam list

		listView = getListView();
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String pilihan = ((TextView) arg1).getText().toString();
				// Toast.makeText(getApplicationContext(), pilihan
				// +" "+arg2+" ", Toast.LENGTH_SHORT).show();
				try {
					Intent intent = null;
					if (pilihan.equals("Tambah Data")) {
						intent = new Intent(MainActivity.this,
								InsertActivity.class);
						// Toast.makeText(getApplicationContext(),
						// intent.getDataString(), Toast.LENGTH_SHORT).show();
					} else if (pilihan.equals("Tampilkan Data")) {
						intent = new Intent(MainActivity.this,
								JSONActivity.class);
						// Toast.makeText(getApplicationContext(), pilihan
						// +" "+arg2+" ", Toast.LENGTH_SHORT).show();

					} else if (pilihan.equals("Exit")) {
						finish();
						// Toast.makeText(getApplicationContext(), pilihan
						// +" "+arg2+" ", Toast.LENGTH_SHORT).show();
					}
					startActivity(intent);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}