package se.puzzlingbytes.changeloghelper.parser;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import se.puzzlingbytes.changeloghelper.data.ChangeLog;
import se.puzzlingbytes.changeloghelper.data.ChangeLogEntry;

import java.io.IOException;

public class ChangeLogParser {
    private static final String TAG = ChangeLogParser.class.getSimpleName();
    private static final String RELEASE_TAG = "release";
    private static final String RELEASE_ATTR_VERSIONNAME = "versionName";
    private static final String RELEASE_ATTR_VERSIONCODE = "versionCode";
    private static final String CHANGE_TAG = "change";
    private Context mContext;
    private String mCurrentVersionCode = null;

    public ChangeLogParser(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context is null");
        }
        mContext = context;
        PackageManager pm = mContext.getPackageManager();
        String packageName = mContext.getPackageName();
        if (pm != null) {
            try {
                PackageInfo info = pm.getPackageInfo(packageName, 0);
                mCurrentVersionCode = String.valueOf(info.versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                mCurrentVersionCode = null;
            }
        }
    }

    public ChangeLog parse(int changeLogResId) {
        XmlResourceParser parser = null;
        try {
            parser = mContext.getResources().getXml(changeLogResId);
            return readChangeLog(parser);
        } catch (XmlPullParserException e) {
            Log.e(TAG, "", e);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
        return null;
    }

    private ChangeLog readChangeLog(XmlPullParser parser) throws XmlPullParserException, IOException {
        ChangeLog changeLog = new ChangeLog();
        ChangeLogEntry changeLogEntry;
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equalsIgnoreCase(RELEASE_TAG)) {
                        changeLogEntry = readChangeLogEntry(parser);
                        if (changeLogEntry != null) {
                            changeLog.addChangeLog(changeLogEntry);
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        return changeLog;
    }

    private ChangeLogEntry readChangeLogEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        ChangeLogEntry changeLogEntry = new ChangeLogEntry(mContext);
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            if (parser.getAttributeName(i).equalsIgnoreCase(RELEASE_ATTR_VERSIONCODE)) {
                changeLogEntry.setVersionCode(parser.getAttributeValue(i));
                changeLogEntry.setCurrentVersion(TextUtils.equals(parser.getAttributeValue(i), mCurrentVersionCode));
            } else if (parser.getAttributeName(i).equalsIgnoreCase(RELEASE_ATTR_VERSIONNAME)) {
                changeLogEntry.setVersionName(parser.getAttributeValue(i));
            }
        }
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_TAG) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equalsIgnoreCase(CHANGE_TAG)) {
                        changeLogEntry.addChange(parser.nextText());
                    }
                    break;
            }
            eventType = parser.next();
        }
        return changeLogEntry;
    }

}
