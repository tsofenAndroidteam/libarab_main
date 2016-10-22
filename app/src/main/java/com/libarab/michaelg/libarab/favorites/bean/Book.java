package com.libarab.michaelg.libarab.favorites.bean;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HaPBoy on 5/14/16.
 */
public class Book implements Serializable {
    private int id;
    private String bookid;
    private String pagenum;
    private String description;
    private String title;
    private String author;
    private String publisher;
    private String creationDate;
    private String source;
    private String other;
    private String image;
    private ArrayList<Integer> pageList;

    public Book() {
        pageList = new ArrayList<Integer>();
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(ArrayList<Integer> pageList) {
        this.pageList = pageList;
    }
}
