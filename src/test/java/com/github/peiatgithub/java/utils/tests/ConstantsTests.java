package com.github.peiatgithub.java.utils.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.assertj.core.api.Assertions.*;

import static com.github.peiatgithub.java.utils.Constants.*;

import org.junit.Test;

/**
 * 
 * @author pei
 *
 */
public class ConstantsTests {

	@Test
	public void testCollectionInitialized() throws Exception {
		
		assertThat(TEST_LIST_123).hasSize(3);
		assertThat(TEST_SET_123).hasSize(3);
		assertThat(TEST_MAP_123).hasSize(3);
		assertThat(TEST_INT_ARRAY_123).hasSize(3);
		
	}
	
	@Test
	public void testNumberCorrectness() throws Exception {
		
		assertThat(K, is((long)1000));
		assertThat(MN, is((long)Math.pow(10, 6)));
		assertThat(BN, is((long)Math.pow(10, 9)));

		assertThat(KB, is(1024L));
		assertThat(MB, is(1024 * 1024L));
		assertThat(GB, is(1024 * 1024 * 1024L));
		
	}
	
}
