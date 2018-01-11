package org.twak.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author twak
 */
public class WeakListener
{
    public interface Changed
    {
        public void changed();
    }
    
    List < WeakReference<Changed> > dispatchees = new ArrayList();

    public void fire()
    {
        dumpVoids();
        for (WeakReference<Changed> d : dispatchees)
        {
            Changed c = d.get();
            if (c != null)
                c.changed();
        }
    }

    public void add(Changed target)
    {
        dumpVoids();
        dispatchees.add(new WeakReference<WeakListener.Changed>(target));
    }
    
    private void dumpVoids() {
        Iterator<WeakReference<Changed>> it = dispatchees.iterator();
        while (it.hasNext())
            if (it.next().get() == null)
                it.remove();
    }

    public void addIfNew( Changed target )
    {
        dumpVoids();
        Iterator<WeakReference<Changed>> it = dispatchees.iterator();
        while (it.hasNext())
            if (it.next().get() == target)
                return;

        add(target);
    }
}
