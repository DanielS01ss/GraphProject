package de.jpp.io.interfaces;

public class ParseException extends Exception {


    public ParseException(String msg)  {
        try {
            throw new Exception(msg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ParseException()  {
        try {
            throw new Exception("Invalid Format!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
