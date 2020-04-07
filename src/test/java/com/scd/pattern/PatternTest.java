package com.scd.pattern;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author James
 */
public class PatternTest {

    public String findDelimiter(Pattern pattern, String inputStr) {
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.find()) {
            return matcher.group(5);
        }
        return "";
    }

    @Test
    public void testDelimiter() {
        Pattern pattern = Pattern.compile("^delimiter\\s*(.*)", Pattern.CASE_INSENSITIVE);
        String testStr1 = "delimiter   ;;";
        String testStr2 = "delimiter  ;";
        System.out.println(findDelimiter(pattern, testStr1));
        System.out.println(findDelimiter(pattern, testStr2));
    }

    private static final Pattern DELIMITER_PATTERN = Pattern.compile("^\\s*((--)|(//))?\\s*(//)?\\s*@?DELIMITER\\s+([^\\s]+)", Pattern.CASE_INSENSITIVE);

    @Test
    public void testPattern() {
        String testStr1 = "delimiter   ;;";
        String testStr2 = "delimiter  ;";
        System.out.println(findDelimiter(DELIMITER_PATTERN, testStr1));
        System.out.println(findDelimiter(DELIMITER_PATTERN, testStr2));
    }
}
