package com.github.peiatgithub.java.utils.xml;

import static com.github.peiatgithub.java.utils.Constants.*;
import static com.github.peiatgithub.java.utils.Utils.*;

/**
 * XpathBuilder helps to build a Xpath String
 * 
 * @author pei
 * @since 3.0
 * 
 */
public class XpathBuilder {

	private StringBuilder xpath = new StringBuilder(EMPTY);

	/**
	 * @return a new instance of the builder
	 */
	public static XpathBuilder newBuilder() {
		return new XpathBuilder();
	}

	/**
	 * Append text to the xpath.
	 */
	public XpathBuilder append(String text) {
		this.xpath.append(text);
		return this;
	}

	/**
	 * Append node name to the xpath.
	 */
	public XpathBuilder node(String nodeName) {
		return append(nodeName);
	}

	/**
	 * Append condition to xpath
	 */
	public XpathBuilder condition(String condition) {
		return append(String.format("[%s]", condition));
	}

	/**
	 * Append the condition of exact content
	 */
	public XpathBuilder withContent(String content) {
		return condition(String.format(".='%s'", content));
	}

	/**
	 * Append the condition of 'content contains' 
	 */
	public XpathBuilder contentContains(String content) {
		return condition(String.format("contains(., '%s')", content));
	}

	/**
	 * The attribute.
	 */
	public XpathBuilder attribute(String attributeName) {
		return append(String.format("@%s", attributeName));
	}
	
	/**
	 * Append the condition of exact attribute value
	 */
	public XpathBuilder withAttribute(String attributeName, String attributeValue) {
		return condition(String.format("@%s='%s'", attributeName, attributeValue));
	}

	/**
	 * Append the condition of 'attribute contains'
	 */
	public XpathBuilder attributeContains(String attributeName, String attributeValuePart) {
		return condition(String.format("contains(@%s, '%s')", attributeName, attributeValuePart));
	}

	/**
	 * Specify the index of the node.
	 * @param index starts from 1
	 */
	public XpathBuilder index(int index) {
		return append(String.format("[%d]", index));
	}

	/**
	 * <pre>
	 * Specify the index of the node, starting from the last node. 
	 * E.g.
	 * indexFromLast(1) specifies the last node.
	 * indexFromLast(2) specifies the second last node. 
	 * </pre>
	 * @param indexFromLast starts from 1.
	 */
	public XpathBuilder indexFromLast(int indexFromLast) {
		return append(String.format("[last() - %d]", indexFromLast - 1));
	}

	/**
	 * Position is similar but different from index. 
	 * @param p Position starts from 1.
	 */
	public XpathBuilder position(int p) {
		return condition(String.format("position() = %d", p));
	}

	/**
	 * <pre>
	 * Preceding sibling nodes.
	 * If the input flag is true, specify all preceding sibling nodes, 
	 * otherwise specify the preceding sibling nodes that meet certain conditions 
	 * which must be set immediately with the builder.  
	 * </pre>
	 */
	public XpathBuilder precedingSibling(boolean all) {
		ensureEndWith(this.xpath, SLASH);
		
		this.xpath.append("preceding-sibling::");
		if (all) {
			this.xpath.append(ASTERISK);
		}
		return this;
	}

	/**
	 * <pre>
	 * Following sibling nodes.
	 * If the input flag is true, specify all following sibling nodes, 
	 * otherwise specify the following sibling nodes that meet certain conditions 
	 * which must be set immediately with the builder.
	 * </pre>  
	 */
	public XpathBuilder followingSibling(boolean all) {
		ensureEndWith(this.xpath, SLASH);
		
		this.xpath.append("following-sibling::");
		if (all) {
			this.xpath.append(ASTERISK);
		}
		return this;
	}

	/**
	 * Ancestor nodes. (parent, grandparent, etc.)
	 * @param all all ancestor nodes if true, otherwise the builder must set other conditions immediately.
	 * @param selfInclusive include the current node or not
	 */
	public XpathBuilder ancestor(boolean all, boolean selfInclusive) {
		ensureEndWith(this.xpath, SLASH);
		
		if (selfInclusive) {
			this.xpath.append("ancestor-or-self::");
		} else {
			this.xpath.append("ancestor::");
		}

		if (all) {
			this.xpath.append(ASTERISK);
		}
		return this;
	}

	/**
	 * Descendant nodes. (children, grandchildren, etc.)
	 * @param all all descendant nodes if true, otherwise the builder must set other conditions immediately.
	 * @param selfInclusive include the current node or not
	 */
	public XpathBuilder descendant(boolean all, boolean selfInclusive) {
		ensureEndWith(this.xpath, SLASH);
		
		if (selfInclusive) {
			this.xpath.append("descendant-or-self::");
		} else {
			this.xpath.append("descendant::");
		}

		if (all) {
			this.xpath.append(ASTERISK);
		}
		return this;
	}

	/**
	 * Node which has exact the specified content
	 */
	public XpathBuilder nodeWithContent(String nodeName, String content) {
		return this.node(nodeName).withContent(content);
	}

	/**
	 * Node with specified name and the content contains the specified value.
	 */
	public XpathBuilder nodeContentContains(String tagName, String content) {
		return this.node(tagName).contentContains(content);
	}

	/**
	 * Node with attribute with the specified value
	 */
	public XpathBuilder nodeWithAttribute(String nodeName, String attributeName, String attributeValue) {
		return this.node(nodeName).withAttribute(attributeName, attributeValue);
	}

	/**
	 * Node with attribute which contains specified value
	 */
	public XpathBuilder nodeAttributeContains(String nodeName, String attributeName, String attributeValuePart) {
		return this.node(nodeName).attributeContains(attributeName, attributeValuePart);
	}

	/**
	 * Node of certain index(starting from 1).
	 */
	public XpathBuilder nodeIndex(String nodeName, int index) {
		return this.node(nodeName).index(index);
	}

	/**
	 * <pre>
	 * Node of certain index counting from the last node. 
	 * Index starts from 1 which means the last node. 
	 * See {@link #indexFromLast(int)}
	 * </pre>
	 */
	public XpathBuilder nodeIndexFromLast(String tagName, int indexFromLast) {
		return this.node(tagName).indexFromLast(indexFromLast);
	}

	/**
	 * <pre>
	 * Goes n level down the path. 
	 * The result is for sure to end with a '/'.
	 * </pre>
	 */
	public XpathBuilder down(int n) {

		if (this.xpath.toString().endsWith("//")) {// do nothing
			return this;
		}
		
		if (n <= 0) {// do nothing
			return this;
		} 
		
		if (this.xpath.toString().endsWith(SLASH)) {
			this.xpath.append(DOT);
		}

		for (int i = 1; i <= n - 1; i++) {
			this.xpath.append("/.");
		}
		
		this.xpath.append(SLASH);

		return this;

	}

	/**
	 * Goes 1 level down the path. An alternative to call down(1). 
	 */
	public XpathBuilder down() {
		return down(1);
	}

	/**
	 * Simpler form of calling levelDown().node(String nodeName) 
	 */
	public XpathBuilder down(String nodeName) {
		return down().node(nodeName);
	}

	/**
	 * <pre>
	 * Goes n level up the path. 
	 * The result is for sure to end with a '/'.
	 * </pre>
	 */
	public XpathBuilder up(int n) {

		if (this.xpath.toString().endsWith("//")) {// do nothing
			return this;
		}

		if (n <= 0) {// do nothing
			return this;
		} 

		if (this.xpath.toString().endsWith(SLASH)) {
			this.xpath.append("..");
		}else {
			this.xpath.append("/..");
		}

		for (int i = 1; i <= n-1 ; i++) {
			this.xpath.append("/..");
		}

		this.xpath.append(SLASH);

		return this;
	}

	/**
	 * Goes 1 level up the path. An alternative to call up(1).
	 */
	public XpathBuilder up() {
		return up(1);
	}

	/**
	 * Simpler form of calling levelUp().node(String nodeName) 
	 */
	public XpathBuilder up(String nodeName) {
		return up().node(nodeName);
	}

	/**
	 * <pre>
	 * Makes the Xpath start from '/', which means start from the root of the DOC.
	 * Note: If needed, this method should be called BEFORE other builder-config methods. 
	 * Note: This method will firstly clear all the existing content of the in-build xpath.
	 * </pre>
	 */
	public XpathBuilder startFromRoot() {
		clearStringBuilder(this.xpath);
		return append(SLASH);
	}

	/**
	 *  The simpler form of calling startFromRoot().node(String nodeName)
	 */
	public XpathBuilder startFromRoot(String nodeName) {
		clearStringBuilder(this.xpath);
		return append(SLASH + nodeName);
	}

	/**
	 * <pre>
	 * Makes the Xpath start from '//', which means anywhere of the DOC. 
	 * Note: If needed, this method should be called BEFORE other builder-config methods.
	 * Note: This method will firstly clear all the existing content of the in-build xpath
	 * </pre>
	 */
	public XpathBuilder startFromAnywhere() {
		clearStringBuilder(this.xpath);
		return append("//");
	}

	/**
	 * A simpler form of calling startFromAnywhere().node(String nodeName)
	 */
	public XpathBuilder startFromAnywhere(String nodeName) {
		clearStringBuilder(this.xpath);
		return append("//" + nodeName);
	}

	/**
	 * Nodes in the document down the current node no matter where they are
	 */
	public XpathBuilder anyWhere() {
		return append("//");
	}

	
	/**
	 * Return the build result xpath. 
	 */
	public String build() {
		return this.xpath.toString();
	}

}