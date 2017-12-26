package com.artembashtovyi.mywordlist.ui.favorites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.favorites.adapter.FavoriteAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements
        FavoriteAdapter.OnWordClickListener,FavoritesView,LoaderManager.LoaderCallbacks<FavoritesPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.word_favorites_recycler_view)
    RecyclerView favoritesRv;


    private FavoritesPresenter presenter;
    private FavoriteAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        favoritesRv.setLayoutManager(llm);


    }




    @Override
    public void clickCallBack(Word word) {
        presenter.removeFavorite(word);
    }

    @Override
    public void showFavorites(List<Word> favorites) {
        adapter = new FavoriteAdapter(favorites,this,this);
        favoritesRv.setAdapter(adapter);
    }

    @Override
    public Loader<FavoritesPresenter> onCreateLoader(int id, Bundle args) {


        return null;
    }

    @Override
    public void onLoadFinished(Loader<FavoritesPresenter> loader, FavoritesPresenter data) {

    }

    @Override
    public void onLoaderReset(Loader<FavoritesPresenter> loader) {

    }
}
