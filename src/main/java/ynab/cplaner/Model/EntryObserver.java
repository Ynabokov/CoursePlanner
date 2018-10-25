package ynab.cplaner.Model;

/**
 * Interface for observers to be able to observe changes to course offerings.
 */
public interface EntryObserver {
    void stateChanged();
}
