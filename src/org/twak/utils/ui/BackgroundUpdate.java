package org.twak.utils.ui;

/**
 *
 * @author twak
 */
public abstract class BackgroundUpdate <E>
{
    boolean busy = false;
    boolean needsRefresh = true;

    E lastUpdate;

    public BackgroundUpdate() {
        changed();
    }

    /**
     * @return latest copy, otherwise null
     */
    public E get()
    {
        if (needsRefresh)
            changed();
        
        return lastUpdate;
    }

    /**
     * Something has changed, lastupdate needs updating.
     */
    public void changed()
    {
        needsRefresh = true;
        
        if (grabBusy())
            new Thread()
            {
                @Override
                public void run()
                {
                    while (needsRefresh)
                    {
                        try
                        {
                            lastUpdate = update();
                        }
                        catch (Throwable th)
                        {
                            System.err.println("while background update was running, it encountered:");
                            th.printStackTrace();
                        }
                        needsRefresh = false;
                    }
                    setBusy(false);
                }
            }.start();
    }

    synchronized private boolean grabBusy()
    {
        if (busy)
            return false;
        else
        {
            busy = true;
            return true;
        }
    }

    synchronized private void setBusy(boolean s)
    {
        busy = s;
    }

    /**
     * do the slow calculation in this method
     * @return
     */
    protected abstract E update();
}
