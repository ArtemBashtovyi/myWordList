package com.artembashtovyi.mywordlist;


import android.os.AsyncTask;
import android.support.test.rule.UiThreadTestRule;

import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesPresenter;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by felix on 1/6/18
 */

public class FavoritePresenterTest {

    FavoritesPresenter presenter;

    @Mock
    WordRepositoryImpl repository;

    @Mock
    FavoritesView view;

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);


        presenter = new FavoritesPresenter(view,repository);
        presenter.onViewAttached(view);
    }



    @Test
    public void getWordsTest() throws Throwable {
        final Object syncObj = new Object();

            // final CountDownLatch signal = new CountDownLatch(1);
            presenter.onViewAttached(view);

            when(repository.getFavorites()).thenReturn(getFavoriteTestWords());

            synchronized (syncObj) {
                syncObj.wait();
            }

            uiThreadTestRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            presenter.loadFavorites();

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            syncObj.notify();
                        }
                    }.execute();


                }
            });



            //signal.await(10, TimeUnit.SECONDS);

            verify(view).showLoading();
            verify(view).hideLoading();
            verify(view).showFavorites(getFavoriteTestWords());


    }


    public List<Word> getFavoriteTestWords() {

        List<Word> words = new ArrayList<>();
        words.add(new Word(2,"availability","наявність"));
        words.add(new Word(3,"arrangement","організація"));
        words.add(new Word(5,"additionally","додатково"));

        return words;
    }

}
