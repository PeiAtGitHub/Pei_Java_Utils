package com.github.peiatgithub.java.utils.tests;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.github.peiatgithub.java.utils.xml.XpathBuilder;

/**
 * 
 * @author pei
 *
 */
public class XpathBuilderTests {
	
	private static final String TITLE = "title";
	private static final String BOOKSTORE = "bookstore";
	private static final String BOOK = "book";
	private static final String AUTHOR = "author";
	private static final String PRICE = "price";
	private static final String JAMES_McGOVERN = "James McGovern";


	@Test
	public void xpathBuilderTest() throws Exception {
		
		//basics
		assertThat(getNewXpathBuilder().node(BOOKSTORE).build(), is(BOOKSTORE));
		assertThat(getNewXpathBuilder().startFromRoot().node(BOOKSTORE).build(), is("/bookstore"));
		assertThat(getNewXpathBuilder().startFromAnywhere().node(BOOK).build(), is("//book"));
		assertThat(getNewXpathBuilder().node(BOOKSTORE).anyWhere().node(BOOK).build(), is("bookstore//book"));
		assertThat(getNewXpathBuilder().startFromAnywhere().attribute("lang").build(), is("//@lang"));

		// index & position
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().node(BOOK).index(1).down().node(TITLE).build(), 
				is("/bookstore/book[1]/title"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().nodeIndex(BOOK, 1).down().node(TITLE).build(), 
				is("/bookstore/book[1]/title"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).indexFromLast(2).down(TITLE).build(), 
				is("/bookstore/book[last() - 1]/title"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().nodeIndexFromLast(BOOK, 2).down(TITLE).build(), 
				is("/bookstore/book[last() - 1]/title"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().node(BOOK).position(3).build(), 
				is("/bookstore/book[position() = 3]"));
		// content
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).withContent("Java").build(), 
				is("/bookstore/book[.='Java']"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().nodeWithContent(BOOK, "Java").build(), 
				is("/bookstore/book[.='Java']"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).contentContains("Java").build(), 
				is("/bookstore/book[contains(., 'Java')]"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().nodeContentContains(BOOK, "Java").build(), 
				is("/bookstore/book[contains(., 'Java')]"));

		// condition
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).condition("price>30").down(TITLE).build(), 
				is("/bookstore/book[price>30]/title"));
	
		// attribute
		assertThat(getNewXpathBuilder().startFromAnywhere(BOOK).withAttribute("category", "cooking").build(), 
				is("//book[@category='cooking']"));
		assertThat(getNewXpathBuilder().startFromAnywhere().nodeWithAttribute(BOOK, "category", "cooking").build(), 
				is("//book[@category='cooking']"));
		assertThat(getNewXpathBuilder().startFromAnywhere(BOOK).attributeContains("category", "cook").build(), 
				is("//book[contains(@category, 'cook')]"));
		assertThat(getNewXpathBuilder().startFromAnywhere().nodeAttributeContains(BOOK, "category", "cook").build(), 
				is("//book[contains(@category, 'cook')]"));

		// navigate up and down the path hierarchy
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.up(TITLE).build(), 
				is("/bookstore/book/author[.='James McGovern']/../title"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.up().up().build(), 
				is("/bookstore/book/author[.='James McGovern']/../../"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.up(2).build(), 
				is("/bookstore/book/author[.='James McGovern']/../../"));
		
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down().down().node(TITLE).build(), 
				is("/bookstore/./title"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(2).node(TITLE).build(), 
				is("/bookstore/./title"));
		
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.up(2).down(BOOK).build(), 
				is("/bookstore/book/author[.='James McGovern']/../.././book"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.up().up().down(BOOK).build(), 
				is("/bookstore/book/author[.='James McGovern']/../.././book"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.down().down().up().up().build(), 
				is("/bookstore/book/author[.='James McGovern']/./../../"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.down(2).up(2).build(), 
				is("/bookstore/book/author[.='James McGovern']/./../../"));

		// start* methods clears firstly
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.down(2).up(2).startFromAnywhere().build(), 
				is("//"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.down(2).up(2).startFromRoot().build(), 
				is("/"));
		
		// siblings 
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).followingSibling(true).build(), 
				is("/bookstore/book/following-sibling::*"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).followingSibling(false).node("newspaper")
				.build(), 
				is("/bookstore/book/following-sibling::newspaper"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).precedingSibling(true).build(), 
				is("/bookstore/book/preceding-sibling::*"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).precedingSibling(false).node("newspaper")
				.build(), 
				is("/bookstore/book/preceding-sibling::newspaper"));

		// ancestor & descendant 
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).ancestor(true, true).build(), 
				is("/bookstore/book/ancestor-or-self::*"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).ancestor(true, false).build(), 
				is("/bookstore/book/ancestor::*"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).ancestor(false, true).node(PRICE)
				.build(), 
				is("/bookstore/book/ancestor-or-self::price"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).ancestor(false, false).node(PRICE).build(), 
				is("/bookstore/book/ancestor::price"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).descendant(true, true).build(), 
				is("/bookstore/book/descendant-or-self::*"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).descendant(true, false).build(), 
				is("/bookstore/book/descendant::*"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).descendant(false, true).node(PRICE).build(), 
				is("/bookstore/book/descendant-or-self::price"));
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).descendant(false, false).node(PRICE)
				.build(), 
				is("/bookstore/book/descendant::price"));

		// combinations
		assertThat(getNewXpathBuilder().startFromRoot(BOOKSTORE).down(BOOK).down(AUTHOR).withContent(JAMES_McGOVERN)
				.up().up().down(BOOK).followingSibling(false).node("newspaper")
				.ancestor(true, false).index(1).down(BOOK).build(), 
				is("/bookstore/book/author[.='James McGovern']/../.././book/following-sibling::newspaper"
						+ "/ancestor::*[1]/book"));

		
	}

	/*
	 * 
	 */
	
	private XpathBuilder getNewXpathBuilder() {
		return XpathBuilder.newBuilder();
	}

}
