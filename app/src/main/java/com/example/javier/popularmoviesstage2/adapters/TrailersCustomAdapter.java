package com.example.javier.popularmoviesstage2.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.model.TrailerMovieEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by javie on 20/06/2016.
 */

public class TrailersCustomAdapter extends RecyclerView.Adapter<TrailersCustomAdapter.ViewHolder> {


    private List<TrailerMovieEntity> mlistTrailers;
    private Activity mActivity;


    public TrailersCustomAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String videoKey = mlistTrailers.get(position).getKey();
        Picasso.with(mActivity).load(MovieAPI.youtubeThumbnailUriBuilder(mlistTrailers.get(position).getKey())).fit().into(holder.trailerThumbnail);
        holder.trailerTitle.setText(mlistTrailers.get(position).getName());
        holder.source.setText(mlistTrailers.get(position).getSite());
        holder.qualityValue.setText(mlistTrailers.get(position).getSize());


        holder.trailerThumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MovieAPI.youtubeVideoURL(videoKey))));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlistTrailers == null ? 0 : mlistTrailers.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView trailerThumbnail;
        private TextView trailerTitle;
        private TextView qualityValue;
        private TextView source;

        public ViewHolder(View view) {
            super(view);
            this.trailerThumbnail = (ImageView) view.findViewById(R.id.trailerThumbnail);
            this.trailerTitle = (TextView) view.findViewById(R.id.trailerTitle);
            this.source = (TextView) view.findViewById(R.id.source_text_value);
            this.qualityValue = (TextView) view.findViewById(R.id.video_quality_value);
        }

        public TextView getTrailerTitle() {
            return trailerTitle;
        }

        public void setTrailerTitle(TextView trailerTitle) {
            this.trailerTitle = trailerTitle;
        }

        public ImageView getTrailerThumbnail() {
            return trailerThumbnail;
        }

        public void setTrailerThumbnail(ImageView trailerThumbnail) {
            this.trailerThumbnail = trailerThumbnail;
        }

        public TextView getQualityValue() {
            return qualityValue;
        }

        public void setQualityValue(TextView qualityValue) {
            this.qualityValue = qualityValue;
        }

        public TextView getSource() {
            return source;
        }

        public void setSource(TextView source) {
            this.source = source;
        }

    }


    public List<TrailerMovieEntity> getMlistTrailers() {
        return mlistTrailers;
    }

    public void setMlistTrailers(List<TrailerMovieEntity> mlistTrailers) {
        this.mlistTrailers = mlistTrailers;
    }
}
