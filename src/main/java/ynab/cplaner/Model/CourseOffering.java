package ynab.cplaner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ynab.cplaner.Controllers.PlannerController;

import java.util.ArrayList;

import static ynab.cplaner.Model.DataSeparator.EXIT_STATUS;
import static ynab.cplaner.Model.DataSeparator.NOT_FOUND;
import static ynab.cplaner.Model.DumpTxtFileCreator.NO_LAST_COMA;

/**
 * Information about Course Offerings
 */
public class CourseOffering {
    private long courseOfferingId;
    private CourseNameInfo course;
    private String location;
    private String instructors;
    private String term;
    private int semesterCode;
    private int year;
    @JsonIgnore
    private ArrayList<Component> components;
    @JsonIgnore
    private ArrayList<Instructor> instructorArrayList;
    @JsonIgnore
    private FindByName findByName = new FindByName();


    CourseOffering(String semesterCode, String location, String enrolmentCapacity, String enrolmentTotal,
                   ArrayList<String> proposedInstructors, String component, long courseOfferingId) {
        setSemesterCode(semesterCode);
        this.location = location;
        this.courseOfferingId = courseOfferingId;
        this.instructors = "";
        this.year = 0;
        this.term = "";

        components = new ArrayList<>();
        addComponent(enrolmentCapacity,enrolmentTotal,component);

        instructorArrayList = new ArrayList<>();
        addInstructors(proposedInstructors);
    }


    public long getCourseOfferingId() {
        return courseOfferingId;
    }
    public CourseNameInfo getCourse() {
        return course;
    }
    public String getLocation() {
        return location;
    }
    public String getInstructors() {
        instructors = convertInstructorsToString();
        return instructors;
    }
    public String getTerm() {
        term = setTerm();
        return term;
    }
    public int getSemesterCode() {
        return semesterCode;
    }
    public int getYear() {
        year = setYear();
        return year;
    }


    @JsonIgnore
    private String convertInstructorsToString() {
        StringBuilder instructors = new StringBuilder();
        for (Instructor instructor : this.instructorArrayList) {
            instructors.append(instructor.getInstructorName());
            instructors.append(", ");
        }
        instructors = new StringBuilder(instructors.substring(0, instructors.length() - NO_LAST_COMA));
        return instructors.toString();
    }
    private String setTerm() {
        int noLastDigit = (getSemesterCode() / 10) * 10;
        int termNumber = this.semesterCode - noLastDigit;
        return PlannerController.TERMS.get(termNumber);
    }
    private int setYear() {
        int baseYear = 1900;
        int termYear = getSemesterCode() / 10;

        return baseYear + termYear;
    }

    @JsonIgnore
    public ArrayList<Component> getComponents() {
        return components;
    }

    @JsonIgnore
    private void addInstructors(ArrayList<String> proposedInstructors) {
        for (String instructor : proposedInstructors) {
            if (findByName.instructor(instructorArrayList, instructor) == NOT_FOUND) {
                addInstructor(instructor);
            }
        }
    }
    @JsonIgnore
    private void addComponent(String enrolmentCapacity, String enrolmentTotal, String componentType) {
        Component component = new Component(componentType, enrolmentCapacity, enrolmentTotal);
        components.add(component);
    }
    @JsonIgnore
    private void addInstructor(String instructorName) {
        Instructor instructor = new Instructor(instructorName);
        instructorArrayList.add(instructor);
    }

    @JsonIgnore
    public void addInternalToCourseOffering(String enrolmentCapacity, String enrolmentTotal,
                                    ArrayList<String> instructors, String component) {
        int componentIndex = findByName.component(getComponents(), component);
        if (componentIndex == NOT_FOUND) {
            addComponent(enrolmentCapacity, enrolmentTotal, component);
        } else {
            addInstructors(instructors);
            components.get(componentIndex).addEnrolmentCapacity(enrolmentCapacity);
            components.get(componentIndex).addTotalCapacity(enrolmentTotal);
        }
    }

    @JsonIgnore
    public void setCourse(long courseId, String catalogNumber) {
        this.course = new CourseNameInfo(courseId, catalogNumber);
    }
    private void setSemesterCode(String semesterCode) {
        try {
            this.semesterCode = Integer.parseInt(semesterCode);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(EXIT_STATUS);
        }
    }

    @JsonIgnore
    public ArrayList<Instructor> getInstructorArrayList() {
        return instructorArrayList;
    }
}
