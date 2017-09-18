package com.example.priya.chatbot;



public class Messages {


    String message;
    String author;
    String cur_user;

    public Messages(String message,String author,String cur_user){
        this.author=author;
        this.message=message;
        this.cur_user=cur_user;
    }

    public String getCur_user() {
        return cur_user;
    }

    public void setCur_user(String cur_user) {
        this.cur_user = cur_user;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }




    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
