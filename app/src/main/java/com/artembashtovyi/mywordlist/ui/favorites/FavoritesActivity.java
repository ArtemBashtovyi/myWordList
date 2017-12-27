package com.artembashtovyi.mywordlist.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.artembashtovyi.mywordlist.BaseActivity;
import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.favorites.adapter.FavoriteAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends BaseActivity<FavoritesPresenter,FavoritesView> implements
        FavoriteAdapter.OnWordClickListener,FavoritesView {

    private final static int LOADER_ID = 1010;
    private static final String TAG = "FavoritesActivity" ;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.word_favorites_recycler_view)
    RecyclerView favoritesRv;

    private FavoritesPresenter presenter;
    private FavoriteAdapter adapter;


    public static void start(Context context) {
        Intent intent = new Intent(context,FavoritesActivity.class);
        context.startActivity(intent);
    }

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
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart" );
        presenter.loadFavorites();
    }

    // removeClick
    @Override
    public void clickCallBack(Word word) {
        presenter.removeFavorite(word);
    }

    @Override
    public void showFavorites(List<Word> favorites) {
        adapter = new FavoriteAdapter(favorites,this,this);
        favoritesRv.setAdapter(adapter);
    }

    // better when unique
    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

    @Override
    public FavoritesPresenter getInitedPresenter(WordRepository repository) {
        presenter = new FavoritesPresenter(this,repository);
        return presenter;
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull FavoritesPresenter presenter) {
        Log.i(TAG, "onPresenterCreatedOrRestored-" );
        this.presenter = presenter;
    }
}
