package org.twak.utils;

/**
 *
 * @author twak
 */
public class MutableInteger {
	public int i;

	public MutableInteger( int i ) {
		this.i = i;
	}

	public MutableInteger() {
		this( 0 );
	}

	@Override
	public String toString() {
		return "" + i;
	}
}
