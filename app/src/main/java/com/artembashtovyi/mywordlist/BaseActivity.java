package com.artembashtovyi.mywordlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.async.AppExecutors;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;

import java.util.concurrent.Executor;

/**
 * Created by felix on 12/26/17
 *
 */

public abstract class BaseActivity<P extends Presenter<V>,V> extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<P> {

    private static final String TAG = "BaseActivity";
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Loader<P> loader = getSupportLoaderManager().getLoader(getLoaderId());

        if (loader == null) {
            getSupportLoaderManager().initLoader(getLoaderId(), null, this);
        } else {

            presenter = ((BaseLoader<P>) loader).getPresenter();
            // delivery presenter to OnStart() method descendants
            onPresenterCreatedOrRestored(presenter);
       }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart" );
        presenter.onViewAttached(getPresenterView());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop" );
        presenter.onViewDetached();
    }



    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreteLoader" );
        DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());
        Executor diskIOExecutor = AppExecutors.getInstance().getDiskExecutor();
        Executor mainThreadExecutor = AppExecutors.getInstance().getMainThreadExecutor();
        WordRepositoryImpl wordRepository = WordRepositoryImpl.getInstance(mainThreadExecutor,diskIOExecutor,dbHelper);

        // set initialized presenter with repository
        return new BaseLoader<>(BaseActivity.this, getInitedPresenter(wordRepository));
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        Log.i(TAG, "onLoadFinished" );
        presenter = data;
        onPresenterCreatedOrRestored(data);
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        BaseActivity.this.presenter = null;
    }


    // various implementation for each activity
    public abstract int getLoaderId();

    // hack with initialized repository and database helper
    public abstract P getInitedPresenter(WordRepositoryImpl repository);

    // when data will be reload (presenter)
    protected abstract void onPresenterCreatedOrRestored(@NonNull P presenter);

    // get current View
    @NonNull
    protected V getPresenterView() {
        return (V) this;
    }
}
