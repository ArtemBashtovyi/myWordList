package com.artembashtovyi.mywordlist.ui.edit.addingDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;


public class EditDialog extends DialogFragment {
    private static final String KEY = "Word";

    private Word oldWord;

    public interface  NoticeDialogListener {
        void onEditPositiveClick(Word oldWord,Word word);
    }

    EditDialog.NoticeDialogListener mListener;

    public static EditDialog newInstance(Word word) {
        EditDialog frag = new EditDialog();
        Bundle args = new Bundle();
        args.putParcelable(KEY, word);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oldWord = getArguments().getParcelable(KEY);

        try {
            mListener = (EditDialog.NoticeDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View myLayout = inflater.inflate(R.layout.fragment_dialog_adding_word, null);
        EditText engVersionEt = (EditText) myLayout.findViewById(R.id.engVersion);
        EditText uaVersionEt = (EditText) myLayout.findViewById(R.id.uaVersion);

        engVersionEt.setText(oldWord.getEngVersion());
        uaVersionEt.setText(oldWord.getUaVersion());

        builder.setView(myLayout)
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    Word word = new Word();
                    word.setEngVersion(engVersionEt.getText().toString());
                    word.setUaVersion(uaVersionEt.getText().toString());

                    try {
                        mListener.onEditPositiveClick(oldWord,word);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                })
                .setNegativeButton("Cancel", (dialog, id) -> EditDialog.this.getDialog().cancel());
        return builder.show();
    }

    private void showError() {

    }
}
