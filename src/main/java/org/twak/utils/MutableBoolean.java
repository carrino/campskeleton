
package org.twak.utils;

/**
 *
 * @author twak
 */
public class MutableBoolean {
    boolean val;

    public MutableBoolean( boolean value )
    {
        this.val = value;
    }

    public void set(boolean value)
    {
        this.val = value;
    }

    public boolean get()
    {
        return val;
    }
}
