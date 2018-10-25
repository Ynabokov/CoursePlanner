package ynab.cplaner.Model;

import java.util.ArrayList;
import java.util.List;

import static ynab.cplaner.Model.DataSeparator.NOT_FOUND;

/**
 * Class to add new Course Offerings to database.
 */
public class CourseOfferingsAdder {
    private List<EntryObserver> observers = new ArrayList<>();
    private OfferingEntry lastOfferingEntry;

    public CourseOfferingsAdder() {}

    public void addOffering(OfferingEntry offeringEntry, IdGenerator idGenerator, DataSeparator dataSeparator) {
        lastOfferingEntry = offeringEntry;
        FindByName findByName = new FindByName();
        String semester = offeringEntry.getSemester();
        String subjectName = offeringEntry.getSubjectName();
        String catalogNumber = offeringEntry.getCatalogNumber();
        String location = offeringEntry.getLocation();
        String enrollmentCap = offeringEntry.getEnrollmentCap();
        String component = offeringEntry.getComponent();
        String enrollmentTotal = offeringEntry.getEnrollmentTotal();
        String instructor = offeringEntry.getInstructor();

        ArrayList<String> instructors = new ArrayList<>();
        instructors.add(instructor);
        int departmentIndex = findByName.department(dataSeparator.getDepartments(), subjectName);
        if (departmentIndex == NOT_FOUND) {
            Department department = new Department(subjectName, semester, catalogNumber, location,
                    enrollmentCap, enrollmentTotal, instructors,
                    component, idGenerator.getDeptId(), idGenerator);
            dataSeparator.getDepartments().add(department);
        } else {
            dataSeparator.getDepartments().get(departmentIndex).
                    addInternalToDepartment(catalogNumber, semester, location,
                            enrollmentCap, enrollmentTotal, instructors, component, idGenerator);
        }
        notifyObservers();
    }


    public OfferingEntry getLastOfferingEntry() {
        return lastOfferingEntry;
    }

    public void addObserver(EntryObserver observer) {
        observers.add(observer);
    }
    private void notifyObservers() {
        for (EntryObserver observer : observers) {
            observer.stateChanged();
        }
    }
}
