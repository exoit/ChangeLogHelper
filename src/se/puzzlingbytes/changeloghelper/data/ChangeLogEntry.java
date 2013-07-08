package se.puzzlingbytes.changeloghelper.data;

import android.content.Context;
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
        this.mVersionName = versionName;
    }

    public String getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(String versionCode) {
        this.mVersionCode = versionCode;
    }

    public ArrayList<String> getChangeList() {
        return mChangeList;
    }

    public void addChange(String change) {
        mChangeList.add(change);
    }

    public String generateHTMLEntry() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VERSION_START_HTML_TAG).append(mVersionName).append((mIsCurrentVersion ? mContext.getString(
                R.string.changelog_current) : "")).append(VERSION_END_HTML_TAG);
        stringBuilder.append(CHANGE_LIST_START_HTML_TAG).append(generateHTMLChangeList()).append(
                CHANGE_LIST_END_HTML_TAG);
        return stringBuilder.toString();
    }

    private String generateHTMLChangeList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String change : mChangeList) {
            stringBuilder.append(CHANGE_ENTRY_START_HTML_TAG).append(change).append(CHANGE_ENTRY_END_HTML_TAG);
        }
        return stringBuilder.toString();
    }

    public void setCurrentVersion(boolean currentVersion) {
        mIsCurrentVersion = currentVersion;
    }
}
