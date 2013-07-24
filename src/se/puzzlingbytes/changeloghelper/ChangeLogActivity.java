
package se.puzzlingbytes.changeloghelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import se.puzzlingbytes.changeloghelper.data.ChangeLog;
import se.puzzlingbytes.changeloghelper.parser.ChangeLogParser;

public class ChangeLogActivity extends Activity {

    public static final String EXTRA_CHANGELOG_XML_RESID = "extra_changelog_xml_resid";

    public static final String EXTRA_CHANGELOG_SHOW_DATE = "extra_changelog_show_date";

    public static final String EXTRA_CHANGELOG_SHOW_CURRENT = "extra_changelog_show_current";

    public static final String EXTRA_CHANGELOG_CSS_STR = "extra_changelog_css_str";

    private static final String MIME_TYPE_TEXT_HTML = "text/html";

    private static final String ENCODING_UTF8 = "utf-8";

    private int mChangeLogResID = -1;

    private boolean mShowDate;

    private boolean mShowCurrent;

    private String mStyle;

    private WebView mWebView;

    public static Intent getChangeLogIntent(Context context, int changeLogXMLResID,
            boolean showDate, boolean showCurrent) {
        return getChangeLogIntent(context, changeLogXMLResID, showDate, showCurrent, null);
    }

    public static Intent getChangeLogIntent(Context context, int changeLogXMLResID,
            boolean showDate, boolean showCurrent, String cssStyle) {
        Intent changeLogintent = new Intent(context, ChangeLogActivity.class);
        changeLogintent.putExtra(EXTRA_CHANGELOG_XML_RESID, changeLogXMLResID);
        changeLogintent.putExtra(EXTRA_CHANGELOG_SHOW_DATE, showDate);
        changeLogintent.putExtra(EXTRA_CHANGELOG_SHOW_CURRENT, showCurrent);
        if (!TextUtils.isEmpty(cssStyle))
        {
            changeLogintent.putExtra(EXTRA_CHANGELOG_CSS_STR, cssStyle);
        }
        return changeLogintent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_changelog);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mChangeLogResID = intent.getIntExtra(EXTRA_CHANGELOG_XML_RESID, -1);
            mShowDate = intent.getBooleanExtra(EXTRA_CHANGELOG_SHOW_DATE, false);
            mShowCurrent = intent.getBooleanExtra(EXTRA_CHANGELOG_SHOW_CURRENT, false);
            mStyle = intent.getStringExtra(EXTRA_CHANGELOG_CSS_STR);
        }
        mWebView = (WebView) findViewById(R.id.changelog_webview);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String webViewData = null;
        if (mChangeLogResID > -1) {
            ChangeLogParser changeLogParser = new ChangeLogParser(this);
            ChangeLog changeLog = changeLogParser.parse(mChangeLogResID);
            webViewData = changeLog.generateChangeLogHTML(mShowDate, mShowCurrent, mStyle);
        } else {
            webViewData = getString(R.string.changelog_missing);
        }
        mWebView.loadData(webViewData, MIME_TYPE_TEXT_HTML, ENCODING_UTF8);
    }
}
