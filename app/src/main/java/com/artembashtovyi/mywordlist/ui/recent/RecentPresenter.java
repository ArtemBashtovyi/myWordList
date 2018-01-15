package com.artembashtovyi.mywordlist.ui.recent;

import android.view.View;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.RecentWordsQuery;

import java.util.List;

/**
 * Created by felix on 12/28/17
 */

public class RecentPresenter implements Presenter<RecentView> {

    private WordRepository repository;
    private RecentView view;
    private List<Word> words;

    public RecentPresenter(WordRepository repository, RecentView view) {
        this.repository = repository;
        this.view = view;
    }

    void loadWords() {
        words = repository.getWords(new RecentWordsQuery(25));
        view.showWords(words);
    }


    @Override
    public void onViewAttached(RecentView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
    }

    @Override
    public void onDestroy() {

    }
}
