
package se.puzzlingbytes.changeloghelper.data;

import android.text.TextUtils;

import java.util.ArrayList;

public class ChangeLog {

    private static final String HTML_STYLE_CSS = "h1 { margin-left: 0px; font-size: 12pt;} " +
            "ul { margin-top: -5px;} " +
            "li { margin-top: 10px; margin-left: 0px; font-size: 9pt;}";

    private static final String HTML_START = "<html><head><style type=\"text/css\">";

    private static final String HTML_MIDDLE = "</style></head><body>";

    private static final String HTML_END = "</body></html>";

    private ArrayList<ChangeLogEntry> mChangeLogEntryList = new ArrayList<ChangeLogEntry>();

    public void addChangeLog(ChangeLogEntry changeLogEntry) {
        mChangeLogEntryList.add(changeLogEntry);
    }

    public String generateChangeLogHTML(boolean showDate, boolean showCurrent)
    {
        return generateChangeLogHTML(showDate, showCurrent, HTML_STYLE_CSS);
    }

    public String generateChangeLogHTML(boolean showDate, boolean showCurrent, String cssStyle) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(HTML_START);
        strBuilder.append((TextUtils.isEmpty(cssStyle) ? HTML_STYLE_CSS : cssStyle));
        strBuilder.append(HTML_MIDDLE).append(
                generateHTMLChangeLogEntryList(showDate, showCurrent)).append(
                HTML_END);
        return strBuilder.toString();
    }

    private String generateHTMLChangeLogEntryList(boolean showDate, boolean showCurrent) {
        StringBuilder strBuilder = new StringBuilder();
        for (ChangeLogEntry changeLogEntry : mChangeLogEntryList) {
            strBuilder.append(changeLogEntry.generateHTMLEntry(showDate, showCurrent));
        }
        return strBuilder.toString();
    }

}
