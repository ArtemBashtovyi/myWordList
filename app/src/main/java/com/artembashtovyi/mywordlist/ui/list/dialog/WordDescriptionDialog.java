package com.artembashtovyi.mywordlist.ui.list.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;


/**
 * Created by felix on 12/25/17
 */

public class WordDescriptionDialog extends DialogFragment{


    private static final String KEY = "Word";
    private static final String KEY_FAVORITE = "Favorite";
    private static final String KEY_LISTENER = "Listener";

    private Word word;
    private boolean isFavorite;
    private DialogClickListener listener;


    public interface DialogClickListener extends Parcelable{
        boolean onImageFavoriteClick(Word word);
    }

    public static WordDescriptionDialog newInstance(Word word,boolean isFavorite,
                                                    DialogClickListener listener) {
        WordDescriptionDialog frag = new  WordDescriptionDialog();
        Bundle args = new Bundle();
        args.putParcelable(KEY, word);
        args.putBoolean(KEY_FAVORITE,isFavorite);
        args.putParcelable(KEY_LISTENER,listener);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getParcelable(KEY);
        isFavorite = getArguments().getBoolean(KEY_FAVORITE);
        listener = getArguments().getParcelable(KEY_LISTENER);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View myLayout = inflater.inflate(R.layout.fragment_dialog_word_description, null);

        ImageView imageView = (ImageView) myLayout.findViewById(R.id.favorite_status);
        TextView engVersionTv = (TextView) myLayout.findViewById(R.id.eng_version);
        TextView uaVersionTv = (TextView) myLayout.findViewById(R.id.ua_version);
        changeView(imageView,isFavorite);

        imageView.setOnClickListener(view -> {
            changeView(imageView,listener.onImageFavoriteClick(word));
        });

        Log.i("DescriptionDialogWord",word.toString() + " ");

        engVersionTv.setText(word.getEngVersion());
        uaVersionTv.setText(word.getUaVersion());

        builder.setView(myLayout)
                .setPositiveButton("OK", (dialog, id) ->  WordDescriptionDialog.this.getDialog().cancel());
        return builder.show();
    }

    private void changeView(ImageView imageView,boolean isFavorite) {
        if (isFavorite) {
            imageView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        } else {
            imageView.setBackgroundResource(R.drawable.ic_unfavorite_border_black_24dp);
        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }
}
