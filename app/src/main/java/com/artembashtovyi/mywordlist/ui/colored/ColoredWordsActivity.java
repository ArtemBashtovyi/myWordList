package com.artembashtovyi.mywordlist.ui.colored;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.artembashtovyi.mywordlist.BaseActivity;
import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.adapter.WordAdapter;
import com.artembashtovyi.mywordlist.ui.dialog.ViewChoiceDialog;
import com.artembashtovyi.mywordlist.ui.dialog.description.DescriptionDialogClickCallbacks;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordDescriptionDialog;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;
import com.artembashtovyi.mywordlist.ui.recycler.RecyclerViewItemDividerDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColoredWordsActivity extends BaseActivity<ColoredWordsPresenter,ColoredWordsView>
        implements ColoredWordsView,WordAdapter.OnWordClickListener {

    private final static int LOADER_ID = 1005;

    @BindView(R.id.words_colored_recycler_view)
    RecyclerView wordsRv;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    @BindView(R.id.view_contract_image_view)
    ImageView viewContractIv;

    @BindView(R.id.interleaving_image_view)
    ImageView shuffleIv;

    private ColoredWordsPresenter presenter;
    private WordAdapter adapter;
    private WordDescriptionDialog descriptionDialog;
    private DescriptionDialogClickCallbacks callback;

    public static void start(Context context) {
        Intent intent = new Intent(context,ColoredWordsActivity.class);
        context.startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    presenter.loadRedWords();
                    return true;
                case R.id.navigation_dashboard:
                    presenter.loadYellowWords();
                    return true;
                case R.id.navigation_notifications:
                    presenter.loadGreenWords();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colored_words);
        ButterKnife.bind(this);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        wordsRv.setLayoutManager(llm);
        wordsRv.addItemDecoration(new RecyclerViewItemDividerDecorator(this));

        shuffleIv.setOnClickListener(view -> presenter.shuffleWords());

        viewContractIv.setOnClickListener(view -> {
            ViewChoiceDialog dialog = ViewChoiceDialog.newInstance(new ViewChoiceDialog.ChoiceListener() {
                @Override
                public void onViewContractClick(ViewBindContract contract) {
                    if (adapter != null) {
                        presenter.changeViewContract(contract);
                    }
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {

                }
            });
            dialog.show(getFragmentManager(),"viewContract");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.restoreCurrentPosition();
    }

    @Override
    public void showWords(List<Word> words, ViewBindContract contract) {
        adapter = new WordAdapter(contract,words,this,this);
        wordsRv.setAdapter(adapter);
    }

    // show specified view contract
    @Override
    public void showViewContract(ViewBindContract contract) {
        adapter.changeContract(contract);
    }


    @Override
    public void saveRecyclerViewPosition() {
        presenter.saveCurrentPosition(
                ((LinearLayoutManager)wordsRv.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition()
        );
    }

    @Override
    public void restoreRecyclerViewPosition(int position) {
        ((LinearLayoutManager) wordsRv.getLayoutManager()).scrollToPositionWithOffset(position,0);
    }


    @Override
    public void showDescriptionDialog(Word word, boolean isFavorite, WordState state) {
        descriptionDialog = WordDescriptionDialog.newInstance(word, isFavorite,
                new WordDescriptionDialog.DialogClickListener() {
                    @Override
                    public void onImageFavoriteClick(Word word) {
                        presenter.changeFavoriteState(word);
                    }

                    @Override
                    public void onImageColoredClick(Word word,WordState wordState) {
                        presenter.setWordState(word,wordState);

                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel parcel, int i) {

                    }
                }
                ,state);
        descriptionDialog.show(getFragmentManager(),"Description");
        callback = descriptionDialog;
    }

    // change description image favorite callback
    @Override
    public void updateDialogFavoriteImage(boolean isFavorite) {
        if (!isFavorite) callback.onFavoriteViewOn(); else callback.onFavoriteViewOff();
    }

    // change description image state callback
    @Override
    public void updateDialogColorImage(WordState state) {
        callback.onStateViewOn(state);
    }

    @Override
    public void updateViewContract(ViewBindContract contract) {
        adapter.changeContract(contract);
    }

    // click Word
    @Override
    public void clickCallBack(Word word) {
        presenter.startDescriptionDialog(word);
    }

    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

    @Override
    public ColoredWordsPresenter getInitedPresenter(WordRepositoryImpl repository) {
        return new ColoredWordsPresenter(this,repository);
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull ColoredWordsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveRecyclerViewPosition();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (descriptionDialog != null && descriptionDialog.isVisible()) {
            descriptionDialog.dismiss();
            descriptionDialog.onStop();
        }
    }
}
