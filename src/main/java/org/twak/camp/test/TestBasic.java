package org.twak.camp.test;

import org.twak.camp.Corner;
import org.twak.camp.Edge;
import org.twak.camp.Machine;
import org.twak.camp.OffsetSkeleton;
import org.twak.utils.collections.Loop;
import org.twak.utils.collections.LoopL;

public class TestBasic {
//	@Test
	public void testBasic() {
		Loop<Edge> loop1 = new Loop<Edge>();
		Corner c1 = new Corner ( 0,0), 
				c2 = new Corner (100,0 ), 
				c3 = new Corner (100,100 );
		
		Machine directionMachine = new Machine ();
		
		loop1.append(new Edge ( c1,c2 ) );
		loop1.append(new Edge ( c2, c3 ) );
		loop1.append(new Edge ( c3, c1 ) );
		for (Edge e : loop1) e.machine = directionMachine;

		LoopL<Edge> a = new LoopL<Edge>(loop1);
		LoopL<Corner> output = OffsetSkeleton.shrink(a, 5);
		
		for (Loop<Corner> ll : output) {
			for (Corner c : ll)
			{
//				Assert.assertNotNull( c );
				System.out.println(c);
			}
			System.out.println(">>");
		}
	}
}
