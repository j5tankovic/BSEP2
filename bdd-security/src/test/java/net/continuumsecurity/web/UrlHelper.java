package net.continuumsecurity.web;

public class UrlHelper {

    private int courseId;
    private int announcementId;
    private boolean used;

    public UrlHelper(int courseId) {
        this.courseId = courseId;
        this.announcementId = -1;
        this.used = false;
    }

    public UrlHelper(int courseId, int announcementId) {
        this.courseId = courseId;
        this.announcementId = announcementId;
        this.used = false;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int[] getIUrlItemsAsArray() {
        return new int[]{this.courseId, this.announcementId};
    }

    @Override
    public String toString() {
        return "UrlHelper{" +
                "courseId=" + courseId +
                ", announcementId=" + announcementId +
                ", used=" + used +
                '}';
    }
}
