package com.artembashtovyi.mywordlist.data.model;
import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {

    private int id;
    private String engVersion;
    private String uaVersion;

    public Word(int id, String engVersion,  String uaVersion) {
        this.id = id;
        this.engVersion = engVersion;
        this.uaVersion = uaVersion;
    }

    public Word() {
    }


    protected Word(Parcel in) {
        id = in.readInt();
        engVersion = in.readString();
        uaVersion = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
    }

    public String getEngVersion() {
        return engVersion;
    }

    public void setEngVersion( String engVersion) {
        this.engVersion = engVersion;
    }

    public String getUaVersion() {
        return uaVersion;
    }

    public void setUaVersion( String uaVersion) {
        this.uaVersion = uaVersion;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id +
                "engVersion='" + engVersion + '\'' +
                ", uaVersion='" + uaVersion + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return engVersion.equals(word.engVersion) && uaVersion.equals(word.uaVersion);

    }

    @Override
    public int hashCode() {
        int result = engVersion.hashCode();
        result = 31 * result + uaVersion.hashCode() * 39;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(engVersion);
        parcel.writeString(uaVersion);
    }
}
