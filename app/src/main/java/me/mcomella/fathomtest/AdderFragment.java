package me.mcomella.fathomtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdderFragment extends ClipboardFragment {
    public static final String FRAGMENT_TITLE = "Adder";

    public AdderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_adder, container, false);
        return rootView;
    }

    @Override
    public String getTitle() {
        return FRAGMENT_TITLE;
    }
}
