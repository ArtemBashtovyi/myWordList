package com.artembashtovyi.mywordlist.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;

import com.artembashtovyi.mywordlist.R;

/**
 * Created by felix on 12/28/17
 */

public class SortDialog extends DialogFragment {
    private static final String KEY_LISTENER = "listener" ;

    public interface SortListener extends Parcelable {
        void onSortTypeClick(int id);
    }

    private SortDialog.SortListener listener;

    public static SortDialog newInstance(SortDialog.SortListener listener) {
        SortDialog frag = new SortDialog();
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

        builder.setTitle(R.string.pick_sort_type)
                .setSingleChoiceItems(R.array.sort_types,0, (dialogInterface, i) -> listener.onSortTypeClick(i))
                .setPositiveButton(R.string.ok, (dialog, id) -> SortDialog.this.getDialog().cancel());

        return builder.create();
    }
}
