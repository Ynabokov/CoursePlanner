package ynab.cplaner.Model;

/**
 * Class to get information from the user about course offering and to add the offering to base.
 */
public class OfferingEntry {
    private String semester;
    private String subjectName;
    private String catalogNumber;
    private String location;
    private String enrollmentCap;
    private String component;
    private String enrollmentTotal;
    private String instructor;

    OfferingEntry() {}

    OfferingEntry(String semester, String subjectName,
                         String catalogNumber, String location, String enrollmentCap,
                         String component, String enrollmentTotal, String instructor) {
        this.semester = semester;
        this.subjectName = subjectName;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.enrollmentCap = enrollmentCap;
        this.component = component;
        this.enrollmentTotal = enrollmentTotal;
        this.instructor = instructor;
    }


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(String enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(String enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
