package com.artembashtovyi.mywordlist.ui.list.addingDialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;

public class WordAddDialog extends DialogFragment {

    public interface NoticeDialogListener{
        void onPositiveClick(Word word);

    }

    NoticeDialogListener mListener;

    public static WordAddDialog newInstance() {
        return new WordAddDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // TODO : IMPL CHECK EMPTY FIELDS
        View myLayout = inflater.inflate(R.layout.fragment_dialog_adding_word, null);
        builder.setView(myLayout)
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    EditText engVersionEt = myLayout.findViewById(R.id.engVersion);
                    EditText uaVersionEt = myLayout.findViewById(R.id.uaVersion);

                    Word word = new Word();
                    word.setEngVersion(engVersionEt.getText().toString());
                    word.setUaVersion(uaVersionEt.getText().toString());

                    try {
                        mListener.onPositiveClick(word);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                })
                .setNegativeButton("Cancel", (dialog, id) -> WordAddDialog.this.getDialog().cancel());
        return builder.show();
    }

}
