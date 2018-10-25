package ynab.cplaner.Model;

import ynab.cplaner.Model.Comparators.CourseOfferingsSortBySemesterCode;
import ynab.cplaner.Model.Comparators.CourseSortByCatalogNumber;
import ynab.cplaner.Model.Comparators.DepartmentSortByDepartmentName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class to print dump information
 */
public class DumpTxtFileCreator {

    static final int NO_LAST_COMA = 2;

    public void printInfo(ArrayList<Department> departments) {
        String targetPath = "/Users/Egor/Downloads/output_dump.txt";
        File targetFile = new File(targetPath);

        try {
            PrintWriter writer = new PrintWriter(targetFile);

            departments.sort(new DepartmentSortByDepartmentName());
            for (Department department : departments) {
                printAdditionalInfo(department, writer);
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printAdditionalInfo(Department department, PrintWriter writer) {
        department.getCourses().sort(new CourseSortByCatalogNumber());
        for (Course course: department.getCourses()) {
            System.out.print(department.getName() + " " + course.getCatalogNumber() + "\n");
            writer.print(department.getName() + " " + course.getCatalogNumber() + "\n");

            course.getCourseOfferings().sort(new CourseOfferingsSortBySemesterCode());
            for (CourseOffering courseOffering : course.getCourseOfferings()) {
                printCampusInfo(courseOffering, writer);
            }
        }
    }

    private void printCampusInfo(CourseOffering courseOffering, PrintWriter writer) {
        System.out.print("\t");
        System.out.print(courseOffering.getSemesterCode() + " in " + courseOffering.getLocation() + " by ");
        writer.print("\t");
        writer.print(courseOffering.getSemesterCode() + " in " + courseOffering.getLocation() + " by ");

        String instructors = courseOffering.getInstructors();
        System.out.println(instructors);
        writer.println(instructors);

        for (Component component : courseOffering.getComponents()) {
            System.out.print("\t\t");
            System.out.println("Type=" + component.getType() + ", Enrollment="
                    + component.getEnrollmentTotal() + "/" + component.getEnrollmentCap());
            writer.print("\t\t");
            writer.println("Type=" + component.getType() + ", Enrollment="
                    + component.getEnrollmentTotal() + "/" + component.getEnrollmentCap());
        }
    }
}
