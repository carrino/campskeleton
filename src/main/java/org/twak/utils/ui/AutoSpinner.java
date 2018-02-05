
package org.twak.utils.ui;

import java.awt.FlowLayout;
import java.lang.reflect.Field;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author twak
 */
public class AutoSpinner extends JPanel implements ChangeListener {

    Field f;
    Object o;
    JSpinner spinner;

    public AutoSpinner ( Object o, String field, final String name, int min, int max )
    {
        try
        {
            this.o = o;
            f = o.getClass().getField( field );
            int initialVal = f.getInt( o );

            setLayout (new FlowLayout(FlowLayout.LEFT));

            add (new JLabel (name));

            spinner = new JSpinner();
            spinner.setModel( new SpinnerNumberModel( initialVal, min, max, 1));
            spinner.addChangeListener( this );

            add(spinner);
        }
        catch ( Throwable ex )
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void stateChanged( ChangeEvent e )
    {
        try
        {
            f.set( o, spinner.getValue() );
        }
        catch ( Throwable ex )
        {
            ex.printStackTrace();
        }
        updated();
    }


    public void updated()
    {
        // override me!
    }
}
