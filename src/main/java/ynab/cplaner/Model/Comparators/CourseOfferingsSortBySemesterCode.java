package ynab.cplaner.Model.Comparators;

import ynab.cplaner.Model.CourseOffering;

import java.util.Comparator;

/**
 * Implements comparator to sort course offerings by their semester code
 */
public class CourseOfferingsSortBySemesterCode implements Comparator<CourseOffering> {

    @Override
    public int compare(CourseOffering courseOffering1, CourseOffering courseOffering2) {
        return courseOffering1.getSemesterCode() - courseOffering2.getSemesterCode();
    }
}
