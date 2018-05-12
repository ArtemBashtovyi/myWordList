package com.artembashtovyi.mywordlist.ui.recent;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.RecentWordsQuery;

import java.util.List;

/**
 * Created by felix on 12/28/17
 */

public class RecentWordsPresenter implements Presenter<RecentView> {

    private WordRepositoryImpl repository;
    private RecentView view;
    private List<Word> words;

    public RecentWordsPresenter(WordRepositoryImpl repository, RecentView view) {
        this.repository = repository;
        this.view = view;
    }

    void loadWords() {
        repository.getWords(words -> view.showWords(words), new RecentWordsQuery(30));
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
