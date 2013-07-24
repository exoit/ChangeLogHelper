
package se.puzzlingbytes.changeloghelper.data;

import android.content.Context;
import android.text.TextUtils;

import se.puzzlingbytes.changeloghelper.R;

import java.util.ArrayList;

public class ChangeLogEntry {

    private static final String VERSION_START_HTML_TAG = "<h1>";

    private static final String VERSION_END_HTML_TAG = "</h1>";

    private static final String CHANGE_LIST_START_HTML_TAG = "<ul>";

    private static final String CHANGE_LIST_END_HTML_TAG = "</ul>";

    private static final String CHANGE_ENTRY_START_HTML_TAG = "<li>";

    private static final String CHANGE_ENTRY_END_HTML_TAG = "</li>";

    private String mVersionName;

    private String mVersionCode;

    private String mVersionDate;

    private boolean mIsCurrentVersion = false;

    private ArrayList<String> mChangeList = new ArrayList<String>();

    private Context mContext;

    public ChangeLogEntry(Context context) {
        mContext = context;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public String getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(String versionCode) {
        mVersionCode = versionCode;
    }

    public String getVersionDate() {
        return mVersionDate;
    }

    public void setVersionDate(String versionDate) {
        mVersionDate = versionDate;
    }

    public ArrayList<String> getChangeList() {
        return mChangeList;
    }

    public void addChange(String change) {
        mChangeList.add(change);
    }

    public String generateHTMLEntry(boolean showDate, boolean showCurrent) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(VERSION_START_HTML_TAG).append(mVersionName);
        if (!TextUtils.isEmpty(mVersionDate) && showDate) {
            strBuilder.append(mContext.getString(R.string.changelog_date_separator)).append(
                    mVersionDate);
        }
        if (mIsCurrentVersion && showCurrent) {
            strBuilder.append(mContext.getString(
                    R.string.changelog_current));
        }
        strBuilder.append(VERSION_END_HTML_TAG).append(CHANGE_LIST_START_HTML_TAG)
                .append(generateHTMLChangeList()).append(
                        CHANGE_LIST_END_HTML_TAG);
        return strBuilder.toString();
    }

    private String generateHTMLChangeList() {
        StringBuilder strBuilder = new StringBuilder();
        for (String change : mChangeList) {
            strBuilder.append(CHANGE_ENTRY_START_HTML_TAG).append(change)
                    .append(CHANGE_ENTRY_END_HTML_TAG);
        }
        return strBuilder.toString();
    }

    public void setCurrentVersion(boolean currentVersion) {
        mIsCurrentVersion = currentVersion;
    }

}
