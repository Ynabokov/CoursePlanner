package ynab.cplaner.Model;

/**
 * Class to store app information
 */
public class AppName {
    private String appName;
    private String authorName;

    public AppName() {
    }

    public AppName(String appName, String authorName) {
        this.appName = appName;
        this.authorName = authorName;
    }

    public String getAppName() {
        return appName;
    }

    public String getAuthorName() {
        return authorName;
    }
}
