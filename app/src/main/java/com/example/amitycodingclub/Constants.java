package com.example.amitycodingclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Constants {
    SharedPreferences sharedPreferences;
    Context context;

    private String ID = "";
    private String user_login="";
    private String user_nicename="";
    private String user_email="";
    private String user_url="";
    private String user_registered=""; //date and time of registration
    private String user_activation_key="";
    private String user_status="";
    private String display_name="";
    private String spam="";
    private String deleted="";
    private boolean subscriber=false;
    private String cap_key="";
    private String roles="";
    private boolean read=false;
    private boolean level_0=false;
    private boolean woffice_read_wikies=false;
    private String filter="";
    private String apiKey = "f20031a4af1f95cc7c30bb81c4ae4f3b";
    private String token = "";
    private boolean login = false;

    public void logout(){
        sharedPreferences.edit().putString("ID","").commit();
        sharedPreferences.edit().putString("user_login","").commit();
        sharedPreferences.edit().putString("user_nicename","").commit();
        sharedPreferences.edit().putString("user_email","").commit();
        sharedPreferences.edit().putString("user_url","").commit();
        sharedPreferences.edit().putString("user_registered","").commit();
        sharedPreferences.edit().putString("user_activation_key","").commit();
        sharedPreferences.edit().putString("user_status","").commit();
        sharedPreferences.edit().putString("display_name","").commit();
        sharedPreferences.edit().putString("spam","").commit();
        sharedPreferences.edit().putString("deleted","").commit();
        sharedPreferences.edit().putString("subscriber","").commit();
        sharedPreferences.edit().putString("cap_key","").commit();
        sharedPreferences.edit().putString("roles","").commit();
        sharedPreferences.edit().putString("read","").commit();
        sharedPreferences.edit().putString("level_0","").commit();
        sharedPreferences.edit().putString("woffice_read_wikies","").commit();
        sharedPreferences.edit().putString("filter","").commit();
        sharedPreferences.edit().putString("token","").commit();
        sharedPreferences.edit().putString("login","").commit();
        Intent i = new Intent(context,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getToken() {
        token = sharedPreferences.getString("token","");
        return token;
    }

    public boolean isLogin() {
        if(sharedPreferences.getString("login","").equals("true")){
            login = true;
        }
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
        if(login){
            sharedPreferences.edit().putString("login","true").commit();
        }
    }

    public void setToken(String token) {
        this.token = token;
        sharedPreferences.edit().putString("token",token).commit();

    }

    public String getApiKey() {
        return apiKey;
    }

    public String getID() {
        ID = sharedPreferences.getString("ID","");
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
        sharedPreferences.edit().putString("ID",ID).commit();

    }

    public String getUser_login() {
        user_login = sharedPreferences.getString("user_login","");
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
        sharedPreferences.edit().putString("user_login",user_login).commit();
    }

    public String getUser_nicename() {
        user_nicename = sharedPreferences.getString("user_nicename","");
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
        sharedPreferences.edit().putString("user_nicename",user_nicename).commit();
    }

    public String getUser_email() {
        user_email = sharedPreferences.getString("user_email","");
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
        sharedPreferences.edit().putString("user_email",user_email).commit();
    }

    public String getUser_url() {
        user_url = sharedPreferences.getString("user_url","");
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
        sharedPreferences.edit().putString("user_url",user_url).commit();

    }

    public String getUser_registered() {
        user_registered = sharedPreferences.getString("user_registered","");
        return user_registered;
    }

    public void setUser_registered(String user_user_registered) {
        this.user_registered = user_user_registered;
        sharedPreferences.edit().putString("user_registered",user_registered).commit();
    }

    public String getUser_activation_key() {
        user_activation_key = sharedPreferences.getString("user_activation_key","");
        return user_activation_key;
    }

    public void setUser_activation_key(String user_activation_key) {
        this.user_activation_key = user_activation_key;
        sharedPreferences.edit().putString("user_activation_key",user_activation_key).commit();
    }

    public String getUser_status() {
        user_status = sharedPreferences.getString("user_status","");
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
        sharedPreferences.edit().putString("user_status",user_status).commit();
    }

    public String getDisplay_name() {
        display_name = sharedPreferences.getString("display_name","");
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
        sharedPreferences.edit().putString("display_name",display_name).commit(); 
    }

    public String getSpam() {
        spam = sharedPreferences.getString("spam","");
        return spam;
    }

    public void setSpam(String spam) {
        this.spam = spam;
        sharedPreferences.edit().putString("spam",spam).commit();

    }

    public String getDeleted() {
        deleted = sharedPreferences.getString("deleted","");

        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
        sharedPreferences.edit().putString("deleted",deleted).commit();

    }

    public boolean isSubscriber() {
        try {
            return sharedPreferences.getString("subscriber", "").equals("true");
        }catch (Exception e){
            return false;
        }
    }

    public void setSubscriber(boolean subscriber) {
        this.subscriber = subscriber;
        if(subscriber) {
            sharedPreferences.edit().putString("subscriber", "true").commit();
            sharedPreferences.edit().putString("subscriber", "true").commit();
        }else{
            sharedPreferences.edit().putString("subscriber", "false").commit();
        }
    }

    public String getCap_key() {
        cap_key = sharedPreferences.getString("cap_key","");
        return cap_key;
    }

    public void setCap_key(String cap_key) {
        this.cap_key = cap_key;
        sharedPreferences.edit().putString("cap_key",cap_key).commit();
    }

    public String getRoles() {
        roles = sharedPreferences.getString("roles","");
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
        sharedPreferences.edit().putString("roles",roles).commit();

    }

    public boolean isRead() {
        try {
            return sharedPreferences.getString("read ", "").equals("true");
        }catch (Exception e){
           return false; 
        }
    }

    public void setRead(boolean read) {
        this.read = read;
        if(read) {
            sharedPreferences.edit().putString("read", "true").commit();
        }else{
            sharedPreferences.edit().putString("read", "false").commit();
        }
    }
    public boolean isLevel_0() {
        try{
            return sharedPreferences.getString("level_0 ", "").equals("true");
        }catch (Exception e){
            return false;
        }
    }

    public void setLevel_0(boolean level_0) {
        this.level_0 = level_0;
        if(level_0) {
            sharedPreferences.edit().putString("level_0", "true").commit();
        }else{
            sharedPreferences.edit().putString("level_0", "false").commit();
        }
    }

    public boolean isWoffice_read_wikies() {
        try {
            return sharedPreferences.getString("woffice_read_wikies", "").equals("true");
        }catch (Exception e){
            return false;
        }
    }

    public void setWoffice_read_wikies(boolean woffice_read_wikies) {
        this.woffice_read_wikies = woffice_read_wikies;
        if(woffice_read_wikies) {
            sharedPreferences.edit().putString("woffice_read_wikies", "true").commit();
        }else{
            sharedPreferences.edit().putString("woffice_read_wikies", "false").commit();
        }
    }

    public String getFilter() {
        filter = sharedPreferences.getString("filter","null");
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        sharedPreferences.edit().putString("filter",filter).commit();

    }
    public Constants(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }
}
