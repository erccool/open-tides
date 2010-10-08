/**
 * 
 */
package org.hightides.annotations.parser;

import java.util.List;

/**
 * @author allantan
 *
 */
public interface Tokenizer {
	public List<Token> doSplit(Token input);
}
