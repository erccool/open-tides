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

package org.hightides.annotations.parser.java;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hightides.annotations.parser.JavaType;
import org.hightides.annotations.parser.Token;
import org.hightides.annotations.parser.Tokenizer;

/**
 * This is the main tokenizer for Java programs.
 * MainSource invokes all other tokenizers to breakdown
 * Java programs into its smallest portion.
 * 
 * @author allantan
 */
public class MainSource implements Tokenizer {
	
	private static Logger _log = Logger.getLogger(MainSource.class);

	public List<Token> doSplit(Token input) {
		// MainSource is the main parser, so let's create the 
		// initial tokens from input
		List<Token> tokens = new ArrayList<Token>();
		tokens.add(input);
		for (JavaType type:JavaType.values()) {
			// skip UNDEFINED type
			if (type==JavaType.UNDEFINED)
				continue;
			List<Token> updates = new ArrayList<Token>();
			Class<? extends Tokenizer> clazz = type.getTokenizer();		
			try {
				// update all tokens that are yet undefined
				Tokenizer tokenizer = (Tokenizer) clazz.newInstance();
				for (Token token:tokens) {
					if (token.getType()==JavaType.UNDEFINED) {
						List<Token> split = tokenizer.doSplit(token);
						updates.addAll(split);
					} else
						updates.add(token);
				}
				// now apply the updates to the list
				tokens = updates;
			} catch (InstantiationException e) {
				_log.error("Failed to instantiate tokenizer ["+clazz.getName()+"]. Skipping this tokenizer...",e);
				continue;
			} catch (IllegalAccessException e) {
				_log.error("Failed to instantiate tokenizer ["+clazz.getName()+"]. Skipping this tokenizer...",e);
				continue;
			} catch (Exception e) {
				_log.error("Failed to process tokenizer ["+clazz.getName()+"].",e);
				continue;				
			}
		}
		return tokens;
	}
}
