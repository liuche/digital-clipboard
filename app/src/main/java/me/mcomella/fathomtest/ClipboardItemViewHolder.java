package me.mcomella.fathomtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ClipboardItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView imageView;
    public TextView description;
    public TextView rating;
    public TextView site;
    private Context context;

    public ClipboardItemViewHolder(View v) {
        super(v);

        context = v.getContext();
        title = (TextView) v.findViewById(R.id.title);
        imageView = (ImageView) v.findViewById(R.id.image);
        description = (TextView) v.findViewById(R.id.description);
        rating = (TextView) v.findViewById(R.id.rating);
        site = (TextView) v.findViewById(R.id.site);
    }

    public void bind(ClipboardItem item) {
        title.setText(item.title);
        Picasso.with(context).load(item.imageUrl).fit().into(imageView);
        description.setText(item.description);
        rating.setText(item.rating);
        site.setText(item.site);
    }
}
