package de.jpp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\[(\s*)[a-zA-Z]*=(((-?)(0|([1-9][0-9]*))(.[0-9]+)?){1})(\s*)\\]");
        String line = "[ label=10 ]";
        Matcher m = p.matcher(line);
        System.out.println(m.matches());
    }
}
