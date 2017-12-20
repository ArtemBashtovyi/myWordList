package com.artembashtovyi.mywordlist.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.SelectedWord;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.WordDeleteBackground;
import com.artembashtovyi.mywordlist.data.sqlite.WordInsertBackground;
import com.artembashtovyi.mywordlist.ui.adapter.EngVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.adapter.editHolder.EditHolder;
import com.artembashtovyi.mywordlist.ui.adapter.editHolder.EditWordAdapter;
import com.artembashtovyi.mywordlist.ui.list.addingDialog.WordAddDialog;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordListActivity extends AppCompatActivity
        implements WordsView,LoaderManager.LoaderCallbacks<List<Word>>,WordAddDialog.NoticeDialogListener,
        EditHolder.OnItemSelectedListener {

    private final static int LOADER_ID = 101;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.word_list_recycler_view)
    RecyclerView wordsRv;

    @BindView(R.id.spinner_sorting)
    Spinner spinner;

    @BindView(R.id.image_delete)
    ImageView imageDelete;

    private EditWordAdapter editWordAdapter;

    private List<Word> words;
    private ActionMode actionMode;

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context,WordListActivity.class);
        context.startActivity(intent);
    }

    // TODO : ON THIS SCREEN ONLY FULL VERSION
    // TODO :  IMPL COMPARABLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);

        fab.setOnClickListener(view ->  {
            WordAddDialog fragment =  WordAddDialog.newInstance();
            fragment.show(getFragmentManager(),"WordAddingDialog");
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        wordsRv.setLayoutManager(llm);


        // Adapter for list clauses in spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        imageDelete.setOnClickListener(view -> {
            List<Word> selectedWords = editWordAdapter.getSelectedWords();
            deleteWords(selectedWords);
            editWordAdapter.setSelectedWords(words);
        });

    }

    void initSpinnerListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String selectedItem = adapterView.getItemAtPosition(pos).toString();
                Log.i("Selected Item",selectedItem);

                switch (selectedItem) {
                    case "By Date":
                        Collections.sort(words, (word1, word2) -> {
                            if (word1.getId() < word2.getId()) {
                                return 1;
                            } else if (word1.getId() > word2.getId())
                                return -1;
                            return 0;
                        });
                        break;
                    case "By Ukr":
                        Collections.sort(words, ((word1, word2) -> word1.getUaVersion().compareTo(word2.getUaVersion())));
                        break;
                    case "By Eng":
                        Collections.sort(words, (word1, word2) -> word1.getEngVersion().compareTo(word2.getEngVersion()));
                        break;
                }

                editWordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_ua_version) {
            editWordAdapter.changeContract(new UaVersionView());
        } else if (id == R.id.action_eng_version) {
            editWordAdapter.changeContract(new EngVersionView());
        } else if (id == R.id.action_full_version) {
            editWordAdapter.changeContract(new FullVersionView());
        }
*/
        return super.onOptionsItemSelected(item);
    }




    // AddDialog callback
    @Override
    public void onPositiveClick(Word word) {
        addWord(word);
    }


    // Loader callbacks
    @Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
        return new WordListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
        // TODO: presenter logic
        words = data;
        showWords(words);

        // FIXME: 12/15/17 stupid solution
        initSpinnerListener();
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @Override
    public void showWords(List<Word> words) {
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
        editWordAdapter.addWord(word);
    }

    // TODO : Animation Scrolling
    public void scrollListDown(){
        wordsRv.scrollToPosition(editWordAdapter.getItemCount() - 1);
    }

    // Logic for presenter
    private void deleteWords(List<Word> selectedWords) {
        if (selectedWords != null) {
            words.removeAll(selectedWords);
            new WordDeleteBackground(this,selectedWords).execute();
        }

    }

    // Logic for presenter
    private void addWord(Word word) {
        if (!words.contains(word)) {

            new WordInsertBackground(getApplicationContext(), word).execute();
            showAddedWord(word);
            words.add(word);
            scrollListDown();

            Log.i("ListActivity", "New Word has been added");
        } else {
            showError();
        }
    }



    @Override
    public void callBack(SelectedWord selectedWord) {
        List<Word> selectedItems = editWordAdapter.getSelectedWords();

    }

}
