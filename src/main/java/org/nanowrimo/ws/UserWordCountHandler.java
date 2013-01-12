package org.nanowrimo.ws;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class UserWordCountHandler extends WordCountHandler<UserWordCount> {
    private StringBuffer sb = new StringBuffer();
    private UserWordCount userSummary;
    private Date entryDate;
    private int wordcount;
    private boolean insideEntry;
    private SimpleDateFormat sdf;
    private List<WordCountListEntry> entries;

    public UserWordCountHandler() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        entries = new ArrayList<WordCountListEntry>();
        userSummary = new UserWordCount();
    }

    @Override
    public UserWordCount getSummary() {
        return userSummary;
    }

    public UserWordCount getUserSummary() {
        return userSummary;
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        sb.setLength(0);
        if (WORD_COUNT_ENTRY.equals(qName)) {
            insideEntry = true;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (USER_NAME.equals(qName)) {
            userSummary.setName(sb.toString());
        } else if (WORD_COUNT_ENTRY.equals(qName)) {
            insideEntry = false;
            if (entryDate != null) {
                WordCountListEntry entry = new WordCountListEntry();
                entry.setDate(entryDate);
                entry.setWordcount(wordcount);
                entries.add(entry);
            }
        } else if (WORD_COUNT_ENTRY_DATE.equals(qName)) {
            try {
                entryDate = sdf.parse(sb.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (USER_ERROR.equals(qName)) {
            userSummary.setError(sb.toString());
        } else if (USER_WINNER.equals(qName)) {
            userSummary.setWinner(Boolean.valueOf(sb.toString()));
        } else if (USER_ID.equals(qName)) {
            try {
                userSummary.setUserID(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORD_COUNT.equals(qName) && insideEntry) {
            try {
                wordcount = Integer.parseInt(sb.toString());
            } catch (NumberFormatException ignored) {
            }
        } else if (USER_WORD_COUNT.equals(qName)) {
            try {
                userSummary.setWordcount(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORD_COUNT_GOAL.equals(qName)) {
            try {
                userSummary.setGoal(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORDCOUNTS_CONTAINER.equals(qName)) {
            userSummary.setEntries(entries.toArray(new WordCountListEntry[entries.size()]));
        }
    }
}
