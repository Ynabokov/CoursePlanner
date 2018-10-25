package ynab.cplaner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

import static ynab.cplaner.Model.DataSeparator.NOT_FOUND;

/**
 * Information about course
 */
public class Course {
    private long courseId;
    private String catalogNumber;
    @JsonIgnore
    private ArrayList<CourseOffering> courseOfferings;

    public Course() {}

    Course(String semester, String catalogNumber, String campus, String enrolmentCapacity,
           String enrolmentTotal, ArrayList<String> instructors,
           String component, long courseId, IdGenerator idGenerator) {
        this.catalogNumber = catalogNumber;
        courseOfferings = new ArrayList<>();
        this.courseId = courseId;


        addCourseOffering(semester, campus, enrolmentCapacity, enrolmentTotal, instructors, component, idGenerator);
    }

    public long getCourseId() {
        return courseId;
    }
    public String getCatalogNumber() {
        return catalogNumber;
    }


    @JsonIgnore
    public ArrayList<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }


    @JsonIgnore
    private void addCourseOffering(String semesterName, String campus, String enrolmentCapacity,
                             String enrolmentTotal, ArrayList<String> instructors,
                                   String component, IdGenerator idGenerator) {
        CourseOffering courseOffering = new CourseOffering (semesterName, campus, enrolmentCapacity,
                                        enrolmentTotal, instructors, component, idGenerator.getCourseOfferingsId());

        courseOffering.setCourse(courseId, catalogNumber);
        courseOfferings.add(courseOffering);
    }

    @JsonIgnore
    public void addInternalToCourse(String semester, String campus, String enrolmentCapacity, String enrolmentTotal,
                                    ArrayList<String> instructors, String component, IdGenerator idGenerator) {
        FindByName findByName = new FindByName();
        int courseOfferingIndex = findByName.courseOffering(getCourseOfferings(), campus, semester);
        if (courseOfferingIndex == NOT_FOUND) {
            addCourseOffering(semester, campus, enrolmentCapacity, enrolmentTotal, instructors, component, idGenerator);
        } else {
            courseOfferings.get(courseOfferingIndex).addInternalToCourseOffering(enrolmentCapacity, enrolmentTotal,
                                                                instructors, component);
        }
    }
}
