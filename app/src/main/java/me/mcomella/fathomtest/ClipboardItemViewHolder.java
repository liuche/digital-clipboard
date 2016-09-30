package me.mcomella.fathomtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ClipboardItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView imageUrl;
    public TextView description;
    public TextView rating;
    public TextView site;

    public ClipboardItemViewHolder(View v) {
        super(v);

        title = (TextView) v.findViewById(R.id.title);
        imageUrl = (ImageView) v.findViewById(R.id.image);
        description = (TextView) v.findViewById(R.id.description);
        rating = (TextView) v.findViewById(R.id.rating);
        site = (TextView) v.findViewById(R.id.site);
    }

    public void bind(ClipboardItem item) {
        title.setText(item.title);
        // TODO: fetch image
        description.setText(item.description);
        rating.setText(item.rating);
        site.setText(item.site);
    }
}
