package com.com.jnu.recycleview.data;

import java.io.Serializable;

public class Book implements Serializable {//对象序列化

    private String title;//书名
    private int coverResourceId;//书的图片
    private String author;//作者
    private String translator;//译者
    private String publisher;//出版社
    private String pubTime;//出版时间
    private String isbn;//ISBN编号
    private boolean hasCover;//是否有封面
    private String notes;//笔记
    private String website;//网站

    public Book(String title, int coverResourceId,String author, String translator, String publisher, String pubTime, String isbn, boolean hasCover, String notes, String website) {
        this.title = title;
        this.coverResourceId = coverResourceId;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.pubTime = pubTime;
        this.isbn = isbn;
        this.hasCover = hasCover;
        this.notes = notes;
        this.website = website;
    }
    //没写阅读状态、标签、书架

    //get和set函数
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}