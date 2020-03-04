package com.example.amitycodingclub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amitycodingclub.adapters_and_models.Student;
import com.example.amitycodingclub.adapters_and_models.StudentAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GudgePanelHome extends AppCompatActivity {
    private ImageView takePictureButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    TextView capture_text;
    ProgressBar progressBar;
    ImageView logout;
    TextView judgeName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudge_panel_home);

        judgeName = findViewById(R.id.judge_name);
        //change status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cardview_dark_background));

        logout = findViewById(R.id.judge_logout);
        capture_text = findViewById(R.id.capture_text);
        progressBar = findViewById(R.id.capture_image_progressbar);
        takePictureButton = findViewById(R.id.capturebtn);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
//                Intent i = new Intent(getApplication(),Activity2.class);
//                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants constants = new Constants(getApplicationContext());
                constants.logout();

            }
        });
        Constants constants = new Constants(this);
        judgeName.setText(constants.getDisplay_name());
    }


    public class FetchDetails extends AsyncTask<Object, String, String> {
        Context context;
        private Constants constants;

        @Override
        protected String doInBackground(Object... params) {
            String type = (String)params[0];
            constants = new Constants(context);
            String fetchURL = "https://acc.amityaump.com/WDCResAPI.php/";

            try{
                // Connect to the web server endpoint
                File imgfile = (File)params[1];
                URL serverUrl = new URL(fetchURL);
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                String boundaryString = "----SomeRandomText";
                File logFileToUpload = imgfile;

                // Indicate that we want to write to the HTTP request body
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("connection","keep-alive");
                urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);

                OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
                BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));
                // Include the key
                //httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
//                String POST_PARAMS = "key=jaguy75cvi7357vug354wzq45836876&^&%^";
//                httpRequestBodyWriter.write(POST_PARAMS);

                httpRequestBodyWriter.write("--" + boundaryString + "\r\n");
                httpRequestBodyWriter.write("Content-Disposition: form-data; name=" + "key" + "\r\n");
                httpRequestBodyWriter.write("Content-Type: text/plain\r\n\r\n");
                httpRequestBodyWriter.write("6464654546864365544" + "\r\n");
                httpRequestBodyWriter.flush();

                // Include value from the myFileDescription text area in the post data
                httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
                httpRequestBodyWriter.write("jaguy75cvi7357vug354wzq45836876&^&%^");
                httpRequestBodyWriter.write("\n\n");
                httpRequestBodyWriter.write("Log file for 20150208");

                // Include the section to describe the file
                httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
                httpRequestBodyWriter.write("Content-Disposition: form-data;"
                        + "name=\"image\";"
                        + "filename=\""+ logFileToUpload.getName() +"\""
                        + "\nContent-Type: text/plain\n\n");
                //String post_data = URLEncoder.encode("key","UTF-8")+"="+URLEncoder.encode("jaguy75cvi7357vug354wzq45836876&^&%^","UTF-8");
                //httpRequestBodyWriter.write(post_data);


                httpRequestBodyWriter.flush();
                // Write the actual file contents
                FileInputStream inputStreamToLogFile = new FileInputStream(logFileToUpload);

                int bytesRead;
                byte[] dataBuffer = new byte[1024];
                while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
                    outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
                }
                outputStreamToRequestBody.flush();

                // Mark the end of the multipart http request
                httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
                httpRequestBodyWriter.flush();

                // Close the streams
                outputStreamToRequestBody.close();
                httpRequestBodyWriter.close();

                // Read response from web server, which will trigger the multipart HTTP request to be sent.

                Log.i("status","now reading feedback");
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line  = "";
                while ((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
                return result;
            }catch(Exception e){

            }

//            if (type.equals("get")){
//                try {
//                    //todo:change this ->String apiKey = constants.getApiKey();
//                    String base64img = strings[1];
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
//                    String post_data = URLEncoder.encode("key","UTF-8")+"="+URLEncoder.encode("jaguy75cvi7357vug354wzq45836876&^&%^","UTF-8")+"&"
//                            +URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(base64img,"UTF-8");
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
            return "neterror";
        }

        @Override
        protected void onPreExecute() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    capture_text.setText("Processing");
                    capture_text.setTextColor(getResources().getColor(R.color.colorAccent));
                    takePictureButton.setVisibility(View.GONE);
                }
            });
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            //TODO:fetch list and update to recycler view
            System.out.println("sourcecode = "+s);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    capture_text.setText("Capture Image");
                    capture_text.setTextColor(Color.parseColor("#000000"));
                    takePictureButton.setVisibility(View.VISIBLE);
                }
            });

            boolean fetchSuccess = s.contains("Email");
            if (fetchSuccess) {
                //alertDialog.setMessage("Login Successful!");
                if(true) {
                    try {
                        JSONObject jsonObj = new JSONObject(s);

                        String contestentEmail = jsonObj.getString("Email");
                        String name = jsonObj.getString("Name");
                        String program = jsonObj.getString("Program");
                        String session = jsonObj.getString("Session");
                        String urn = jsonObj.getString("Urn");
                        String image =jsonObj.getString("Image");
                        String phone =jsonObj.getString("Phone");
                        String Selected_Competition_Type =jsonObj.getString("Selected_Competition_Type");
                        String Title =jsonObj.getString("Title");
                        String team = jsonObj.getString("Team");
                        String judge1 =jsonObj.getString("Judge1");
                        String judge2 =jsonObj.getString("Judge2");
                        String judge3 =jsonObj.getString("Judge3");
                        String judge4 =jsonObj.getString("Judge4");
                        String judge5 =jsonObj.getString("Judge5");

                        System.out.println("name:" + name);
                        boolean canJudge = false;

                        if(constants.getUser_login().equals("judge1")){
                            if(judge1.equals("null")){
                                canJudge = true;
                            }else{
                                canJudge = false;
                            }
                        }else if(constants.getUser_login().equals("judge2")){
                            if(judge2.equals("null")){
                                canJudge = true;
                            }else{
                                canJudge = false;
                            }
                        }else if(constants.getUser_login().equals("judge3")){
                            if(judge3.equals("null")){
                                canJudge = true;
                            }else{
                                canJudge = false;
                            }
                        }else if(constants.getUser_login().equals("judge4")){
                            if(judge4.equals("null")){
                                canJudge = true;
                            }else{
                                canJudge = false;
                            }
                        }else if(constants.getUser_login().equals("judge5")){
                            if(judge5.equals("null")){
                                canJudge = true;
                            }else{
                                canJudge = false;
                            }
                        }
                        if(canJudge){
                            Intent i = new Intent(context, Activity2.class);
                            i.putExtra("contestentEmail",contestentEmail);
                            i.putExtra("name",name);
                            i.putExtra("program",program);
                            i.putExtra("session",session);
                            i.putExtra("urn",urn);
                            i.putExtra("image",image);
                            i.putExtra("phone",phone);
                            i.putExtra("Selected_Competition_Type",Selected_Competition_Type);
                            i.putExtra("Title",Title);
                            i.putExtra("team",team);
                            startActivity(i);
                        }else{
                            Toast.makeText(context, "Judgement already submitted", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                //todo:apply settings of when the non registerd user is taken image of

            }
        }
        public FetchDetails(Context ctx) {
            context = ctx;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            File imgFile = bitmapToJpeg(imageBitmap);

            FetchDetails fetchDetails = new FetchDetails(this);
            fetchDetails.execute("get",imgFile);
            //takePictureButton.setImageBitmap(imageBitmap);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
//            Toast.makeText(this, "permission not granted!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public File bitmapToJpeg(Bitmap bitmap){
        File file = new File(this.getCacheDir(),"img.jpeg");
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }
}
