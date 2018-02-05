package org.twak.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author twak
 */
public class CombineAbles<I> implements Iterable<List<I>>
{
    List<? extends Iterable<I>> ables;
    
    public CombineAbles( List<? extends Iterable<I>> ables )
    {
        this.ables = ables;
    }
    
    protected class CAIt implements Iterator<List<I>>
    {
        List<Iterator<I>> current = new ArrayList();
        boolean done = false;
        List<I> values = new ArrayList();

        public CAIt()
        {
            for ( Iterable<I> ii : ables )
            {
                Iterator<I> it = ii.iterator();
                current.add( it );
                if (!it.hasNext())
                {
                    done = true;
                    break;
                }
                values.add( it.next() );
            }
        }

        @Override
        public boolean hasNext()
        {
            return !done;
        }

        @Override
        public List<I> next()
        {
            List<I> out2 = new ArrayList( values );

            int index = 0;

            for ( Iterator<I> ii : current )
            {
                if ( ii.hasNext() )
                {
                    values.set( index, ii.next() );

                    break;
                }
                else // rewind this guy, proceed to next
                {
                    if ( ii == current.get( current.size() - 1 ) )
                        done = true;
                    
                    current.set( index, ii = ables.get( index ).iterator() );
                    values.set (index, ii.next());
                }
                
                index++;
            }

            return out2;
        }

        @Override
        public void remove()
        {
            throw new Error( "not on my watch Jimmy" );
        }
    }

    @Override
    public Iterator<List<I>> iterator()
    {
        return new CAIt();
    }

    private static List<Integer> make (int count, int prefix)
    {
        List<Integer> out = new ArrayList<Integer>();
        
        for (int i = 0; i < count; i++)
            out.add (prefix + i);
        
        return out;
    }
    
    public static void main (String[] args) {
        List<Integer> a = make (2, 10), b = make (4, 20), c = make (2, 30);
        for (List<Integer> li : new CombineAbles<Integer> (Arrays.asList( a,b, c) ) )
            System.out.println(li);
    }
}
