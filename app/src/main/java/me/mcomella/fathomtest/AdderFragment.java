package me.mcomella.fathomtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.mcomella.fathomtest.interfaces.ClipboardFragment;
import me.mcomella.fathomtest.interfaces.PageExtractorHelper;

public class AdderFragment extends ClipboardFragment {
    public static final String LOGTAG = "AdderFragment";
    public static final String FRAGMENT_TITLE = "Add";

    private PageExtractorHelper extractorHelper;
    private EditText urlEditText;
    private Button adderButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PageExtractorHelper) {
            extractorHelper = (PageExtractorHelper) context;
        } else {
            Log.e(LOGTAG, "Activity doesn't implement PageExtractorHelper!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add, container, false);

        urlEditText = (EditText) rootView.findViewById(R.id.url_text);
        adderButton = (Button) rootView.findViewById(R.id.add_button);
        adderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = urlEditText.getText().toString();
                if (!TextUtils.isEmpty(url)){
                    extractorHelper.fetchUrl(url);
                }
            }
        });
        return rootView;
    }

    @Override
    public String getTitle() {
        return FRAGMENT_TITLE;
    }
}
