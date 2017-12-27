package com.artembashtovyi.mywordlist.ui.recent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.RecentWordsQuery;
import com.artembashtovyi.mywordlist.ui.adapter.FullVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.WordAdapter;
import com.artembashtovyi.mywordlist.ui.edit.EditListActivity;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesActivity;
import com.artembashtovyi.mywordlist.ui.list.WordListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,WordAdapter.OnWordClickListener {

    @BindView(R.id.words_recent_recycler_view)
    RecyclerView wordsRv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private WordAdapter wordAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        wordsRv.setLayoutManager(llm);

        new AsyncTask<Void,Void,List<Word>>() {
            @Override
            protected List<Word> doInBackground(Void... voids) {
                DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());
                WordRepository wordRepository = WordRepository.getInstance(dbHelper);
                return wordRepository.getWords(new RecentWordsQuery(5));
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                super.onPostExecute(words);
                showWords(words);

            }

        }.execute();
    }

    public void showWords(List<Word> words) {
        wordAdapter = new WordAdapter(new FullVersionView(),words,RecentActivity.this,this);
        wordsRv.setAdapter(wordAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            EditListActivity.start(this);
        } else if (id == R.id.nav_slideshow) {
            WordListActivity.start(this);
        } else if (id == R.id.nav_manage) {
            FavoritesActivity.start(this);
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickCallBack(Word word) {

    }
}
