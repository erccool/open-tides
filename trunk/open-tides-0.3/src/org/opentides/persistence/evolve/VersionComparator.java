package org.opentides.persistence.evolve;

import java.util.Comparator;

import org.opentides.persistence.evolve.DBEvolve;


public class VersionComparator implements Comparator<DBEvolve> {
	public int compare(DBEvolve o1, DBEvolve o2) {
		return o1.getVersion()-o2.getVersion();
	}
}