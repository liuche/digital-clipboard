package me.mcomella.fathomtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import me.mcomella.fathomtest.interfaces.ClipboardFragment;

public class SavedItemsFragment extends ClipboardFragment {
    public static final String LOGTAG = "SavedItemsFragment";
    public static final String FRAGMENT_TITLE = "Saved";

    private ClipboardAdapter itemAdapter;
    private List<ClipboardItem> clipboardItemList;
    private RecyclerView recyclerView;

    public SavedItemsFragment() {
        clipboardItemList = new LinkedList<ClipboardItem>();
        itemAdapter = new ClipboardAdapter(clipboardItemList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.items_list);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    public void handlePageData(JSONObject pageJSONObject) {
        // TODO: Add content to UI.
    }

    @Override
    public String getTitle() {
        return FRAGMENT_TITLE;
    }
}
