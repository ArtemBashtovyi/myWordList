package com.artembashtovyi.mywordlist.data;


import android.util.Log;

import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.ArrayList;
import java.util.List;
// imlp Word DTO
public class WordRepository implements RepositoryContract<Word> {


    // emulate DAO entity
    private List<Word> words = null;

    {
        words = new ArrayList<>();
       /* words.add(new Word("ought","повинен"));
        words.add(new Word("determine","визначати"));
        words.add(new Word("unfortunately","на жаль"));*/
    }


    // emulate database
    @Override
    public List<Word> getWords() {
        Log.i("Repository","getWords");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return words;
    }

}
