package com.artembashtovyi.mywordlist.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.ui.adapter.EngVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.FullVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.UaVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.list.dialog.WordDescriptionDialog;

/**
 * Created by felix on 12/28/17
 */

public class ViewChoiceDialog extends DialogFragment {

    private static final String KEY_LISTENER = "listener" ;

    public interface ChoiceListener extends Parcelable {
        void onViewContractClick(ViewBindContract contract);
    }

    private ChoiceListener listener;
    private ViewBindContract viewBindContract = new FullVersionView();

    public static ViewChoiceDialog newInstance(ViewChoiceDialog.ChoiceListener listener) {
        ViewChoiceDialog frag = new ViewChoiceDialog();
        Bundle args = new Bundle();
        args.putParcelable(KEY_LISTENER,listener);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = getArguments().getParcelable(KEY_LISTENER);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.pick_toppings)
                .setSingleChoiceItems(R.array.toppings,0, (dialogInterface, i) -> {
                    switch (i) {
                        case 0 :
                            viewBindContract = new EngVersionView();
                            break;
                        case 1 :
                            viewBindContract = new UaVersionView();
                            break;
                        case 2 :
                            viewBindContract = new FullVersionView();
                            break;
                    }
                })
                .setPositiveButton(R.string.ok, (dialog, id) -> listener.onViewContractClick(viewBindContract))
                .setNegativeButton(R.string.cancel, (dialog, id) -> ViewChoiceDialog.this.getDialog().cancel());

        return builder.create();
    }

}
