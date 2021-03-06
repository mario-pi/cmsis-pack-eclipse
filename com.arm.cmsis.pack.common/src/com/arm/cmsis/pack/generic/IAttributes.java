/*******************************************************************************
* Copyright (c) 2015 ARM Ltd. and others
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* ARM Ltd and ARM Germany GmbH - Initial API and implementation
*******************************************************************************/

package com.arm.cmsis.pack.generic;

import java.util.Map;
import java.util.Map.Entry;

import com.arm.cmsis.pack.common.CmsisConstants;
import com.arm.cmsis.pack.utils.Utils;

/**
 * Interface describing an item that has String attributes map. 
 */
public interface IAttributes {
	
	/**
	 * Checks if element has attributes
	 * @return true if element has non-empty attributes map
	 */
	boolean hasAttributes();

	/**
	 *  Clears the internal collection
	 */
	void clear();

	/**
	 * Returns all attributes of this element as a Map<String,String>  
	 * @return the attributes as a Map
	 */
	Map<String, String> getAttributesAsMap();
	

	/**
	 * Parses supplied string into collection of attributes and sets it to this element overwriting existing collection
	 * @param attributesString string in the form <code>key1="value1", key2="vaule2", ...</code>
	 */
	void setAttributes(String attributesString);

	/**
	 * Puts collection of attributes to this element overwriting existing collection
	 * @param attributes collection to set
	 */
	void setAttributes(Map<String, String> attributes);
	
	/**
	 * Adds collection of attributes to this element overwriting existing elements
	 * @param attributes collection to set
	 */
	void addAttributes(Map<String, String> attributes);

	/**
	 * Copies attributes from supplied attributes to this element overwriting existing collection
	 * @param attributes collection to set
	 */
	void setAttributes(final IAttributes attributes);

	/**
	 * Adds attributes from supplied attributes to this element overwriting existing ones
	 * @param attributes collection to set
	 */
	void addAttributes(final IAttributes attributes);

	
	/**
	 * Merges attributes from supplied attributes to this element, does not overwrite existing ones  
	 * @param attributes collection to merge
	 */
	void mergeAttributes(final IAttributes attributes);

	/**
	 * Merges attributes from supplied attributes to this element considering only attributes with given prefix  
	 * @param attributes collection to merge
	 * @param prefix attributes prefix, for instance "C"
	 */
	void mergeAttributes(final IAttributes attributes, final String prefix);
	
	
	/**
	 * Checks if element has attribute for given key
	 * @param key attribute key
	 * @return true if attribute for the given key is found, false otherwise
	 */
	boolean hasAttribute(String key);

	/**
	 * Checks if element has at least one attribute matching specified key pattern.
	 * Equivalent to hasAttribute() if argument contains no wild cards  
	 * @param keyPattern to search for
	 * @return true if matching attribute is found, otherwise false
	 */
	boolean containsAttribute(String pattern);

	/**
	 * Returns attribute value stored in object if any
	 * @param key attribute key
	 * @return value or null if attribute does not exist
	 */
	String getAttribute(String key);
	
	/**
	 * Returns attribute value stored in object if or default value if no attribute exists
	 * @param key attribute key
	 * @param defaultValue value to return if attribute is not found
	 * @return attribute value or default value if attribute does not exist
	 */
	String getAttribute(String key, String defaultValue);

	/**
	 * Adds attribute key-value pair to this element, overwriting existing one  
	 * @param key attribute key
	 * @param value attribute value
	 */
	void setAttribute(String key, String value);
	
	/**
	 * Adds attribute key-value pair to this element, overwriting existing one  
	 * @param key attribute key
	 * @param value boolean attribute value
	 */
	void setAttribute(String key, boolean value);

	/**
	 * Adds attribute key-value pair to this element, overwriting existing one  
	 * @param key attribute key
	 * @param value integer attribute value
	 */
	void setAttribute(String key, int value);

	/**
	 * Adds attribute key-value pair to this element, overwriting existing one  
	 * @param key attribute key
	 * @param value long attribute value
	 */
	default void setAttributeHex(String key, long value) {
		setAttribute(key, longToHexString(value));
	}

	/**
	 * Updates existing attribute key-value pair or adds one if attribute does not exist  
	 * @param key attribute key
	 * @param value attribute value
	 * @return true if value has changes or attribute added
	 */
	default boolean updateAttribute(String key, String value) {
		if(hasAttribute(key)&& getAttribute(key).equals(value)) {
			return false;
		}
		setAttribute(key, value );
		return true;
	}
	
	/**
	 * Updates existing attributes with supplied ones, adds attributes if do not exist  
	 * @param attributes IAttributes with new attribute values 
	 * @return true if a single value has changes or an attribute added
	 */
	default boolean updateAttributes(IAttributes attributes) {
		if(attributes == null)
			return false;
		return updateAttributes(attributes.getAttributesAsMap());
	}

	/**
	 * Updates existing attributes with supplied ones, adds attributes if do not exist  
	 * @param attributes Map<String, String> with new key-value pairs 
	 * @return true if a single value has changes or an attribute added
	 */
	default boolean updateAttributes(Map<String, String> attributes) {
		if(attributes == null || attributes.isEmpty())
			return false;
		boolean bChanged = false;
		for( Entry<String, String> e : attributes.entrySet()) {
			if(updateAttribute(e.getKey(),e.getValue())) {
				bChanged = true;
			}
		}
		return bChanged;
	}
	
	
	/**
	 * Removes attribute from the map 
	 * @param key attribute key
	 */
	void removeAttribute(String key);
	
	
	/**
	 * Returns integer representation of an attribute value  
	 * @param key attribute key
	 * @param nDefault value to return if attribute is not found
	 * @return attribute value as integer or nDefault  
	 */
	int getAttributeAsInt(String key, int nDefault);

	/**
	 * Returns long integer representation of an attribute value  
	 * @param key attribute key
	 * @param nDefault value to return if attribute is not found
	 * @return attribute value as long or nDefault  
	 */
	long getAttributeAsLong(String key, long nDefault);
	
	
	/**
	 * Returns boolean representation of an attribute value  
	 * @param key attribute key
	 * @param bDefault value to return if attribute is not found
	 * @return attribute value as boolean or bDefault  
	 */
	boolean getAttributeAsBoolean(String key, boolean bDefault);
	
	/**
	 * Adds attribute key-value pair to this element if it does not exist
	 * @param key attribute key
	 * @param value attribute value
	 */
	void mergeAttribute(String key, String value);
	
	
	/**
	 * Checks if all attributes of this element exist in supplied map and their values match. 
	 * Wild card match is used  
	 * @param otherAttributes attributes to match to
	 * @return true if matches, false otherwise
	 */
	boolean matchAttributes(final IAttributes otherAttributes);

	/**
	 * Checks if all attributes with given prefix of this element exist in supplied map and their values match. 
	 * Wild card match is used  
	 * @param otherAttributes attributes to match to
	 * @param prefix attribute key prefix 
	 * @return true if matches, false otherwise
	 */
	boolean matchAttributes(final IAttributes otherAttributes, String prefix);

	/**
	 * Checks if attributes found in this item and supplied map match    
	 * using wild card match  
	 * @param otherAttributes attributes to match to
	 * @return true if matches, false otherwise
	 */
	boolean matchCommonAttributes(final IAttributes otherAttributes);

	/**
	 * Checks if attributes with given prefix found in this item and supplied map match    
	 * using wild card match  
	 * @param otherAttributes attributes to match to
	 * @param prefix attribute key prefix 
	 * @return true if matches, false otherwise
	 */
	boolean matchCommonAttributes(final IAttributes otherAttributes, String prefix);

	
	/**
	 * Matches attribute value against pattern 
	 * @param key attribute key, can be used to define match method 
	 * @param value attribute value
	 * @param pattern pattern to match to value 
	 * @return true if attribute value matches pattern 
	 */
	boolean matchAttribute(final String key, final String value, final String pattern);


	/**
	 * Checks if attributes map contain at least one attribute with value exactly matching supplied string 
	 * @param value attribute value
	 * @return true if map contains attribute value  
	 */
	boolean containsValue(final String value);

	/**
	 * Checks if attributes map contain at least one attribute with value matching given pattern value 
	 * @param pattern attribute value pattern
	 * @return true if map contains attribute value pattern  
	 */
	boolean containsValuePattern(final String pattern);

	
	/**
	 * Returns URL associated with the item if any
	 * @return URL associated with the item
	 */
	String getUrl();
	
	/**
	 * Returns document file or URL associated with the item if any
	 * @return document file or HTTP link associated with the item
	 */
	String getDoc();


	/**
	 * Returns string containing all attributes in the form "key0"="value0" "key1"=value1 ...
	 * @return string containing keys and values of all attributes separated by spaces
	 */
	default String toXmlString() {
		return getAttributesAsString(" "); //$NON-NLS-1$
	}

	/**
	 * Returns string containing all attributes in the form "key0"="value0", "key1"=value1,...
	 * @param delimiter delimiter to use (usually ", " or " ")
	 * @return string containing keys and values of all attributes
	 */
	default String getAttributesAsString(String delimiter) {
		String s = CmsisConstants.EMPTY_STRING;
		// we need to synchronize the entire object here as we cannot use synchronized method modifier in interfaces
		synchronized (this) { 
			if(hasAttributes()) {
				Map<String, String> attr = getAttributesAsMap();
				for(Entry<String, String> e : attr.entrySet()){
					if(!s.isEmpty()) {
						s += delimiter; 
					}
					s += e.getKey();
					s += "=\""; //$NON-NLS-1$
					s += e.getValue();
					s += "\""; //$NON-NLS-1$
				}
			}
		}
		return s;
	}

	
	/**
	 * Returns long integer representation of a supplied string value  
	 * @param value string to convert
	 * @param nDefault value to return if conversion is not successful
	 * @return value as long or nDefault  
	 */
	static long stringToLong(String value, long nDefault) {
		Long result = Utils.stringToLong(value);
		if(result != null)
			return result;
		return nDefault;
	}
	
	/**
	 * Returns string representation of supplied long value as hexadecimal string prefixed with "0x"   
	 * @param value long to convert
	 * @return value as hexadecimal string   
	 */
	static String longToHexString(long value) {
		 return "0x" + Long.toHexString(value).toUpperCase();  //$NON-NLS-1$
	}
	
}
