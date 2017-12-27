package com.artembashtovyi.mywordlist.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.artembashtovyi.mywordlist.BaseActivity;
import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.EngVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.adapter.WordAdapter;
import com.artembashtovyi.mywordlist.ui.list.dialog.WordDescriptionDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordListActivity extends BaseActivity<WordListPresenter,WordListView> implements WordListView,
        WordAdapter.OnWordClickListener{

    private static final int LOADER_ID = 202;

    @BindView(R.id.spinner_sorting)
    Spinner spinner;

    @BindView(R.id.word_list_recycler_view)
    RecyclerView wordsRv;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private WordAdapter wordAdapter;
    private WordListPresenter presenter;
    private WordDescriptionDialog descriptionDialog;

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context,WordListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        wordsRv.setLayoutManager(llm);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                wordAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                wordAdapter.getFilter().filter(query);
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadWords();
    }

    @Override
    public void showWords(List<Word> words) {
        wordAdapter = new WordAdapter(new EngVersionView(),words,this,this);
        wordsRv.setAdapter(wordAdapter);
    }

    @Override
    public void showViewContract(ViewBindContract contract) {
        wordAdapter.changeContract(contract);
    }

    @Override
    public void showDescriptionDialog(Word word,boolean isFavorite) {
       descriptionDialog = WordDescriptionDialog.newInstance(word, isFavorite,
                new WordDescriptionDialog.DialogClickListener() {
            @Override
            public boolean onImageFavoriteClick(Word word) {
                return presenter.isFavoriteState(word);
            }

            @Override
            public int describeContents() {
                return 0;
                // Stub
            }


            @Override
            public void writeToParcel(Parcel parcel, int i) {
                // Stub
            }
        });
        descriptionDialog.show(getFragmentManager(),"Description");
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
    public WordListPresenter getInitedPresenter(WordRepository repository) {
        return new WordListPresenter(this,repository);
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull WordListPresenter presenter) {
        this.presenter = presenter;
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
