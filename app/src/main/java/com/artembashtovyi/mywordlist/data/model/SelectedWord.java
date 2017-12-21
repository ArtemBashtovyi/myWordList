package com.artembashtovyi.mywordlist.data.model;



public class SelectedWord extends Word{

    private boolean isSelected = false;

    public SelectedWord(Word word, boolean isSelected) {
        super(word.getId(),word.getEngVersion(), word.getUaVersion());
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
