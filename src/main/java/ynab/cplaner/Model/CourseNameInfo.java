package ynab.cplaner.Model;

public class CourseNameInfo {
    private long courseId;
    private String catalogNumber;

    CourseNameInfo(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }
}
