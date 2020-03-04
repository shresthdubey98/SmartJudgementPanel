package com.example.amitycodingclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amitycodingclub.adapters_and_models.Student;
import com.example.amitycodingclub.adapters_and_models.StudentAdapter;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ParticipantActivity extends AppCompatActivity {
    Toolbar toolbar;
    View logo_back;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Student> students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        //toolbar stuff
        toolbar = findViewById(R.id.buySell_toolbar);
        logo_back = toolbar.getChildAt(1);
        logo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        students = new ArrayList<>();

        recyclerView = findViewById(R.id.homeRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    private void getData() {
        //data generation
        FetchStudentsList fetchStudentsList = new FetchStudentsList(getApplication());
        fetchStudentsList.execute("getusers");
    }

    public class FetchStudentsList extends AsyncTask<String, String, String> {
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
            //TODO:fetch list and update to recycler view

            try{

                String user_id,user_name,email,image;
                JSONArray jsonArray= new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    user_id = jsonObj.getString("user_id");
                    user_name = jsonObj.getString("user_name");
                    email = jsonObj.getString("email");
                    image = jsonObj.getString("image");
                    students.add(new Student(user_id,user_name,email,image,false));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StudentAdapter studentAdapter = new StudentAdapter(students,context);
                        recyclerView.setAdapter(studentAdapter);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public FetchStudentsList(Context ctx) {
            context = ctx;
        }
    }

}
