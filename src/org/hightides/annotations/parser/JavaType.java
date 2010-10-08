/**
 * 
 */
package org.hightides.annotations.parser;

import org.hightides.annotations.parser.java.Comment;
import org.hightides.annotations.parser.java.Expression;
import org.hightides.annotations.parser.java.InlineComment;
import org.hightides.annotations.parser.java.Operator;
import org.hightides.annotations.parser.java.Separator;
import org.hightides.annotations.parser.java.Undefined;

/**
 * Different types of token parsed in a Java program.
 * @author allantan
 *
 */
public enum JavaType {
	UNDEFINED("Undefined", 0, Undefined.class),					// undefined
	COMMENT("Block Comment", 1, Comment.class),					// block comments	
	INLINE_COMMENT("Inline Comment", 2, InlineComment.class),	// inline comments
	EXPRESSION("String Expression", 3, Expression.class),		// string expression
	OPERATOR("Operator", 4, Operator.class),	 				// operators including postfix, unary, multiplicative
	SEPARATOR("Separator", 5, Separator.class);					// separators including , ; 
		
	private String name;
	private int	level;
	private Class<? extends Tokenizer>  tokenizer;

	/**
	 * JavaType Constructor
	 * @param name
	 * @param level
	 * @param tokenizer
	 */
	JavaType(String name, int level, Class<? extends Tokenizer> tokenizer) {
		this.name = name;
		this.level = level;
		this.tokenizer = tokenizer;
	}
	
	/**
	 * Returns the name of the JavaType.
	 * @return
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Returns the level of the JavaType 
	 * @return the level
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * Returns the Tokenizer class for this JavaType
	 * @return the tokenizer
	 */
	public final Class<? extends Tokenizer> getTokenizer() {
		return tokenizer;
	}
}
