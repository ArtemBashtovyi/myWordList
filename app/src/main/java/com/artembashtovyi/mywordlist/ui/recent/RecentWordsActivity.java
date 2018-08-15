package com.artembashtovyi.mywordlist.ui.recent;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.artembashtovyi.mywordlist.BaseActivity;
import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.EngVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.adapter.WordAdapter;
import com.artembashtovyi.mywordlist.ui.colored.ColoredWordsActivity;
import com.artembashtovyi.mywordlist.ui.dialog.ViewChoiceDialog;
import com.artembashtovyi.mywordlist.ui.edit.EditListActivity;
import com.artembashtovyi.mywordlist.ui.favorites.FavoritesActivity;
import com.artembashtovyi.mywordlist.ui.list.WordListActivity;
import com.artembashtovyi.mywordlist.ui.recycler.RecyclerViewItemDividerDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentWordsActivity extends BaseActivity<RecentWordsPresenter,RecentView>
        implements NavigationView.OnNavigationItemSelectedListener,
        WordAdapter.OnWordClickListener,RecentView{

    private static final int LOADER_ID = 213;

    @BindView(R.id.words_recent_recycler_view)
    RecyclerView wordsRv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_contract_image_view)
    ImageView imageView;

    private RecentWordsPresenter presenter;
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


        imageView.setOnClickListener(view -> {
            if (wordAdapter != null) {
               ViewChoiceDialog dialog = ViewChoiceDialog.newInstance(new ViewChoiceDialog.ChoiceListener() {
                    @Override
                    public void onViewContractClick(ViewBindContract contract) {
                        wordAdapter.changeContract(contract);
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
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        wordsRv.setLayoutManager(llm);



    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadWords();
    }

    @Override
    public int getLoaderId() {
        return LOADER_ID;
    }

    @Override
    public RecentWordsPresenter getInitedPresenter(WordRepositoryImpl repository) {
        return new RecentWordsPresenter(repository,this);
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull RecentWordsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showWords(List<Word> words) {
        wordAdapter = new WordAdapter(new EngVersionView(),words,RecentWordsActivity.this,this);
        wordsRv.setAdapter(wordAdapter);
        wordsRv.addItemDecoration(new RecyclerViewItemDividerDecorator(this));
    }

    @Override
    public void showEmptyError() {

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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

            // Handle the camera action
        if (id == R.id.nav_gallery) {
            EditListActivity.start(this);
        } else if (id == R.id.nav_slideshow) {
            WordListActivity.start(this);
        } else if (id == R.id.nav_manage) {
            FavoritesActivity.start(this);
        } else if (id == R.id.nav_colored) {
            ColoredWordsActivity.start(this);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickCallBack(Word word) {

    }

}
