package ynab.cplaner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static ynab.cplaner.Model.DataSeparator.EXIT_STATUS;

/**
 * Information about course component
 */
public class Component {
    private String type;
    private int enrollmentCap = 0;
    private int enrollmentTotal = 0;

    public Component(String type, String enrolmentCapacity, String enrollmentTotal) {
        this.type = type;
        addEnrolmentCapacity(enrolmentCapacity);
        addTotalCapacity(enrollmentTotal);
    }

    public String getType() {
        return type;
    }
    public int getEnrollmentCap() {
        return enrollmentCap;
    }
    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    @JsonIgnore
    public void addEnrolmentCapacity(String enrolmentCapacity) {
        try {
            this.enrollmentCap += Integer.parseInt(enrolmentCapacity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(EXIT_STATUS);
        }
    }
    @JsonIgnore
    public void addTotalCapacity(String totalCapacity) {
        try {
            this.enrollmentTotal += Integer.parseInt(totalCapacity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(EXIT_STATUS);
        }
    }
}
