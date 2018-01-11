package org.twak.utils;


import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author twak
 */
public class ReflectionHelpers
{
    public static boolean declaresMethod (String methodName, Object o)
    {
            try
            {
                Method m = o.getClass().getMethod(methodName);
                if ( m.getDeclaringClass() == o.getClass() )
                    return true;
            }
            catch (Throwable ex)
            {
                ex.printStackTrace();
            }
            return false;
    }
}
