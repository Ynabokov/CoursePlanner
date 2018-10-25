package ynab.cplaner.Model;

/**
 * Class to read info about course, which needs a watcher
 */
public class InfoForNewWatcher {
    private long deptId;
    private long courseId;

    public InfoForNewWatcher() {}

    public InfoForNewWatcher(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getDeptId() {
        return deptId;
    }

    public long getCourseId() {
        return courseId;
    }
}
