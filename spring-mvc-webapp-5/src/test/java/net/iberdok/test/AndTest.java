/**
 * 
 */
package net.iberdok.test;

import org.junit.Test;

import net.iberdok.util.test.logger.Log4jAwareTest;

/**
 * @author DOIBALMI
 *
 */
public class AndTest extends Log4jAwareTest {

	/**
	 * 
	 */
	public AndTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void test() {
		this.info("1 & 4 = " + (1 & 4));
		this.info("2 & 4 = " + (2 & 4));
		this.info("3 & 4 = " + (3 & 4));
		this.info("4 & 4 = " + (4 & 4));
		this.info("15 & 4 = " + (15 & 4));
	}

}
