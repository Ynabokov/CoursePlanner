package ynab.cplaner.Model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Class to generate id for different parts of the system
 */
public class IdGenerator {
    private AtomicLong deptId = new AtomicLong();
    private AtomicLong courseId = new AtomicLong();
    private AtomicLong courseOfferingsId = new AtomicLong();
    private AtomicLong watcherId = new AtomicLong();

    public IdGenerator() {}

    public long getDeptId() {
        return deptId.incrementAndGet();
    }

    public long getCourseId() {
        return courseId.incrementAndGet();
    }

    public long getCourseOfferingsId() {
        return courseOfferingsId.incrementAndGet();
    }

    public long getWatcherId() {
        return watcherId.incrementAndGet();
    }
}

