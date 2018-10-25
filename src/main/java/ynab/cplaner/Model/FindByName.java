package ynab.cplaner.Model;

import java.util.ArrayList;

import static ynab.cplaner.Model.DataSeparator.EXIT_STATUS;

/**
 * Implements search methods for different kinds of ArrayList.
 */
public class FindByName {
    private final int NOTHING = -1;

    public int department(ArrayList<Department> departments, String impDepartment) {
        int index = 0;
        for (Department department : departments) {
            if (department.getName().equals(impDepartment)) {
                return index;
            }
            index++;
        }
        return NOTHING;
    }

    public int course(ArrayList<Course> courses, String impCourse) {
        int index = 0;
        for (Course course : courses) {
            if (impCourse.equals(course.getCatalogNumber())) {
                return index;
            }
            index++;
        }
        return NOTHING;
    }
    public int courseOffering(ArrayList<CourseOffering> courseOfferings, String impCampus, String impSemester) {
        int index = 0;
        int impSemesterCode = 0;
        try {
            impSemesterCode= Integer.parseInt(impSemester);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(EXIT_STATUS);
        }

        for (CourseOffering courseOffering : courseOfferings) {
            if (impCampus.equals(courseOffering.getLocation()) &&
                    (impSemesterCode == courseOffering.getSemesterCode())) {
                return index;
            }
            index++;
        }
        return NOTHING;
    }
    public int instructor(ArrayList<Instructor> instructors, String impInstructor) {
        int index = 0;
        for (Instructor instructor : instructors) {
            if (impInstructor.equals(instructor.getInstructorName())) {
                return index;
            }
            index++;
        }
        return NOTHING;
    }

    public int component(ArrayList<Component> components, String impComponent) {
        int index = 0;
        for (Component component : components) {
            if (impComponent.equals(component.getType())) {
                return index;
            }
            index++;
        }
        return NOTHING;
    }

    public int semester(ArrayList<Semester> semesters, int impSemesterCode) {
        int index = 0;

        for (Semester semester : semesters) {
            if (impSemesterCode == semester.getSemesterCode()) {
                return index;
            }
            index++;
        }
        return NOTHING;
    }
}
