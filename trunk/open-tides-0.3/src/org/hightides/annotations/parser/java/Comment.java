/**
 * 
 */
package org.hightides.annotations.parser.java;

import java.util.List;

import org.hightides.annotations.parser.Token;
import org.hightides.annotations.parser.Tokenizer;

/**
 * Extracts all block comments including javadoc.
 * @author allantan
 *
 */
public class Comment implements Tokenizer {

	/* (non-Javadoc)
	 * @see org.opentides.annotations.parser.Tokenizer#doSplit(java.lang.String)
	 */
	public List<Token> doSplit(Token input) {
//		String code = input.getValue();
//		SimpleMatcher sm = new SimpleMatcher(code, "/*", "*/");
//		while (true) {
//			String match = 
//		}
		return null;
	}

}
