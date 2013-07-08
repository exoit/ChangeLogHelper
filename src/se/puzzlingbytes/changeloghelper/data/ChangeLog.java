package se.puzzlingbytes.changeloghelper.data;

import java.util.ArrayList;

public class ChangeLog {
    private static final String CHANGELOG_HTML_STYLE = "<style type=\"text/css\">"
            + "h1 { margin-left: 0px; font-size: 12pt;}"
            + "ul { margin-top: -5px;}"
            + "li { margin-top: 10px; margin-left: 0px; font-size: 9pt;}"
            + "</style>";
    private static final String CHANGELOG_HTML_START_TAG = "<html><head>" + CHANGELOG_HTML_STYLE + "</head><body>";
    private static final String CHANGELOG_HTML_END_TAG = "</body></html>";

    private ArrayList<ChangeLogEntry> mChangeLogEntryList = new ArrayList<ChangeLogEntry>();

    public void addChangeLog(ChangeLogEntry changeLogEntry) {
        mChangeLogEntryList.add(changeLogEntry);
    }

    public String generateChangeLogHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CHANGELOG_HTML_START_TAG).append(
                generateHTMLChangeLogEntryList()).append(
                CHANGELOG_HTML_END_TAG);
        return stringBuilder.toString();
    }

    private String generateHTMLChangeLogEntryList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ChangeLogEntry changeLogEntry : mChangeLogEntryList) {
            stringBuilder.append(changeLogEntry.generateHTMLEntry());
        }
        return stringBuilder.toString();
    }


}
