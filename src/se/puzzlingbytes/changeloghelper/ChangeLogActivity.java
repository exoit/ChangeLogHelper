package se.puzzlingbytes.changeloghelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import se.puzzlingbytes.changeloghelper.data.ChangeLog;
import se.puzzlingbytes.changeloghelper.parser.ChangeLogParser;

public class ChangeLogActivity extends Activity {

    public static final String EXTRA_CHANGELOG_XML_RESID = "extra_changelog_xml_resid";

    private int mChangeLogResID = -1;

    public static Intent getChangeLogIntent(Context context, int changeLogXMLResID) {
        Intent changeLogintent = new Intent(context, ChangeLogActivity.class);
        changeLogintent.putExtra(EXTRA_CHANGELOG_XML_RESID, changeLogXMLResID);
        return changeLogintent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_changelog);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mChangeLogResID = intent.getIntExtra(EXTRA_CHANGELOG_XML_RESID, -1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        WebView webView = (WebView) findViewById(R.id.changelog_webview);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        if (mChangeLogResID > -1) {
            ChangeLogParser changeLogParser = new ChangeLogParser(this);
            ChangeLog changeLog = changeLogParser.parse(mChangeLogResID);
            webView.loadData(changeLog.generateChangeLogHTML(), "text/html", "utf-8");
        } else {
            webView.loadData(getString(R.string.changelog_missing), "text/html", "utf-8");
        }
    }
}

