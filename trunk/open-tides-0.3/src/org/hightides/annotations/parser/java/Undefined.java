/**
 * 
 */
package org.hightides.annotations.parser.java;

import java.util.List;

import org.hightides.annotations.parser.Token;
import org.hightides.annotations.parser.Tokenizer;
import org.opentides.InvalidImplementationException;

/**
 * @author allantan
 *
 */
public class Undefined implements Tokenizer {

	/* (non-Javadoc)
	 * @see org.opentides.annotations.parser.Tokenizer#doSplit(java.lang.String)
	 */
	public List<Token> doSplit(Token input) {
		throw new InvalidImplementationException("Cannot invoke doSplit() of Undefined tokenizer.");
	}

}
