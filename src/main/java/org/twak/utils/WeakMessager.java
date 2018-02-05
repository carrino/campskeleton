package org.twak.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author twak
 */
public class WeakMessager<E>
{
    public interface Changed<E>
    {
        public void changed(E message);
    }
    List < WeakReference<Changed<E>> > dispatchees = new ArrayList();

    public void fire(E message)
    {
        dumpVoids();
        for (WeakReference<Changed<E>> d : dispatchees)
        {
            Changed c = d.get();
            if (c != null)
                c.changed( message );
        }
    }

    public void add( Changed toAdd)
    {
        dumpVoids();
        dispatchees.add(new WeakReference<WeakMessager.Changed<E>>(toAdd));
    }

    public void addIfNew( Changed toAdd )
    {
        dumpVoids();
        
        for (WeakReference<Changed<E>> d : dispatchees)
        {
            Changed c = d.get();
            if (c != null)
                if (c == toAdd)
                    return;
        }
        dumpVoids();
        
        dispatchees.add(new WeakReference<WeakMessager.Changed<E>>(toAdd));
    }
    
    public void remove( Changed toKill )
    {
        for (WeakReference<Changed<E>> d : dispatchees)
        {
            Changed c = d.get();
            if (c != null)
                if (c == toKill)
                    d.clear();
        }
        dumpVoids();
    }
    
    private void dumpVoids() {
        Iterator<WeakReference<Changed<E>>> it = dispatchees.iterator();
        while (it.hasNext())
            if (it.next().get() == null)
                it.remove();
    }
}
