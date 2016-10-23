package com.libarab.michaelg.libarab.Trivia;

/**
 * Created by Pcp on 02/10/2016.
 */

public class ItemsQ {
    private String author;
    private String itemName;
    private String creationDate;
    private String realAuther;
    private String publisher;
    private String urlImg;


    public ItemsQ(String author, String itemName,String creationDate,String realAuther,String publisher,String urlImg) {
        this.author = author;
        this.itemName = itemName;
        this.creationDate = creationDate;
        this.realAuther = realAuther;
        this.publisher = publisher;
        this.urlImg = urlImg;
    }
    public ItemsQ() {
        // TODO Auto-generated constructor stub
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getcreationDate() {
        return creationDate;
    }

    public void setcreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public String getrealAuther() {
        return realAuther;
    }

    public void setrealAuther(String realAuther) {
        this.realAuther = realAuther;
    }


    public String getpublisher() {
        return publisher;
    }

    public void setpublisher(String publisher) {
        this.publisher = publisher;
    }


    public String geturlImg() {
        return urlImg;
    }

    public void seturlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
