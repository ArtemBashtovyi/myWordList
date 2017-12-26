package com.artembashtovyi.mywordlist;

/**
 * Created by felix on 12/22/17
 */


// Base presenter for different Views
public interface Presenter<V> {

    void onViewAttached(V view);
    void onViewDetached();
    void onDestroy();

}
