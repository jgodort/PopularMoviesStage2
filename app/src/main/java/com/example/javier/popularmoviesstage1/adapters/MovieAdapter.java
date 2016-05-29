package com.example.javier.popularmoviesstage1.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.javier.popularmoviesstage1.R;
import com.example.javier.popularmoviesstage1.api.MovieAPI;
import com.example.javier.popularmoviesstage1.model.MovieEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by javier on 28/03/2016.
 */
public class MovieAdapter extends ArrayAdapter<MovieEntity> {

    private static final String LOG_TAG = MovieAdapter.class.getName();
    static LayoutInflater inflater = null;


    public MovieAdapter(Context vContext, List<MovieEntity> movies) {

        super(vContext, 0, movies);
        inflater = LayoutInflater.from(vContext);

    }


    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieEntity movie = getItem(position);
        ViewHolder myViewHolder;


        if (convertView == null) {
            LayoutInflater inflater = ((Activity) this.getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

            myViewHolder = new ViewHolder();
            myViewHolder.posterImage = (ImageView) convertView.findViewById(R.id.posterImage);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }


        if (movie != null) {
            Picasso.with(getContext()).
                    load(MovieAPI.moviePosterUriBuilder(MovieAPI.ImageSizes.W185, movie.getPosterPath().substring(1))
                    ).into(myViewHolder.posterImage);

        }
        return convertView;
    }


    static class ViewHolder {
        ImageView posterImage;

    }

}
