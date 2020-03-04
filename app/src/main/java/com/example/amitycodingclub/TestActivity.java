package com.example.amitycodingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class TestActivity extends AppCompatActivity {
    TextView textView;
    String str = "xyz";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.demotext);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("getusers");
    }
    public class BackgroundWorker extends AsyncTask<String, String, String> {
        Context context;
        AlertDialog alertDialog;
        private Constants constants;
        private String ip;

        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            constants = new Constants(context);
            String login_url = "https://acc.amityaump.com/wp-json/app-gateway/users?";

            if (type.equals("getusers")){
                try {
                    String apiKey = constants.getApiKey();
                    Log.i("status","inside the login try catch");
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();



                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);


                    //this is the auth token.
                    httpURLConnection.setRequestProperty("Authorization","Bearer "+constants.getToken());

                    Log.i("status","Http url connection established properly");

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    Log.i("status","buffer writer working");

                    String post_data = URLEncoder.encode("key","UTF-8")+"="+URLEncoder.encode(apiKey,"UTF-8");
                    Log.i("status","string post_data concatenation successful");
                    Log.i("post_data",post_data);

                    bufferedWriter.write(post_data);
                    Log.i("status","bufferedWriter.write(post_data) executed successfully");

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    //reading response for feedback
                    Log.i("status","now reading feedback");


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result = "";
                    String line  = "";
                    while ((line = bufferedReader.readLine())!=null){
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "neterror";
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("LoginStatus");
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            str = s;
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                Log.i("Exception","e");
            }
        }

        public BackgroundWorker(Context ctx) {
            context = ctx;
        }
    }
}
