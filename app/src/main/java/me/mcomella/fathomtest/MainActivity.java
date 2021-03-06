package me.mcomella.fathomtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import me.mcomella.fathomtest.interfaces.ClipboardFragment;
import me.mcomella.fathomtest.interfaces.PageExtractorHelper;

import static java.security.AccessController.getContext;

/*
 * Access a fathom context using a visible WebView.
 *
 * This is an experiment to see if it can work so there are a lot of bad
 * practices, such as potential memory leaks.
 */
public class MainActivity extends FragmentActivity implements PageExtractorHelper {
    private final String LOGTAG = "MainActivity";
    private final boolean DEBUG = false;

    public static final String PREF_CLIPPINGS = "pref_clippings";

    private WebView webView;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XXX Clear the saved clippings each time.
        getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putStringSet(PREF_CLIPPINGS, new HashSet<String>()).commit();

        webView = (WebView) findViewById(R.id.webview);
        setupWebView();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ClipboardPagerAdapter(getSupportFragmentManager()));
    }

    private void setupWebView() {
        webView.setVisibility(View.INVISIBLE);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new FathomObject(), "java");

        webView.setWebViewClient(new InjectClient());
        webView.setWebChromeClient(new ChromeClient());
    }

    private class ClipboardPagerAdapter extends FragmentStatePagerAdapter {
        private final ClipboardFragment[] fragments = new ClipboardFragment[] { new AdderFragment(), new SavedItemsFragment() };

        public ClipboardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position].getTitle();
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

    @Override
    public void fetchUrl(String url) {
        webView.loadUrl(url);
    }

    // Log javascript errors.
    public class ChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (DEBUG && consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                Log.d(LOGTAG, consoleMessage.messageLevel() + "> " + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ": " + consoleMessage.message());
            //    Toast.makeText(MainActivity.this, "console: " + consoleMessage.message(), Toast.LENGTH_SHORT).show();
            }
            return super.onConsoleMessage(consoleMessage);
        }
    }

    public class InjectClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            final String script = Util.getStringFromResources(view.getContext(), R.raw.extract);

            view.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d(LOGTAG, "Script completed: " + s);
                }
            });
        }
    }

    public class FathomObject {
        @JavascriptInterface
        public void handleParameters(final String jsonString) {
            // XXX: Hardcode getting the "saved" fragment.
            SavedItemsFragment savedFragment = (SavedItemsFragment) getSupportFragmentManager().getFragments().get(1);
            savedFragment.handlePageData(jsonString);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "parameters returned!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}
