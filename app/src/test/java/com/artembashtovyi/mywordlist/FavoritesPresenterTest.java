package com.artembashtovyi.mywordlist;

import android.content.Context;

import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesPresenter;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by felix on 1/5/18
 */

public class FavoritesPresenterTest {

    FavoritesPresenter presenter;

    @Mock
    WordRepository repository;

    @Mock
    FavoritesView view;

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
        when(repository.getFavorites()).thenReturn(initRule.getFavoriteTestWords());

        presenter.loadFavorites();

        verify(view,atLeastOnce()).showLoading();
        verify(view,atLeastOnce()).showFavorites(initRule.getFavoriteTestWords());
        verify(view,atLeastOnce()).hideLoading();
    }




}
