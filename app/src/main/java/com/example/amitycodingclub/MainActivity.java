package com.example.amitycodingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {

    //General variable declaration.
    EditText etEmail,etPassward;
    Button btnLognin;
    TextView btnsignup,forgetPassword;
    ProgressBar progressBar;
    Constants constants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constants = new Constants(this);
        if(constants.isLogin()){
            if(constants.getRoles().contains("subscriber")) {
                Intent i = new Intent(this, GudgePanelHome.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.mainactivity_edittext_email);
        etPassward = findViewById(R.id.mainactivity_edittext_password);
        forgetPassword = findViewById(R.id.btnforgetpassword);
        btnLognin = findViewById(R.id.mainactivity_button_login);
        btnsignup = findViewById(R.id.mainactivity_button_signup);
        progressBar = findViewById(R.id.login_progressBar);
        btnLognin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:sign in request.
                BackgroundWorker backgroundWorker = new BackgroundWorker(getApplication());
                backgroundWorker.execute("login",etEmail.getText().toString(),etPassward.getText().toString());
            }
        });

    }
    public class BackgroundWorker extends AsyncTask<String, String, String> {
        Context context;
        AlertDialog alertDialog;
        String username = "",password = "";
        private Constants constants;
        private String ip;

        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            constants = new Constants(context);
            String login_url = "https://acc.amityaump.com/wp-json/app-gateway/login";

            if (type.equals("login")){
                try {
                    username = strings[1];
                    password = strings[2];
                    Log.i("status","inside the login try catch");
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();



                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    Log.i("status","Http url connection established properly");

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    Log.i("status","buffer writer working");

                    String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("key","UTF-8")+"="+URLEncoder.encode(constants.getApiKey(),"UTF-8");
                    Log.i("status","string post_data concatenation successful");

                    bufferedWriter.write(post_data);
                    Log.i("status","bufferedWriter.write(post_data) executed successfully");

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    String headerName = "";
                    String cookieValue = null;
                    for (int i = 1; (headerName = httpURLConnection.getHeaderFieldKey(i)) != null; i++)
                    {
                        if(headerName.equals("Set-Cookie"))
                        {
                            cookieValue = httpURLConnection.getHeaderField(i);
                        }
                    }                    //reading response for feedback
                    Log.i("cookie",cookieValue);
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setAlpha(1);
                }
            });
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("LoginStatus");
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                boolean loginSuccessful = s.contains("ID");
                boolean loginUnsuccessful = s.contains("incorrect_password");
                boolean neterror = s.contains("neterror");


                if (loginSuccessful) {
                    //alertDialog.setMessage("Login Successful!");
                    try {
                        JSONObject jsonObj = new JSONObject(s);

                        JSONObject data_json = new JSONObject(jsonObj.getString("data"));
                        constants.setID(data_json.getString("ID"));
                        constants.setUser_login(data_json.getString("user_login"));
                        constants.setUser_nicename(data_json.getString("user_nicename"));
                        constants.setUser_email(data_json.getString("user_email"));
                        constants.setUser_url(data_json.getString("user_url"));
                        constants.setUser_registered(data_json.getString("user_registered"));
                        constants.setUser_activation_key(data_json.getString("user_activation_key"));
                        constants.setUser_status(data_json.getString("user_status"));
                        constants.setDisplay_name(data_json.getString("display_name"));
                        constants.setSpam(data_json.getString("spam"));
                        constants.setDeleted(data_json.getString("deleted"));


//                        JSONObject caps_json = new JSONObject(jsonObj.getString("caps"));
//                        if (caps_json.getString("subscriber").equals("true")){
//                            constants.setSubscriber(true);
//                        }else{
//                            constants.setSubscriber(false);
//                        }

                        constants.setRoles(jsonObj.getString("roles"));
                        System.out.println(jsonObj.getString("roles"));

                        JSONObject allcaps_json = new JSONObject(jsonObj.getString("allcaps"));
                        if (allcaps_json.getString("read").equals("true")){
                            constants.setRead(true);
                        }else{
                            constants.setRead(false);
                        }
                        if(allcaps_json.getString("level_0").equals("true")){
                            constants.setLevel_0(true);
                        }else{
                            constants.setLevel_0(false);
                        }
                        if(allcaps_json.getString("woffice_read_wikies").equals("true")){
                            constants.setLevel_0(false);
                        }
                        constants.setFilter(jsonObj.getString("filter"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    TokenGeter tokenGeter = new TokenGeter(context);
                    tokenGeter.execute("gettoken",username,password);
                    String finalString = "ID:"+constants.getID()+"\n" +
                            "user_login:"+constants.getUser_login()+"\n" +
                            "user_nicename:"+constants.getUser_nicename()+"\n" +
                            "user_email:"+constants.getUser_email()+"\n" +
                            "user_url:"+constants.getUser_url()+"\n" +
                            "user_registered:"+constants.getUser_registered()+"\n" +
                            "user_activation_key:"+constants.getUser_activation_key()+"\n" +
                            "user_status:"+constants.getUser_status()+"\n" +
                            "display_name:"+constants.getDisplay_name()+"\n" +
                            "spam:"+constants.getSpam()+"\n" +
                            "deleted:"+constants.getDeleted()+"\n" +
                            "roles:"+constants.getRoles();
                    Log.i("finla output",finalString);
                } else if (loginUnsuccessful) {
                    alertDialog.setMessage("Incorrect Password or username!");
                    alertDialog.show();
                    Log.i("info",s);
                } else if (neterror) {
                    Log.i("error",s);
                    alertDialog.setMessage("Network Error!");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.show();
                        }
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.i("Exception","e");
            }
        }

        public BackgroundWorker(Context ctx) {
            context = ctx;
        }
    }
    public class TokenGeter extends AsyncTask<String, String, String> {
        Context context;
        String username = "",password = "";
        Constants constants;
        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            constants = new Constants(context);
            String login_url = "https://acc.amityaump.com/wp-json/jwt-auth/v1/token?";

            if (type.equals("gettoken")){
                try {
                    username = strings[1];
                    password = strings[2];
                    Log.i("status","inside the login try catch");
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    Log.i("status","Http url connection established properly");

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    Log.i("status","buffer writer working");


                    String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("key","UTF-8")+"="+URLEncoder.encode(constants.getApiKey(),"UTF-8");
                    Log.i("status","string post_data concatenation successful");

                    bufferedWriter.write(post_data);
                    Log.i("status","bufferedWriter.write(post_data) executed successfully");

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    String headerName = "";
                    String cookieValue = null;
                    for (int i = 1; (headerName = httpURLConnection.getHeaderFieldKey(i)) != null; i++)
                    {
                        if(headerName.equals("Set-Cookie"))
                        {
                            cookieValue = httpURLConnection.getHeaderField(i);
                        }
                    }                    //reading response for feedback
                    Log.i("cookie",cookieValue);
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
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("token",s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setAlpha(0);
                }
            });
            try {
                boolean loginSuccessful = s.contains("token");
                if (loginSuccessful) {
                    //alertDialog.setMessage("Login Successful!");
                    try {
                        JSONObject jsonObj = new JSONObject(s);
                        constants.setToken(jsonObj.getString("token"));
                        Log.i("TOKEN",jsonObj.getString("token"));
                        constants.setLogin(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("ROLES:"+ constants.getRoles());
                    if(constants.getRoles().contains("um_wdc-admin")) {
                        Intent i = new Intent(context, GudgePanelHome.class);
                        //Intent i = new Intent(context,TestActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }else{
                        Toast.makeText(context, "Sorry! You not authorized.", Toast.LENGTH_SHORT).show();
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
                Log.i("Exception","e");
            }

        }

        public TokenGeter(Context ctx) {
            context = ctx;
        }
    }
}
