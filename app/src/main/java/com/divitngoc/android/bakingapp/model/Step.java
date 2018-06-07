package com.divitngoc.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoUrlPath;
    private String thumbnailUrlPath;

    public Step(int id, String shortDescription, String description, String videoUrlPath, String thumbnailUrlPath) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrlPath = videoUrlPath;
        this.thumbnailUrlPath = thumbnailUrlPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoUrlPath);
        parcel.writeString(thumbnailUrlPath);
    }

    public Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrlPath = in.readString();
        thumbnailUrlPath = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }

    };

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrlPath() {
        return videoUrlPath;
    }

    public String getThumbnailUrlPath() {
        return thumbnailUrlPath;
    }
}
