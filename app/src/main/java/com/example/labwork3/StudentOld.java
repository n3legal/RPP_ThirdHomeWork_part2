package com.example.labwork3;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentOld implements Parcelable {
    private int id;
    private String FIO;

    public StudentOld(Parcel parcel){
        id = parcel.readInt();
        FIO = parcel.readString();
    }

    public StudentOld(int id, String FIO){
        this.id = id;
        this.FIO = FIO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(FIO);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StudentNew createFromParcel(Parcel in) {
            return new StudentNew(in);
        }

        public StudentNew[] newArray(int size) {
            return new StudentNew[size];
        }
    };

    @Override
    public String toString() {
        return "\nID = " + id + " | FIO = " + FIO;
    }
}
