package com.joonhuiwong.mylibrary.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class BookEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String author;
    private int pages;
    private String imageUrl;
    private String shortDesc;
    private String longDesc;

    private boolean isExpanded;

    private boolean isCurrentlyReading;
    private boolean isAlreadyRead;
    private boolean isWantToRead;
    private boolean isFavorite;

    public BookEntity(String name, String author, int pages, String imageUrl, String shortDesc, String longDesc) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;

        this.isExpanded = false;

        this.isCurrentlyReading = false;
        this.isAlreadyRead = false;
        this.isWantToRead = false;
        this.isFavorite = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isCurrentlyReading() {
        return isCurrentlyReading;
    }

    public void setCurrentlyReading(boolean currentlyReading) {
        isCurrentlyReading = currentlyReading;
    }

    public boolean isAlreadyRead() {
        return isAlreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        isAlreadyRead = alreadyRead;
    }

    public boolean isWantToRead() {
        return isWantToRead;
    }

    public void setWantToRead(boolean wantToRead) {
        isWantToRead = wantToRead;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", imageUrl='" + imageUrl + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", isExpanded=" + isExpanded +
                ", isCurrentlyReading=" + isCurrentlyReading +
                ", isAlreadyRead=" + isAlreadyRead +
                ", isWantToRead=" + isWantToRead +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
