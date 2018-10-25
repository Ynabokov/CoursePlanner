package ynab.cplaner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class to store all info about semester.
 */
public class Semester {
    private int semesterCode;
    private int totalCoursesTaken;

    Semester(int semesterCode, int totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }
    @JsonIgnore
    public void addTotalCoursesTaken(int totalCoursesTaken) {
        this.totalCoursesTaken += totalCoursesTaken;
    }
}
