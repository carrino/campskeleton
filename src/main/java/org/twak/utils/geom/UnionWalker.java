package org.twak.utils.geom;

import static org.twak.utils.Mathz.*;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.vecmath.Point2d;

import org.twak.utils.Pair;
import org.twak.utils.collections.Loop;
import org.twak.utils.collections.LoopL;
import org.twak.utils.collections.MultiMap;

/**
 *
 * @author twak
 */
public class UnionWalker
{
    public MultiMap<Point2d,Point2d> map = new MultiMap();
    public Set<Pair<Point2d, Point2d>> starts = new LinkedHashSet();

    public void addEdge (Point2d a, Point2d b, boolean isStart) {
    	
        addEdge(a, b);
        if (isStart)
            starts.add( new Pair<Point2d, Point2d>(a,b));
    }
    
    public void addEdge (Point2d a, Point2d b) {
    	if (!a.equals(b) && !map.contains(a, b))
    		map.put( a, b );
    }
    

	public void addEdges( Point2d a, Point2d b ) {
		addEdge(a,b);
		addEdge(b,a);
	}

    public LoopL<Point2d> find()  {
    	
        LoopL<Point2d> loopl = new LoopL();
        start:
        for (Pair<Point2d, Point2d> s : starts)
        {
            Loop<Point2d> loop = new Loop();

            Point2d prev = s.first(), current = s.second(), start = s.second();
            do
            {
                Point2d next = null;
                double angle = -Double.MAX_VALUE;
                for (Point2d n : map.get( current) )
                {
                    if ( n != prev )
                    {
                        double iA = interiorAngleBetween( prev, current, n );
                        if ( iA > angle )
                        {
                            next = n;
                            angle = iA;
                        }
                    }
                }
                if (next == null)
                {
                    continue start;
                }
                map.remove( current ); // don't revisit points
                loop.append( new Point2d(next) );
                prev = current;
                current = next;
            }
            while (current != start);

            // if we sucessfully complete :)
            loopl.add( loop );
        }
        return loopl;
    }

    public LoopL<Point2d> findAll() /* ignores start */ {
    	
        LoopL<Point2d> loopl = new LoopL();
        start:
        while  (!map.isEmpty())
        {
            Loop<Point2d> loop = new Loop();

            Point2d prev = map.keySet().iterator().next(),
            		current = map.get(prev).iterator().next(),
            		start = current;
            
            do
            {
                Point2d next = null;
                double angle = -Double.MAX_VALUE;
                
				for (Point2d n : map.get(current)) {

					double iA = interiorAngleBetween(prev, current, n);
					if (iA > angle) {
						next = n;
						angle = iA;
					}
				}
                
                if (next == null ) {
                	
                	map.remove( prev ); // don't revisit points
                    continue start;
                }
                
				{
					Point2d tmp = new Point2d(0.01, 0.01);
					tmp.add(current);
				}
                
                map.remove( current, next ); // don't revisit points
                
                loop.append( new Point2d(next) );
                prev = current;
                current = next;
            }
            while (!current.equals ( start ) );

            // if we sucessfully complete :)
            loopl.add( loop );
        }
        return loopl;
    }
    
    public static void main (String[] args)
    {
        Point2d a = new Point2d (0,0),
                b=new Point2d (1,0),
                c=new Point2d (1,1),
                d=new Point2d (0,1),
                y=new Point2d (0,2),
                x=new Point2d (-1,1);
        
        UnionWalker g = new UnionWalker();
        
        g.addEdge( a, b, true );
        g.addEdge( b, c, false );
        g.addEdge( c, d, false );
        g.addEdge( d, a, false );
        g.addEdge( d, x, false );
        g.addEdge( d, y, false );

        for (Loop<Point2d> l : g.find())
            for (Point2d p : l)
                System.out.println (p);
    }

    @Override
    public String toString()
    {
        return map.toString();
    }

}
