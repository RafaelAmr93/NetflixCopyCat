package com.rafael.netflixcopycat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.rafael.netflixcopycat.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtCast;
    private TextView txtDesc;
    private RecyclerView rvMovieSimilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.text_view_title);
        txtCast = findViewById(R.id.text_view_cast);
        txtDesc = findViewById(R.id.text_view_desc);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        if (getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getActionBar().setTitle(null);
        }

        /*LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this,R.drawable.shadows);

        if(drawable != null){
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie);
            drawable.setDrawableByLayerId(R.id.cover_drawable, movieCover);
            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
        }*/

        txtTitle.setText("Batman Begins");
        txtDesc.setText("After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from corruption.");
        txtCast.setText(getString(R.string.cast, "Christian Bale, Michael Caine, Liam Neeson, Katie Holmes, Gary Oldman, Cillian Murphy, Tom Wilkinson, Rutger Hauer"));

        rvMovieSimilar = findViewById(R.id.recycler_view_similar);
        List<Movie> movies = new ArrayList<>();
        for (int i =0; i<15; i++){
            Movie movie = new Movie();
            movies.add(movie);
        }
        rvMovieSimilar.setAdapter(new MovieAdapter(movies));
        rvMovieSimilar.setLayoutManager(new GridLayoutManager(this,3));


    }

    private static class MovieHolder extends RecyclerView.ViewHolder{

        public MovieHolder(View itemView) {
            super(itemView); //container principal de movie_item, pode acessar seus conteúdos
        }

        public void bind (Movie movies){
            ImageView cover_img = itemView.findViewById(R.id.cover_img);

        }
    }


    private class MovieAdapter extends RecyclerView.Adapter<MainActivity.MovieHolder>{

        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }


        @Override
        public MainActivity.MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainActivity.MovieHolder(getLayoutInflater().inflate(R.layout.movie_item_similar, parent, false));
        }


        @Override
        public void onBindViewHolder(MainActivity.MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            holder.bind(movie);
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }
}