package com.artembashtovyi.mywordlist;

import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesPresenter;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by felix on 1/5/18
 */

public class FavoritesPresenterTest {

    FavoritesPresenter presenter;

    @Mock
    WordRepositoryImpl repository;

    @Mock
    FavoritesView view;

    @Mock
    WordCallbacks callback;

    @Rule
    public WordInitializationRule initRule = new WordInitializationRule();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenter = new FavoritesPresenter(view,repository);
        presenter.onViewAttached(view);
    }

    @Test
    public void getWordsTest() {
        //when(repository.getFavorites(callback)).thenReturn(initRule.getFavoriteTestWords());

        presenter.loadFavorites();

        verify(view,atLeastOnce()).showLoading();
        verify(view,atLeastOnce()).showFavorites(initRule.getFavoriteTestWords());
        verify(view,atLeastOnce()).hideLoading();
    }




}
