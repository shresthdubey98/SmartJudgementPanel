package com.example.amitycodingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    Button judgeBtn;
    TextView tv_name,tv_branch,tv_theme,tv_urn,tv_title,tv_email,tv_phone,tv_session;
    String contestentEmail ;
    String name ;
    String program;
    String session ;
    String urn ;
    String image;
    String phone;
    String Selected_Competition_Type;
    String title;
    String team;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        judgeBtn = findViewById(R.id.judgeBtn);

        Intent preIntent = getIntent();
        contestentEmail = preIntent.getStringExtra("contestentEmail");
        name = preIntent.getStringExtra("name");
        program = preIntent.getStringExtra("program");
        session = preIntent.getStringExtra("session");
        urn = preIntent.getStringExtra("urn");
        image =preIntent.getStringExtra("image");
        phone =preIntent.getStringExtra("phone");
        Selected_Competition_Type =preIntent.getStringExtra("Selected_Competition_Type");
        title =preIntent.getStringExtra("Title");
        team = preIntent.getStringExtra("team");
        tv_name = findViewById(R.id.tv_name);
        tv_branch = findViewById(R.id.tv_branch);
        tv_theme = findViewById(R.id.tv_theme);
        tv_urn = findViewById(R.id.tv_urn);
        tv_title = findViewById(R.id.tv_title);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        tv_session = findViewById(R.id.tv_session);


        tv_name.setText(name);
        tv_branch.setText(program);
        tv_theme.setText("Category:"+Selected_Competition_Type);
        tv_urn.setText(urn);
        tv_title.setText(title);
        tv_email.setText(contestentEmail);
        tv_phone.setText(phone);
        tv_session.setText(session);

        String htmlText = " %s ";
        String myData = "<html><img src='"+image+"' width='160' height='180'/></br></html>";
        WebView webView = findViewById(R.id.profile_web_view);
        webView.loadData(String.format(htmlText, myData), "text/html", "utf-8");

        judgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),ResultActivity.class);
                i.putExtra("urn",urn);
                i.putExtra("team",team);
                startActivity(i);
            }
        });
    }
}
