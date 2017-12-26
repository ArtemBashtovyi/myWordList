package com.artembashtovyi.mywordlist.data.model;


public class FavoriteWord extends Word {

    private boolean favoriteFlag;

    public FavoriteWord(Word word,boolean favoriteFlag) {
        super(word.getId(),word.getEngVersion(),word.getUaVersion());
        this.favoriteFlag = favoriteFlag;
    }

    public boolean isFavorite() {
        return favoriteFlag;
    }


    public void setFavorite(boolean favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }
}
