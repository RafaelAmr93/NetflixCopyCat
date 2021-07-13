package com.rafael.netflixcopycat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafael.netflixcopycat.Model.Category;
import com.rafael.netflixcopycat.Model.Movie;
import com.rafael.netflixcopycat.util.CategoryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryTask.CategoryLoader {

    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMain = findViewById(R.id.recycler_view_main);

        List<Category> categories = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(categories);
        rvMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvMain.setAdapter(categoryAdapter);

        CategoryTask categoryTask = new CategoryTask(this);
        categoryTask.setCategoryLoader(this);
        categoryTask.execute("https://tiagoaguiar.co/api/netflix/home");
    }

    public void onResult (List<Category> categories){
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }


    //passo2 criar o view holder que irá referenciar os itens a compor o recycler view
    private class CategoryHolder extends RecyclerView.ViewHolder{

        public CategoryHolder(View itemView) {
            super(itemView); //container principal de movie_item, pode acessar seus conteúdos
        }

        public void bind (Category categories){
            TextView categoryTitle = itemView.findViewById(R.id.category);
            RecyclerView rvMovie = itemView.findViewById(R.id.recycler_view_movie);
            rvMovie.setAdapter(new MovieAdapter(categories.getMovies()));
            rvMovie.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.HORIZONTAL, false));
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private List<Category> categories;

        private CategoryAdapter(List<Category> categories) {
            this.categories = categories;
        }


        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CategoryHolder(getLayoutInflater().inflate(R.layout.category_item, parent, false));
            /*o retorno precisa ser um view holder que passará como parâmetro (inflar na tela)
                o xml que o adapter irá gerenciar*/
        }


        @Override
        public void onBindViewHolder(MainActivity.CategoryHolder holder, int position) {
            Category category = categories.get(position);
            holder.bind(category);
            /*vai controlar a posição do item a ser mostrado, a variável movie é uma instância de Movie
               então tem acesso aos campos. O AS gerencia a position*/
        }

        @Override
        public int getItemCount() {
            return categories.size();
            //quantidade de itens da coleção, que normalmente vem de uma lista
        }

        void setCategories(List<Category> categories) {
            this.categories.clear();
            this.categories.addAll(categories);
        }
    }


    public static class MovieHolder extends RecyclerView.ViewHolder{

        public MovieHolder(View itemView) {
            super(itemView); //container principal de movie_item, pode acessar seus conteúdos
        }

        public void bind (Movie movies){
            //ImageView cover_img = itemView.findViewById(R.id.cover_img);

        }
    }


    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{

        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }


        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item, parent, false));
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