package com.trm.trmclient.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator
 * Create an instance to call validate method
 * @author Hieu
 *
 */
public class EmailValidator {
	private static Pattern pattern;
	  private Matcher matcher;
	  private final String EMAIL_PATTERN = 
                 "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	  public boolean validate(final String hex){
		  pattern = Pattern.compile(EMAIL_PATTERN);
		  matcher = pattern.matcher(hex);
		  return matcher.matches();

	  }

}
