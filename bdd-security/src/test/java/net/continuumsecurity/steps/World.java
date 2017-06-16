package net.continuumsecurity.steps;

import edu.umass.cs.benchlab.har.HarEntry;
import net.continuumsecurity.Credentials;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.jsslyze.JSSLyze;
import net.continuumsecurity.web.UrlHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stephen on 04/03/2016.
 *
 * Singleton to store shared state between scenarios
 */
public class World {
    private static World ourInstance = new World();
    Map<String, List<HarEntry>> methodProxyMap = new HashMap<String, List<HarEntry>>();
    private Map<String, String> sessionIds = new HashMap<>();
    private List<HarEntry> recordedEntries;
    private boolean httpHeadersRecorded = false;
    private boolean navigated;
    private boolean spidered;
    private Credentials credentials;
    private boolean sslRunCompleted;
    private HarEntry currentHar;
    private JSSLyze jSSLyze;
    private Map<String, List<UrlHelper>> urlOptions = new HashMap<>();

    public synchronized Map<String, List<HarEntry>> getMethodProxyMap() {
        return methodProxyMap;
    }

    public boolean isNavigated() {
        return navigated;
    }

    public void setNavigated(boolean navigated) {
        this.navigated = navigated;
    }

    public boolean isSpidered() {
        return spidered;
    }

    public void setSpidered(boolean spidered) {
        this.spidered = spidered;
    }

    public static World getInstance() {
        return ourInstance;
    }

    private World() {
        this.initializeUrlOptionsMap();
    }

    public JSSLyze getjSSLyze() {
        return jSSLyze;
    }

    public void setjSSLyze(JSSLyze jSSLyze) {
        this.jSSLyze = jSSLyze;
    }

    public Map<String, String> getSessionIds() {
        return sessionIds;
    }

    public void setSessionIds(Map<String, String> sessionIds) {
        this.sessionIds = sessionIds;
    }

    public List<HarEntry> getRecordedEntries() {
        return recordedEntries;
    }

    public void setRecordedEntries(List<HarEntry> recordedEntries) {
        this.recordedEntries = recordedEntries;
    }

    public boolean isHttpHeadersRecorded() {
        return httpHeadersRecorded;
    }

    public void setHttpHeadersRecorded(boolean httpHeadersRecorded) {
        this.httpHeadersRecorded = httpHeadersRecorded;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public UserPassCredentials getUserPassCredentials() {
        return (UserPassCredentials)credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public boolean isSslRunCompleted() {
        return sslRunCompleted;
    }

    public void setSslRunCompleted(boolean sslRunCompleted) {
        this.sslRunCompleted = sslRunCompleted;
    }

    public HarEntry getCurrentHar() {
        return currentHar;
    }

    public void setCurrentHar(HarEntry currentHar) {
        this.currentHar = currentHar;
    }

    public void initializeUrlOptionsMap() {
        List<UrlHelper> addAnnouncementUrls = new ArrayList<>();
        addAnnouncementUrls.add(new UrlHelper(1));

        List<UrlHelper> editAnnouncementUrls = new ArrayList<>();
        editAnnouncementUrls.add(new UrlHelper(1, 1));

        List<UrlHelper> deleteAnnouncementUrls = new ArrayList<>();
        deleteAnnouncementUrls.add(new UrlHelper(1, 2));
        deleteAnnouncementUrls.add(new UrlHelper(1, 3));
        deleteAnnouncementUrls.add(new UrlHelper(1, 4));
        deleteAnnouncementUrls.add(new UrlHelper(1, 5));
        deleteAnnouncementUrls.add(new UrlHelper(1, 6));
        deleteAnnouncementUrls.add(new UrlHelper(1, 7));

        this.urlOptions.put("addAnnouncement", addAnnouncementUrls);
        this.urlOptions.put("editAnnouncement", editAnnouncementUrls);
        this.urlOptions.put("deleteAnnouncement", deleteAnnouncementUrls);
    }

    public Map<String, List<UrlHelper>>  getUrlOptionsMap() {
        return this.urlOptions;
    }






}
