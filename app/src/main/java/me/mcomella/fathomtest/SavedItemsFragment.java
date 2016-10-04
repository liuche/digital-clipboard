package me.mcomella.fathomtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

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
        clipboardItemList = new LinkedList<>();
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

    private void loadClippings() {
        Set<String> JSONClippings = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getStringSet(MainActivity.PREF_CLIPPINGS, new HashSet<String>());
        for (String jsonString : JSONClippings) {
            try {
                final JSONObject clippingObj = new JSONObject(jsonString);
                clipboardItemList.add(new ClipboardItem(clippingObj));
            } catch (JSONException e) {
                Log.e(LOGTAG, "Error parsing JSON from saved clipping", e);
            }
        }
        itemAdapter.notifyDataSetChanged();
    }

    public void handlePageData(String pageJSONString) {
        final SharedPreferences sharedPrefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Set<String> clippingsSet = sharedPrefs.getStringSet(MainActivity.PREF_CLIPPINGS, new HashSet<String>());
        clippingsSet.add(pageJSONString);
        sharedPrefs.edit().putStringSet(MainActivity.PREF_CLIPPINGS, clippingsSet).apply();
        loadClippings();
    }

    @Override
    public String getTitle() {
        return FRAGMENT_TITLE;
    }
}
