package me.mcomella.fathomtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Access a fathom context using a visible WebView.
 *
 * This is an experiment to see if it can work so there are a lot of bad
 * practices, such as potential memory leaks.
 */
public class MainActivity extends AppCompatActivity {
    private final String LOGTAG = "MainActivity";
    private final boolean DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView webView = (WebView) findViewById(R.id.webview);
        webView.setVisibility(View.INVISIBLE);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new FathomObject(), "java");

        webView.setWebViewClient(new InjectClient());
        webView.setWebChromeClient(new ChromeClient());

        // Test site: Rotten Tomatoes: Kubo
        webView.loadUrl("https://www.rottentomatoes.com/m/kubo_and_the_two_strings_2016/");
    }

    // Log javascript errors.
    public class ChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d(LOGTAG, consoleMessage.messageLevel() + "> " + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ": " + consoleMessage.message());
            if (DEBUG && consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                Toast.makeText(MainActivity.this, "console: " + consoleMessage.message(), Toast.LENGTH_SHORT).show();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "parameters returned", Toast.LENGTH_SHORT).show();
                    try {
                        final JSONObject jsonObj = new JSONObject(jsonString);
                        Log.e(LOGTAG, "Fathom object: " + jsonString);
                    } catch (JSONException e) {
                        Log.e(LOGTAG, "Problem parsing JSON string from JS script into JSONObject ", e);
                    }
                }
            });
        }
    }
}
