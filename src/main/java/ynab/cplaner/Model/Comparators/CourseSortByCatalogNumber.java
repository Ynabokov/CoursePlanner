package ynab.cplaner.Model.Comparators;

import ynab.cplaner.Model.Course;

import java.util.Comparator;

/**
 * Implements comparator to sort courses by their catalog number.
 */
public class CourseSortByCatalogNumber implements Comparator<Course> {

    private static final int COURSE_NUMBER_MIN_SIZE = 3;

    @Override
    public int compare(Course course1, Course course2) {
        String courseNumber1 = course1.getCatalogNumber();
        String courseNumber2 = course2.getCatalogNumber();
        for (int i = 0; i < COURSE_NUMBER_MIN_SIZE; i++) {
            char number1 = courseNumber1.charAt(i);
            char number2 = courseNumber2.charAt(i);
            if (number1 != number2) {
                return number1 - number2;
            }
        }
        return 0;
    }
}
