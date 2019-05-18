package com.example.labwork3;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentNew implements Parcelable{
    private int id;
    private String surname, name, patronymic ;

    public StudentNew(Parcel parcel){
        id = parcel.readInt();
        surname = parcel.readString();
        name = parcel.readString();
        patronymic = parcel.readString();
    }

    public StudentNew(int id, String surname, String name, String patronymic){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(surname);
        dest.writeString(name);
        dest.writeString(patronymic);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StudentNew createFromParcel(Parcel in) {
            return new StudentNew(in);
        }

        public StudentNew[] newArray(int size) {
            return new StudentNew[size];
        }
    };
}
