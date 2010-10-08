package org.hightides.annotations.bean;

public enum SyncMode {
	CREATE, 	// Create the source during sync if not exist. If file exist, no processing performed.
	REPLACE,	// Overwrites the source duing sync. If file exist, it will be replaced with generated code.
	RETAIN 		// Keeps the changes and replace generated codes
}
