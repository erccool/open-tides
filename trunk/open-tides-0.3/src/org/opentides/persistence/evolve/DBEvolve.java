/**
 * 
 */
package org.opentides.persistence.evolve;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author allantan
 *
 */
public interface DBEvolve {
	/**
	 * Actual database evolve script operations.
	 */
	@Transactional
	public void execute();
	/**
	 * Returns the description of evolve script.
	 * @return
	 */
	public String getDescription();
	/**
	 * Returns the version of this evolve script.
	 * Ensures that execution of evolve script is in proper
	 * sequence.
	 * @return
	 */
	public int getVersion();
}
