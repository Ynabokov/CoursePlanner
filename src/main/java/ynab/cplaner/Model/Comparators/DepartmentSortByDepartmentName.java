package ynab.cplaner.Model.Comparators;

import ynab.cplaner.Model.Department;

import java.util.Comparator;

/**
 * Sorting list of departments by their name.
 */
public class DepartmentSortByDepartmentName implements Comparator<Department> {
    private static final int DEPARTMENT_NUMBER_MIN_SIZE = 4;

    @Override
    public int compare(Department department1, Department department2) {
        String departmentName1 = department1.getName();
        String departmentName2 = department2.getName();

        for (int i = 0; i < DEPARTMENT_NUMBER_MIN_SIZE; i++) {
            char character1 = departmentName1.charAt(i);
            char character2 = departmentName2.charAt(i);
            if (character1 != character2) {
                return character1 - character2;
            }
        }
        return 0;
    }
}
