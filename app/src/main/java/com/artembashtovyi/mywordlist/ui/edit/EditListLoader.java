package com.artembashtovyi.mywordlist.ui.edit;



import android.content.Context;
import com.artembashtovyi.mywordlist.BaseLoader;


public class EditListLoader extends BaseLoader<EditListPresenter> {

    private EditListPresenter presenter;

    public EditListLoader(Context context,
                          EditListPresenter presenter) {
        super(context,presenter);
        this.presenter = presenter;
    }

    public EditListPresenter getPresenter() {
        return presenter;
    }
}
