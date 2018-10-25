package ynab.cplaner.Model.Comparators;

import ynab.cplaner.Model.Semester;

import java.util.Comparator;

/**
 * Class to sort semester information by semester code
 */
public class SemestersSortBySemesterCode implements Comparator<Semester> {

    @Override
    public int compare(Semester semester1, Semester semester2) {
        return semester1.getSemesterCode() - semester2.getSemesterCode();
    }
}
