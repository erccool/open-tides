package org.hightides.annotations.param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hightides.annotations.CheckBox;
import org.hightides.annotations.DropDown;
import org.hightides.annotations.HiddenField;
import org.hightides.annotations.RadioButton;
import org.hightides.annotations.TextArea;
import org.hightides.annotations.TextField;
import org.junit.Test;

import resources.java.TestClass;

public class BaseParamReaderTest extends TestCase{
	private static final Logger _log = Logger.getLogger(BaseParamReaderTest.class);
	private BaseParamReader reader;
	
	public BaseParamReaderTest() {
		super("BaseParamReaderTest");
	}
	
	@Override
	protected void setUp() throws Exception {
		reader = new BaseParamReader();
	}
	
	@Test
	public void testGetAllAnnotatedFields(){
		Class[] annotationList = {TextField.class,
				DropDown.class,
				TextArea.class,
				CheckBox.class,
				RadioButton.class,
				HiddenField.class};
		int expected = 0;
		try {
			Class clazz = Class.forName("resources.java.TestClass");
			Field[] fields = clazz.getDeclaredFields();
			for(Field f:fields){
				Annotation[] annotations = f.getAnnotations();
				for(Annotation a:annotations){
					for(Class c:annotationList){
						if(c.isAssignableFrom(a.getClass())){
							expected++;
							break;
						}
					}				
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		int actual = reader.getAllAnnotatedFields(TestClass.class).size();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetStandardParams(){
		List<Field> fields = reader.getAllAnnotatedFields(TestClass.class);
		Map<String, Object> params = reader.getStandardParams(fields.get(0));
		assertEquals("name", String.valueOf(params.get("fieldName")));
		assertEquals("getName", String.valueOf(params.get("getterName")));
		assertEquals("setName", String.valueOf(params.get("setterName")));
		assertEquals("Name", params.get("fieldLabel"));
	}
}
