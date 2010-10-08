/**
 * 
 */
package org.hightides.comparator;

import java.util.Comparator;

import org.hightides.annotations.parser.Token;

/**
 * @author allantan
 *
 */
public class TokenComparator implements Comparator<Token> {

	public int compare(Token o1, Token o2) {
		if (o1.equals(o2))
			return 0;
		else 
			return -1;
	}

}
