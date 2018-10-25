package ynab.cplaner.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *  Class to read data from CSV file
 */
public class DataSeparator {
    private boolean isNotReadAlready = true;
    static final int EXIT_STATUS = -1;
    private ArrayList<Department> departments;
    static final int NOT_FOUND = -1;

    public DataSeparator() {
        departments = new ArrayList<>();
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void prepareData(IdGenerator idGenerator) {
        if (isNotReadAlready) {
            isNotReadAlready = false;
            readCsvFile(idGenerator);
        }
    }

    private void readCsvFile(IdGenerator idGenerator) {
        final String CSV_PATH = "/Users/Egor/Documents/Programming/Java/CoursePlanner/docs/c_data_2018.csv";
        final String TITLE_FIRST_TOKEN = "SEMESTER";

        try {
            rowsAnalyzer(CSV_PATH, TITLE_FIRST_TOKEN, idGenerator);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(EXIT_STATUS);
        }
    }

    public boolean isNotReadAlready() {
        return isNotReadAlready;
    }

    private void rowsAnalyzer(final String CSV_PATH, final String TITLE_FIRST_TOKEN, IdGenerator idGenerator)
            throws FileNotFoundException {

        File csv = new File(CSV_PATH);
        Scanner scanner = new Scanner(csv);
        final int TOKEN_FOR_COMPONENT = 1;


        while (scanner.hasNextLine()) {
            String row = scanner.nextLine();

            String regex = "\"";
            String replacement = "";
            String delim = ",";

            row = row.replaceAll(regex, replacement);
            StringTokenizer rowTokenizer = new StringTokenizer(row, delim);
            FindByName findByName = new FindByName();

            String semester = rowTokenizer.nextToken().trim();
            if (semester.equalsIgnoreCase(TITLE_FIRST_TOKEN)) {
                continue;
            }

            String departmentName = rowTokenizer.nextToken().trim();
            String courseNumber = rowTokenizer.nextToken().trim();
            String campus = rowTokenizer.nextToken().trim();
            String enrolmentCapacity = rowTokenizer.nextToken().trim();
            String enrolmentTotal = rowTokenizer.nextToken().trim();

            int instructorsNumber = rowTokenizer.countTokens() - TOKEN_FOR_COMPONENT;
            ArrayList<String> instructors = new ArrayList<>();
            for (int i = 0; i < instructorsNumber; i++) {
                instructors.add(rowTokenizer.nextToken().trim());
            }
            String component = rowTokenizer.nextToken().trim();

            int departmentIndex = findByName.department(departments, departmentName);
            if (departmentIndex == NOT_FOUND) {
                Department department = new Department(departmentName, semester, courseNumber, campus,
                                            enrolmentCapacity, enrolmentTotal, instructors,
                                            component, idGenerator.getDeptId(), idGenerator);
                departments.add(department);
            } else {
                departments.get(departmentIndex).addInternalToDepartment(courseNumber, semester, campus,
                        enrolmentCapacity, enrolmentTotal, instructors, component, idGenerator);
            }
        }

        scanner.close();
    }
}
