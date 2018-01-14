package org.nanowrimo.ws;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unused", "Duplicates"})
class RegionWordCountHandler extends WordCountHandler<RegionWordCount> {
    private StringBuffer sb = new StringBuffer();
    private RegionWordCount regionSummary;
    private Date entryDate;
    private int wordcount;
    private boolean insideEntry;
    private SimpleDateFormat sdf;
    private List<WordCountListEntry> entries;

    public RegionWordCountHandler() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        entries = new ArrayList<>();
        regionSummary = new RegionWordCount();
    }

    @Override
    RegionWordCount getSummary() {
        return regionSummary;
    }

    public RegionWordCount getRegionSummary() {
        return regionSummary;
    }

    public void characters(char ch[], int start, int length) {
        sb.append(ch, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        sb.setLength(0);
        if (WORD_COUNT_ENTRY.equals(qName)) {
            insideEntry = true;
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (REGION_NAME.equals(qName)) {
            regionSummary.setName(sb.toString());
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
        } else if (REGION_ID.equals(qName)) {
            try {
                regionSummary.setRegionID(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORD_COUNT_GOAL.equals(qName)) {
            try {
                regionSummary.setGoal(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (REGION_AVERAGE.equals(qName)) {
            try {
                regionSummary.setAverage(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (REGION_COUNT.equals(qName)) {
            try {
                regionSummary.setCount(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (REGION_STDDEV.equals(qName)) {
            try {
                regionSummary.setStddev(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (REGION_MIN.equals(qName)) {
            try {
                regionSummary.setMin(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (REGION_MAX.equals(qName)) {
            try {
                regionSummary.setMax(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORD_COUNT_RANK.equals(qName)) {
            try {
                regionSummary.setRank(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORD_COUNT.equals(qName) && insideEntry) {
            try {
                wordcount = Integer.parseInt(sb.toString());
            } catch (NumberFormatException ignored) {
            }
        } else if (REGION_WORD_COUNT.equals(qName)) {
            try {
                regionSummary.setWordcount(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException ignored) {
            }
        } else if (WORDCOUNTS_CONTAINER.equals(qName)) {
            regionSummary.setEntries(entries.toArray(new WordCountListEntry[entries.size()]));
        }
    }
}
