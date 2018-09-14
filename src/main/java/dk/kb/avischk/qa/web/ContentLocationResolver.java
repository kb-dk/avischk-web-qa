package dk.kb.avischk.qa.web;

public class ContentLocationResolver {

    private static String httpContentBase;
    
    public static void setHttpContentBase(String base) {
        httpContentBase = base;
    }
    
    public static String getHttpContentBase() {
        return httpContentBase;
    }
    
}
