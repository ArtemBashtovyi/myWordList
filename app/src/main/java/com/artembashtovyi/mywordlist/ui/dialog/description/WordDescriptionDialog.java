package com.artembashtovyi.mywordlist.ui.dialog.description;

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

public class WordDescriptionDialog extends DialogFragment implements DescriptionDialogClickCallbacks {

    private static final String KEY = "Word";
    private static final String KEY_FAVORITE = "Favorite";
    private static final String KEY_LISTENER = "Listener";
    private static final String KEY_WORD_STATE = "State";

    private Word word;
    private boolean isFavorite;
    private DialogClickListener listener;
    private ImageView imageView;
    private ImageView redStateIv;
    private ImageView greenStateIv;
    private ImageView yellowStateIv;
    private WordState wordState;

    public interface DialogClickListener extends Parcelable {
        void onImageFavoriteClick(Word word);

        void onImageColoredClick(Word word,WordState wordState);
    }

    public static WordDescriptionDialog newInstance(Word word,boolean isFavorite,
                                                    DialogClickListener listener,WordState state) {
        WordDescriptionDialog frag = new  WordDescriptionDialog();
        Bundle args = new Bundle();
        args.putParcelable(KEY, word);
        args.putBoolean(KEY_FAVORITE,isFavorite);
        args.putParcelable(KEY_LISTENER,listener);
        args.putSerializable(KEY_WORD_STATE,state);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getParcelable(KEY);
        isFavorite = getArguments().getBoolean(KEY_FAVORITE);
        listener = getArguments().getParcelable(KEY_LISTENER);
        wordState = (WordState) getArguments().getSerializable(KEY_WORD_STATE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View myLayout = inflater.inflate(R.layout.fragment_dialog_word_description, null);

        imageView = (ImageView) myLayout.findViewById(R.id.favorite_status);

        // word color state
        redStateIv = (ImageView) myLayout.findViewById(R.id.word_red_state_image_view);
        yellowStateIv = (ImageView) myLayout.findViewById(R.id.word_yellow_state_image_view);
        greenStateIv = (ImageView) myLayout.findViewById(R.id.word_green_state_image_view);

        TextView engVersionTv = (TextView) myLayout.findViewById(R.id.eng_version);
        TextView uaVersionTv = (TextView) myLayout.findViewById(R.id.ua_version);

        // show basic state of favorite
        if (isFavorite) onFavoriteViewOn(); else onFavoriteViewOff();

        // set basic word state
        setColoredState(wordState);

        imageView.setOnClickListener(view -> listener.onImageFavoriteClick(word));
        redStateIv.setOnClickListener(view -> listener.onImageColoredClick(word,WordState.RED));
        greenStateIv.setOnClickListener(view -> listener.onImageColoredClick(word,WordState.GREEN));
        yellowStateIv.setOnClickListener(view -> listener.onImageColoredClick(word,WordState.YELLOW));

        Log.i("DescriptionDialogWord",word.toString() + " ");

        // assign current word values
        engVersionTv.setText(word.getEngVersion());
        uaVersionTv.setText(word.getUaVersion());

        builder.setView(myLayout)
                .setPositiveButton("OK", (dialog, id) ->  WordDescriptionDialog.this.getDialog().cancel());
        return builder.show();
    }

    private void setColoredState(WordState wordState) {
        if (wordState != null) {
            redStateIv.setImageResource(android.R.color.transparent);
            greenStateIv.setImageResource(android.R.color.transparent);
            yellowStateIv.setImageResource(android.R.color.transparent);
            switch (wordState) {
                case RED:
                    redStateIv.setImageResource(R.drawable.ic_check_black_24dp);
                    break;
                case GREEN:
                    greenStateIv.setImageResource(R.drawable.ic_check_black_24dp);
                    break;
                case YELLOW:
                    yellowStateIv.setImageResource(R.drawable.ic_check_black_24dp);
                    break;
            }
        } else Log.i("DescriptionDialog","word state = null");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onFavoriteViewOn() {
        imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    @Override
    public void onFavoriteViewOff() {
        imageView.setImageResource(R.drawable.ic_unfavorite_border_black_24dp);
    }

    @Override
    public void onStateViewOn(WordState wordState) {
        setColoredState(wordState);
    }


}
