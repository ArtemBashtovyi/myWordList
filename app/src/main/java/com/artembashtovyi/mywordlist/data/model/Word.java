package com.artembashtovyi.mywordlist.data.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

public class Word implements Parcelable {

    private int id;
    private String engVersion;
    private String uaVersion;
    private WordState state;
    private boolean isFavorite;

    public Word(int id, String engVersion, String uaVersion, WordState state, boolean isFavorite) {
        this.id = id;
        this.engVersion = engVersion;
        this.uaVersion = uaVersion;
        this.state = state;
        this.isFavorite = isFavorite;
    }

    public Word() {
    }

    protected Word(Parcel in) {
        id = in.readInt();
        engVersion = in.readString();
        uaVersion = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngVersion() {
        return engVersion;
    }

    public void setEngVersion(String engVersion) {
        this.engVersion = engVersion;
    }

    public String getUaVersion() {
        return uaVersion;
    }

    public void setUaVersion(String uaVersion) {
        this.uaVersion = uaVersion;
    }

    public WordState getState() {
        return state;
    }

    public void setState(WordState state) {
        this.state = state;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(engVersion);
        parcel.writeString(uaVersion);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (id != word.id) return false;
        if (isFavorite != word.isFavorite) return false;
        if (engVersion != null ? !engVersion.equals(word.engVersion) : word.engVersion != null)
            return false;
        if (uaVersion != null ? !uaVersion.equals(word.uaVersion) : word.uaVersion != null)
            return false;
        return state == word.state;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (engVersion != null ? engVersion.hashCode() : 0);
        result = 31 * result + (uaVersion != null ? uaVersion.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (isFavorite ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", engVersion='" + engVersion + '\'' +
                ", uaVersion='" + uaVersion + '\'' +
                ", state=" + state +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
