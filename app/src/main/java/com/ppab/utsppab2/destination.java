package com.ppab.utsppab2;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class destination implements Parcelable {
    private String city;

    private String state;

    private String country;

    private boolean visited;

    public destination(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.visited = false;
    }

    protected destination(Parcel in) {
        city = in.readString();
        state = in.readString();
        country = in.readString();
        visited = in.readByte() != 0;
    }

    public static final Creator<destination> CREATOR = new Creator<destination>() {
        @Override
        public destination createFromParcel(Parcel in) {
            return new destination(in);
        }

        @Override
        public destination[] newArray(int size) {
            return new destination[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void readFromParcel(Parcel in) {
        city = in.readString();
        state = in.readString();
        country = in.readString();
        visited = in.readBoolean();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeBoolean(visited);
    }
}
