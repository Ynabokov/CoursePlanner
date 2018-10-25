package ynab.cplaner.Controllers;

import ynab.cplaner.Model.*;
import ynab.cplaner.Model.*;
import ynab.cplaner.Model.Comparators.CourseOfferingsSortBySemesterCode;
import ynab.cplaner.Model.Comparators.CourseSortByCatalogNumber;
import ynab.cplaner.Model.Comparators.DepartmentSortByDepartmentName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main Controller for CoursePlanner app
 */
@RestController
public class PlannerController {
    private final String AUTHOR = "Yehor Nabokov";
    private final String NAME = "Course Planner App";

    private DataSeparator dataSeparator = new DataSeparator();
    private DumpTxtFileCreator dumpTxtFileCreator = new DumpTxtFileCreator();
    private CourseOfferingsAdder courseOfferingsAdder = new CourseOfferingsAdder();
    private AppName appName = new AppName(NAME, AUTHOR);
    public final static HashMap<Integer, String> TERMS = new HashMap<>();
    private IdGenerator idGenerator = new IdGenerator();
    private ArrayList<Watcher> watchers = new ArrayList<>();

    @GetMapping("/api/about")
    public AppName getAppInfo() {
        return appName;
    }

    @GetMapping("/api/dump-model")
    public void getDumpModel() {
        checkForData();
        dumpTxtFileCreator.printInfo(dataSeparator.getDepartments());
    }

    @GetMapping("/api/departments")
    public List<Department> getDepartments() {
        checkForData();
        dataSeparator.getDepartments().sort(new DepartmentSortByDepartmentName());
        return dataSeparator.getDepartments();
    }

    @GetMapping("/api/departments/{id}/courses")
    public List<Course> getCourses(@PathVariable("id") long deptId) {
        checkForData();
        ArrayList<Department> departments = dataSeparator.getDepartments();
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                department.getCourses().sort(new CourseSortByCatalogNumber());
                return department.getCourses();
            }
        }
        throw new NotFoundException("Department of ID " + deptId + " not found.");
    }

    @GetMapping("/api/departments/{dept_id}/courses/{course_id}/offerings")
    public List<CourseOffering> getOfferings(@PathVariable("dept_id") long deptId,
                                   @PathVariable("course_id") long courseId) {
        checkForData();

        ArrayList<Department> departments = dataSeparator.getDepartments();
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                for (Course course : department.getCourses()) {
                    if (course.getCourseId() == courseId) {
                        course.getCourseOfferings().sort(new CourseOfferingsSortBySemesterCode());
                        return course.getCourseOfferings();
                    }
                }
                throw new NotFoundException("Course of ID " + courseId + " not found.");
            }
        }
        throw new NotFoundException("Department of ID " + deptId + " not found.");
    }

    @GetMapping("/api/departments/{dept_id}/courses/{course_id}/offerings/{offer_id}")
    public List<Component> getComponents(@PathVariable("dept_id") long deptId,
                                         @PathVariable("course_id") long courseId,
                                         @PathVariable("offer_id") long offerId) {
        checkForData();

        ArrayList<Department> departments = dataSeparator.getDepartments();
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                for (Course course : department.getCourses()) {
                    if (course.getCourseId() == courseId) {
                        for (CourseOffering courseOffering : course.getCourseOfferings()) {
                            if (courseOffering.getCourseOfferingId() == offerId) {
                                return courseOffering.getComponents();
                            }
                        }
                        throw new NotFoundException("Course offering of ID " + offerId + " not found.");
                    }
                }
                throw new NotFoundException("Course of ID " + courseId + " not found.");
            }
        }
        throw new NotFoundException("Department of ID " + deptId + " not found.");
    }


    @GetMapping("/api/stats/students-per-semester")
    public List<Semester> getStatPerSemester(@RequestParam("deptId") long deptId) {
        checkForData();
        ArrayList<Department> departments = dataSeparator.getDepartments();

        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                department.prepareStatForGraph();
                return department.getSemestersStat();
            }
        }
        throw new NotFoundException("Department of ID " + deptId + " not found.");
    }


    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public Component createNewOffering(@RequestBody OfferingEntry offeringEntry) {

        courseOfferingsAdder.addOffering(offeringEntry, idGenerator, dataSeparator);

        return new Component(offeringEntry.getComponent(), offeringEntry.getEnrollmentCap(),
                offeringEntry.getEnrollmentTotal());
    }

    @GetMapping("/api/watchers")
    public List<Watcher> getWatchers() {
        return watchers;
    }

    @GetMapping("/api/watchers/{watcherId}")
    public Watcher getWatcher(@PathVariable("watcherId") long watcherId) {
        for (Watcher watcher : watchers) {
            if (watcher.getId() == watcherId) {
                return watcher;
            }
        }
        throw new NotFoundException("Unable to find requested watcher.");
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public Watcher createNewWatcher(@RequestBody InfoForNewWatcher infoForNewWatcher) {
        Department department = new Department();
        Course course = new Course();

        checkForData();

        for (Department departm : dataSeparator.getDepartments()) {
            if (departm.getDeptId() == infoForNewWatcher.getDeptId()) {
                department = departm;
            }
        }
        for (Course course1 : department.getCourses()) {
            if (course1.getCourseId() == infoForNewWatcher.getCourseId()) {
                course = course1;
            }
        }

        Watcher watcher = new Watcher(idGenerator.getWatcherId(), department, course, courseOfferingsAdder);

        watchers.add(watcher);
        return watcher;
    }

    @DeleteMapping("/api/watchers/{watcherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("watcherId") long watcherId) {
        for (Watcher watcher : watchers) {
            if (watcher.getId() == watcherId) {
                watchers.remove(watcher);
                return;
            }
        }
        throw new NotFoundException("Unable to find requested watcher.");
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NotFoundException extends IllegalArgumentException {
        public NotFoundException(String Msg) {
            super(Msg);
        }
    }

    private void checkForData() {
        if (dataSeparator.isNotReadAlready()) {
            TERMS.put(1, "Spring");
            TERMS.put(4, "Summer");
            TERMS.put(7, "Fall");
            dataSeparator.prepareData(this.idGenerator);
        }
    }
}
