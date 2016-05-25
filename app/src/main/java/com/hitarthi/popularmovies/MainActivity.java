package com.hitarthi.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LinkInteract.MovieCallback, MovieAdapter.MovieTouchFeedback {

    LinkInteract li;

    private ArrayList<Movie> mMovies;
    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        li = new LinkInteract(this, this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.rv_content_main);


        if(savedInstanceState == null || !savedInstanceState.containsKey(TAG)){
            li.fetchMovies(LinkInteract.POPULAR);
        } else {
            mMovies = savedInstanceState.getParcelableArrayList(TAG);
            onSuccess(mMovies);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TAG, mMovies);
        super.onSaveInstanceState(outState);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.menu_most_popular:
                li.fetchMovies(LinkInteract.POPULAR);
                break;
            case R.id.menu_highest_rated:
                li.fetchMovies(LinkInteract.TOP_RATED);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSuccess(ArrayList<Movie> movies) {
        MovieAdapter adapter = new MovieAdapter(this, movies, this);

        this.mMovies = movies;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onFailure() {
        Toast.makeText(MainActivity.this, "Failed to load movies!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemTouch(int position) {
        Intent intent = new Intent(MainActivity.this, MovieDetails.class);
        intent.putExtra(getString(R.string.transfer_movie), mMovies.get(position));
        startActivity(intent);
    }

}

