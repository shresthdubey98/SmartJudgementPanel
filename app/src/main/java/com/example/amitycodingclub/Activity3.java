//package com.example.amitycodingclub;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Activity3 extends AppCompatActivity {
//
//    //TextView t1,t2,t3,t4,t5,t6;
//    //SeekBar s1,s2,s3,s4,s5,s6;
//    SeekBar s[];
//    TextView t[];
//    public int i=0;
//    ImageButton imageButton;
//    Button submitButton;
//    String urn,key,username,result,team;
//    int arr[];
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_3);
//
//        submitButton = findViewById(R.id.submit_result);
//
//
//        Intent preIntent = getIntent();
//        urn = preIntent.getStringExtra("urn");
//        team = preIntent.getStringExtra("team");
//        key = "6464654546864365544";
//        arr = new int[7];
//        s = new SeekBar[6];
//        t = new TextView[6];
//        setContentView(R.layout.activity_3);
//        t[0] = findViewById(R.id.t1);
//        t[1] = findViewById(R.id.t2);
//        t[2] = findViewById(R.id.t3);
//        t[3] = findViewById(R.id.t4);
//        t[4] = findViewById(R.id.t5);
//        t[5] = findViewById(R.id.t6);
//        s[0] = findViewById(R.id.s1);
//        s[1] = findViewById(R.id.s2);
//        s[2] = findViewById(R.id.s3);
//        s[3] = findViewById(R.id.s4);
//        s[4] = findViewById(R.id.s5);
//        s[5] = findViewById(R.id.s6);
//
//
//
//        for(i =0;i<s.length;i++){
//            s[i].setMax(10);
//            s[i].setProgress(5);
//            t[i].setText("5");
//        }
//        s[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                t[0].setText(String.valueOf(seekBar.getProgress()));
////                Log.i("value",String.valueOf(seekBar.getProgress()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#008FF8"), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            }
//        });
//        s[1].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                t[1].setText(String.valueOf(seekBar.getProgress()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#008FF8"), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            }
//        });
//        s[2].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                t[2].setText(String.valueOf(seekBar.getProgress()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#008FF8"), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            }
//        });
//        s[3].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                t[3].setText(String.valueOf(seekBar.getProgress()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#008FF8"), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            }
//        });
//        s[4].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                t[4].setText(String.valueOf(seekBar.getProgress()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#008FF8"), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            }
//        });
//        s[5].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                t[5].setText(String.valueOf(seekBar.getProgress()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#008FF8"), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            }
//        });
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int tot = 0;
//                Log.i("something","something");
//                List<Integer> myList = new ArrayList<Integer>();
//                for(int i=0;i<s.length;i++){
//                    tot += s[i].getProgress();
//                    myList.add(s[i].getProgress());
//                }
//                Toast.makeText(Activity3.this, "button clicked", Toast.LENGTH_SHORT).show();
//                myList.add(tot);
//                System.out.println("\n\n\n"+myList+"\n\n\n");
//            }
//        });
//
//    }
//
//    public class FetchDetails extends AsyncTask<String, String, String> {
//        Context context;
//        private Constants constants;
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String type = strings[0];
//            constants = new Constants(context);
//            String fetchURL = "https://acc.amityaump.com/wp-json/app-gateway/result ";
//
//            if (type.equals("submit")){
//                try {
//                    String urn = strings[1];
//                    String res = strings[2];
//                    String team = strings[3];
//                    String key = strings[4];
//                    String username = constants.getUser_login();
//
//                    Log.i("status","inside the login try catch");
//                    URL url = new URL(fetchURL);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    //this is the auth token.
//                    //httpURLConnection.setRequestProperty("Authorization","Bearer "+constants.getToken());
//
//                    Log.i("status","Http url connection established properly");
//
//                    OutputStream outputStream = httpURLConnection.getOutputStream();
//
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    Log.i("status","buffer writer working");
//
//                    String post_data = URLEncoder.encode("urn","UTF-8")+"="+URLEncoder.encode(urn,"UTF-8")+"&"
//                            +URLEncoder.encode("res","UTF-8")+"="+URLEncoder.encode(res,"UTF-8")
//                            +URLEncoder.encode("key","UTF-8")+"="+URLEncoder.encode(key,"UTF-8")
//                            +URLEncoder.encode("team","UTF-8")+"="+URLEncoder.encode(team,"UTF-8")
//                            +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
//                            ;
//                    Log.i("status","string post_data concatenation successful");
//                    Log.i("post_data",post_data);
//
//                    bufferedWriter.write(post_data);
//                    Log.i("status","bufferedWriter.write(post_data) executed successfully");
//
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    outputStream.close();
//
//                    //reading response for feedback
//                    Log.i("status","now reading feedback");
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                    String result = "";
//                    String line  = "";
//                    while ((line = bufferedReader.readLine())!=null){
//                        result += line;
//                    }
//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//
//                    return result;
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return "neterror";
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            //TODO:fetch list and update to recycler view
//            if(s.contains("true")){
//                Toast.makeText(context, "Submit Successful", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(context, GudgePanelHome.class);
//                //Intent i = new Intent(context,TestActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }else{
//                Toast.makeText(context, "Submit Unsuccessful", Toast.LENGTH_SHORT).show();
//            }
//        }
//        public FetchDetails(Context ctx) {
//            context = ctx;
//        }
//    }
//
//}
