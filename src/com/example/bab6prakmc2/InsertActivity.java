package com.example.bab6prakmc2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends Activity {
	private EditText txtNama;
	private EditText txtHarga;
	private Button btnSimpan;
	// Seusuaikan url dengan nama domain yang anda gunakan
	private String url = "http://192.168.43.195/menu/addmakanan.php";

	// Method yang dipanggil pada saat aplikaasi dijalankan
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tambah);
		txtNama = (EditText) findViewById(R.id.txtNama);
		txtHarga = (EditText) findViewById(R.id.txtHarga);
		btnSimpan = (Button) findViewById(R.id.btnSimpan);
		// daftarkan even onClick pada btnSimpan
		btnSimpan.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// setiap parameter yang akan dikirim melalui http
					// harus encode agar
					// dapat terbaca dengan baik oleh server
					String nama = URLEncoder.encode(txtNama.getText()
							.toString(), "utf-8");
					String harga = URLEncoder.encode(txtHarga.getText()
							.toString(), "utf-8");
					url += "?nama=" + nama + "&harga=" + harga;
					
					DoTask doTask = new DoTask();
					doTask.execute(url);   
//					getRequest(url);  // eror saat menjalankan melalui intent
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private class DoTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(InsertActivity.this, "Tambah Data " + result + " ",
			Toast.LENGTH_SHORT).show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String pesan = getRequest(params[0]);
			return pesan;
		}
		
	}
	// Method untuk Mengirimkan data ke server event by button login diklik
	public String getRequest(String Url) {
//		Toast.makeText(this, "Tambah Data " + Url + " ", Toast.LENGTH_SHORT)
//				.show();
		String pesan = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
			pesan = request(response);
//			Toast.makeText(this, "Tambah Data " + request(response) + " ",
//					Toast.LENGTH_SHORT).show();
		} catch (Exception ex) {
//			Toast.makeText(this, "Tambah Data Gagal !", Toast.LENGTH_SHORT)
//					.show();
		}
		return pesan;
	}

	// Method untuk Menenrima data dari server
	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}
}
