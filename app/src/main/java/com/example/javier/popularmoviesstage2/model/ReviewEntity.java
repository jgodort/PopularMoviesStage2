package com.example.javier.popularmoviesstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 11/06/2016.
 */

public class ReviewEntity implements Parcelable {

    private String id;
    private String author;
    private String content;

    /**
     * Minimal Constructor
     *
     * @param id
     */
    public ReviewEntity(String id) {
        this.id = id;
    }

    public ReviewEntity(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public static final Parcelable.Creator<ReviewEntity> CREATOR
            = new Parcelable.Creator<ReviewEntity>() {
        public ReviewEntity createFromParcel(Parcel in) {
            return new ReviewEntity(in);
        }

        public ReviewEntity[] newArray(int size) {
            return new ReviewEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    private ReviewEntity(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
