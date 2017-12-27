package com.artembashtovyi.mywordlist.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.artembashtovyi.mywordlist.BaseActivity;
import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.edit.EditWordAdapter;
import com.artembashtovyi.mywordlist.ui.edit.addingDialog.EditDialog;
import com.artembashtovyi.mywordlist.ui.edit.addingDialog.WordAddDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditListActivity extends BaseActivity<EditListPresenter,EditWordsView>
        implements EditWordsView,WordAddDialog.NoticeDialogListener,
        EditWordAdapter.OnViewClickListener,EditDialog.NoticeDialogListener {

    private final static int LOADER_ID = 105;
    private final static String TAG = "EditListActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.word_list_recycler_view)
    RecyclerView wordsRv;


    @BindView(R.id.image_delete)
    ImageView imageDelete;


    private EditListPresenter presenter;

    private EditWordAdapter editWordAdapter;


    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context,EditListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> showAddDialog());

        LinearLayoutManager llm = new LinearLayoutManager(this);
        wordsRv.setLayoutManager(llm);

        imageDelete.setOnClickListener(view -> {
            if (editWordAdapter != null) {
                List<Word> selectedWords = editWordAdapter.getSelectedWords();
                presenter.deleteWords(selectedWords);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getAllWords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.sortWords(id);
        return super.onOptionsItemSelected(item);
    }


    // AddDialog callback
    @Override
    public void onPositiveClick(Word word) {
        presenter.addWord(word);
    }

    // EditDialog callback
    @Override
    public void onEditPositiveClick(Word oldWord,Word word) {
        presenter.editWord(oldWord,word);
    }

    // ViewBindContract
    @Override
    public void showWords(List<Word> words) {
        Log.i(TAG,"showWords");
        editWordAdapter = new EditWordAdapter(this,words,this);
        wordsRv.setAdapter(editWordAdapter);
    }

    @Override
    public void showError() {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Таке слово існує!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    public void showAddedWord(Word word) {
        Log.i(TAG,"showAddedWord");
        editWordAdapter.addWord(word);
        scrollListDown();
    }

    @Override
    public void showEditedWord(Word oldWord,Word newWord) {
        Log.i(TAG,"showEditedWord");
        editWordAdapter.editWord(oldWord,newWord);
    }

    // TODO : Animation Scrolling
    private void scrollListDown(){
        wordsRv.scrollToPosition(editWordAdapter.getItemCount() - 1);
    }


    @Override
    public void showAddDialog() {
        WordAddDialog fragment =  WordAddDialog.newInstance();
        fragment.show(getFragmentManager(),"WordAddingDialog");
    }

    @Override
    public void showEditDialog(Word oldWord) {
        EditDialog editDialog = EditDialog.newInstance(oldWord);
        editDialog.show(getFragmentManager(),"WordEditDialog");
    }

    @Override
    public void showEditedWordList(List<Word> words) {
        editWordAdapter.setSelectedWords(words);
    }


    // Invoke fragment for edit word
    @Override
    public void onEditClick(Word selectedWord) {
        EditDialog editDialog = EditDialog.newInstance(selectedWord);
        editDialog.show(getFragmentManager(),"edit");
    }


    @Override
    protected void onStop() {
        super.onStop();
        editWordAdapter = null;
    }



    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

    @Override
    public EditListPresenter getInitedPresenter(WordRepository repository) {
        return new EditListPresenter(this,repository);
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull EditListPresenter presenter) {
        this.presenter = presenter;
    }


}
