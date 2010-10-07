/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
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
