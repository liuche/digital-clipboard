package me.mcomella.fathomtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ClipboardAdapter extends RecyclerView.Adapter<ClipboardItemViewHolder> {
    private List<ClipboardItem> clipboardItemList;

    public ClipboardAdapter(List<ClipboardItem> list) {
        clipboardItemList = list;
    }

    @Override
    public ClipboardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ClipboardItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClipboardItemViewHolder holder, int position) {
        holder.bind(clipboardItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return clipboardItemList.size();
    }
}
