/**
 * 
 */
package it.courses.test.utility;

import java.sql.Date;
import java.text.SimpleDateFormat;

import it.courses.utility.CommonUtil;


/**
 * @author fede
 *
 */
public class CommonUtilTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Date today = new Date(System.currentTimeMillis());
		System.out.println("dateToString: "+CommonUtil.dateToString(today));
		
		String stoday = new SimpleDateFormat("yyyy-MM-dd").format(today);
		System.out.println("stringToDate: "+CommonUtil.stringToDate(stoday));
	}

}
