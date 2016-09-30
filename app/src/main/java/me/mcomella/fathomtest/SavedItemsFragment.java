package me.mcomella.fathomtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    public void handlePageData(String pageJSONString) {
        final SharedPreferences sharedPrefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Set<String> clippingsSet = sharedPrefs.getStringSet(MainActivity.PREF_CLIPPINGS, new HashSet<String>());
        clippingsSet.add(pageJSONString);
        sharedPrefs.edit().putStringSet(MainActivity.PREF_CLIPPINGS, clippingsSet);
    }

    @Override
    public String getTitle() {
        return FRAGMENT_TITLE;
    }
}
