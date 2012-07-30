/**
 * 
 */
package org.opentides.persistence.evolve;


/**
 * @author allantan
 *
 */
public class TestEvolve extends Evolver implements DBEvolve {

    private boolean executed = false;
    private int version = 0;
    
    /**
     * @param version
     */
    public TestEvolve(int version) {
        super();
        this.version = version;
    }

    /* (non-Javadoc)
     * @see org.opentides.persistence.evolve.DBEvolve#execute()
     */
    public void execute() {
        executed = true;
    }

    /* (non-Javadoc)
     * @see org.opentides.persistence.evolve.DBEvolve#getDescription()
     */
    public String getDescription() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.opentides.persistence.evolve.DBEvolve#getVersion()
     */
    public int getVersion() {
        return version;
    }

    /**
     * Setter method for version.
     *
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Getter method for executed.
     *
     * @return the executed
     */
    public boolean isExecuted() {
        return executed;
    }
}
