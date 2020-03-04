package com.example.amitycodingclub.adapters_and_models;

public class Student {
    private String name;
    private String email;
    private String url;
    private String id;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }



    public Student(String id,String name, String email, String url, boolean isChecked) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.url = url;
        this.isChecked = isChecked;
    }
}
