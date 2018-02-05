package org.twak.utils;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Given objects with a field that is a string, this class defines an ordering over the class.
 * Named field must be public!
 * 
 * @author twak
 */
public class DuckTypeAlphabetComparator implements Comparator<Object>
{
    String field, method;

    private DuckTypeAlphabetComparator() {
    }

    public static DuckTypeAlphabetComparator byField(String field)
    {
        DuckTypeAlphabetComparator out = new DuckTypeAlphabetComparator();
        out.field = field;
        return out;
    }
    
    public static DuckTypeAlphabetComparator byMethod(String method)
    {
        DuckTypeAlphabetComparator out = new DuckTypeAlphabetComparator();
        out.method = method;
        return out;
    }

    public static DuckTypeAlphabetComparator byToString()
    {
        DuckTypeAlphabetComparator out = new DuckTypeAlphabetComparator();
        out.method = "toString";
        return out;
    }

    @Override
    public int compare(Object o1, Object o2)
    {
        try {
            String s1, s2;
            if (field != null) {
                s1 = (String) o1.getClass().getField(field).get(o1);
                s2 = (String) o2.getClass().getField(field).get(o2);
            } else {
                s1 = (String) o1.getClass().getMethod(method).invoke(o1);
                s2 = (String) o2.getClass().getMethod(method).invoke(o2);
            }
            return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
        } catch (Throwable ex) {
            Logger.getLogger(DuckTypeAlphabetComparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
