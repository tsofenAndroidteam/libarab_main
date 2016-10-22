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


    public ItemsQ(String author, String itemName) {
        this.author = author;
        this.itemName = itemName;
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
}
