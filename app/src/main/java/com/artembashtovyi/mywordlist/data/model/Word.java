package com.artembashtovyi.mywordlist.data.model;


public class Word {

    private int id;
    private String engVersion;
    private String uaVersion;

    public Word(int id,  String engVersion,  String uaVersion) {
        this.id = id;
        this.engVersion = engVersion;
        this.uaVersion = uaVersion;
    }

    public Word() {
    }


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
}
