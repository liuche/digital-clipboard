package me.mcomella.fathomtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import me.mcomella.fathomtest.interfaces.ClipboardFragment;

public class SavedItemsFragment extends ClipboardFragment {
    public static final String LOGTAG = "SavedItemsFragment";
    public static final String FRAGMENT_TITLE = "Saved";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_saved, container, false);
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
