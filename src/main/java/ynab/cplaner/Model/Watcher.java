package ynab.cplaner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ynab.cplaner.Controllers.PlannerController;

import java.util.ArrayList;
import java.util.Date;


/**
 * Class to observe changes of distinct course
 */
public class Watcher {
    private long id;
    private Department department;
    private Course course;
    private ArrayList<String> events;
    @JsonIgnore
    private CourseOfferingsAdder courseOfferingsAdder;

    public Watcher(long id, Department department, Course course, CourseOfferingsAdder courseOfferingsAdder) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.events = new ArrayList<>();
        this.courseOfferingsAdder = courseOfferingsAdder;

        registerAsObserver();
    }

    public long getId() {
        return id;
    }
    public Department getDepartment() {
        return department;
    }
    public Course getCourse() {
        return course;
    }
    public ArrayList<String> getEvents() {
        return events;
    }


    @JsonIgnore
    private void registerAsObserver() {
        courseOfferingsAdder.addObserver(() -> updateEvents());
    }

    @JsonIgnore
    private void updateEvents() {
        OfferingEntry lastOfferingEntry = courseOfferingsAdder.getLastOfferingEntry();
        if (!isChangeable(lastOfferingEntry)) {
            return;
        }

        String date = String.valueOf(new Date());
        String section = lastOfferingEntry.getComponent();
        String total = lastOfferingEntry.getEnrollmentTotal();
        String cap = lastOfferingEntry.getEnrollmentCap();

        int semesterCode = 0;
        try {
            semesterCode = Integer.parseInt(lastOfferingEntry.getSemester());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(DataSeparator.EXIT_STATUS);
        }

        int baseYear = 1900;
        int termYear = semesterCode / 10;
        String year = String.valueOf(baseYear + termYear);

        int noLastDigit = termYear * 10;
        int termNumber = semesterCode - noLastDigit;
        String term = PlannerController.TERMS.get(termNumber);

        events.add(date + ": Added section " + section + " with enrollment (" +
                total + " / " + cap + ") to offering " + term + " " + year);
    }

    @JsonIgnore
    private boolean isChangeable(OfferingEntry lastOfferingEntry) {
        return (lastOfferingEntry.getSubjectName().equalsIgnoreCase(department.getName()))
                && (lastOfferingEntry.getCatalogNumber().equalsIgnoreCase(course.getCatalogNumber()));
    }
}
