package ynab.cplaner.Model;

import ynab.cplaner.Model.Comparators.SemestersSortBySemesterCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Information about department
 */
public class Department {
    private long deptId;
    private String name;
    @JsonIgnore
    private ArrayList<Course> courses;
    @JsonIgnore
    private ArrayList<Semester> semestersStat;
    @JsonIgnore
    private FindByName findByName = new FindByName();

    public Department(){}

    Department(String name, String semester, String courseNumber, String campus,
               String enrolmentCapacity, String enrolmentTotal, ArrayList<String> instructors,
               String component, long deptId, IdGenerator idGenerator) {
        this.name = name;
        this.courses = new ArrayList<>();
        this.deptId = deptId;
        this.semestersStat = new ArrayList<>();

        addCourse(semester, courseNumber, campus, enrolmentCapacity,
                enrolmentTotal, instructors, component, idGenerator);
        addSemesterStat(semester, enrolmentTotal, component);
    }

    public long getDeptId() {
        return deptId;
    }
    public String getName() {
        return name;
    }


    @JsonIgnore
    public ArrayList<Semester> getSemestersStat() {
        return semestersStat;
    }
    @JsonIgnore
    public ArrayList<Course> getCourses() {
        return courses;
    }

    @JsonIgnore
    private void addCourse(String semester, String courseNumber, String campus, String enrolmentCapacity,
                           String enrolmentTotal, ArrayList<String> instructors,
                           String component, IdGenerator idGenerator) {
        Course course = new Course(semester, courseNumber, campus, enrolmentCapacity,
                enrolmentTotal, instructors, component, idGenerator.getCourseId(), idGenerator);
        courses.add(course);
    }
    @JsonIgnore
    private void addSemesterStat(String semesterCodeString, String enrollmentTotalString, String component) {
        int semesterCode = 0;
        int enrollmentTotal = 0;
        final int NOT_FOUND = -1;
        final String APPROPRIATE_COMPONENT_NAME = "LEC";

        if (!(component.equalsIgnoreCase(APPROPRIATE_COMPONENT_NAME))) {
            return;
        }

        try {
            semesterCode = Integer.parseInt(semesterCodeString);
            enrollmentTotal = Integer.parseInt(enrollmentTotalString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(DataSeparator.EXIT_STATUS);
        }

        int semesterIndex = findByName.semester(this.semestersStat, semesterCode);
        if(semesterIndex == NOT_FOUND) {
            Semester semester = new Semester(semesterCode, enrollmentTotal);
            this.semestersStat.add(semester);
        } else {
            this.semestersStat.get(semesterIndex).addTotalCoursesTaken(enrollmentTotal);
        }
    }

    @JsonIgnore
    public void addInternalToDepartment (String courseNumber, String semester, String campus, String enrolmentCapacity,
                                         String enrolmentTotal, ArrayList<String> instructors,
                                         String component, IdGenerator idGenerator) {

        addSemesterStat(semester, enrolmentTotal, component);

        int courseIndex = findByName.course(getCourses(), courseNumber);
        if (courseIndex == DataSeparator.NOT_FOUND) {
            addCourse(semester, courseNumber, campus, enrolmentCapacity, enrolmentTotal,
                    instructors, component, idGenerator);
        } else {
            courses.get(courseIndex).addInternalToCourse(semester, campus, enrolmentCapacity,
                                                        enrolmentTotal, instructors, component, idGenerator);
        }
    }

    @JsonIgnore
    public void prepareStatForGraph() {
        if (this.semestersStat.isEmpty()) {
            return;
        }

        this.semestersStat.sort(new SemestersSortBySemesterCode());
        final int NOT_FOUND = -1;
        final int NEW_SEMESTER = 3;
        final int NEW_YEAR = 1;
        final int NO_ENROLLMENT = 0;
        int firstIndex = 0;
        int lastIndex = semestersStat.size() - 1;
        int firstTerm = semestersStat.get(firstIndex).getSemesterCode();
        int finalTerm = semestersStat.get(lastIndex).getSemesterCode();

        int term = firstTerm;
        while (term != finalTerm) {
            if (findByName.semester(semestersStat, term) == NOT_FOUND) {
                Semester semester = new Semester(term, NO_ENROLLMENT);
                this.semestersStat.add(semester);
            }
            term += NEW_SEMESTER;
            if (term % 10 == 0) {
                term += NEW_YEAR;
            }
        }

        this.semestersStat.sort(new SemestersSortBySemesterCode());
    }
}
