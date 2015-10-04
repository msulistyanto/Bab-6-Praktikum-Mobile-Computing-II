package com.example.bab6prakmc2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

//import com.example.bab5prakmc2.R;

import android.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

//import Activity;

public class JSONActivity extends Activity {

	private JSONObject jObject;

	private String xResult ="";
	//Sesuaikan url dengan nama domain yang anda gunakan
	private String url = "http://192.168.43.195/menu/daftarmakanan.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftarmakanan);
		TextView txtResult = (TextView)findViewById(R.id.TextViewResult);
        xResult = getRequest(url);
       	try {
 			parse(txtResult);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}

	}
	private void parse(TextView txtResult) throws Exception {
		jObject = new JSONObject(xResult);

		JSONArray menuitemArray = jObject.getJSONArray("makanan");
		String sret="";
		for (int i = 0; i < menuitemArray.length(); i++) {
			sret +=menuitemArray.getJSONObject(i)
			.getString("nama_makanan").toString()+" : ";
			System.out.println(menuitemArray.getJSONObject(i)
					.getString("nama_makanan").toString());
			System.out.println(menuitemArray.getJSONObject(i).getString(
					"harga").toString());
			sret +=menuitemArray.getJSONObject(i).getString(
			"harga").toString()+"\n";
		}
		txtResult.setText(sret);
	}

	
//	 Method untuk Mengirimkan data ke server
	
	public String getRequest(String Url){

       String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try{
          HttpResponse response = client.execute(request);
          sret =request(response);

        }catch(Exception ex){
        	Toast.makeText(this,"Gagal "+sret, Toast.LENGTH_SHORT).show();
        }
        return sret;

    }
//	Method untuk Menerima data dari server
	
	public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }
}
