package com.artembashtovyi.mywordlist;

import android.content.Context;

import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by felix on 12/22/17
 */

public class BaseLoader<T extends Presenter> extends Loader<T> {
    private static final String TAG = "BaseLoader";
    private T presenter;


    public BaseLoader(Context context,T presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        Log.i(TAG, "onStartLoading" );
        deliverResult(presenter);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        Log.i(TAG, "onReset");
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }

    public T getPresenter() {
        return presenter;
    }
}
