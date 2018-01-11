package org.twak.utils.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author twak
 */
public class AutoTextField
{

    public static void linkFieldString( final JTextField sequenceVarField, final Object down, String fieldString )
    {
        linkFieldString( sequenceVarField, down, fieldString, new AutoTextField(), "toString" ); // <-- toString unlikely to have any noticable effect ;)
    }

    public static void linkFieldString( final JTextField sequenceVarField, final Object down, String fieldString, final Object changeFireObject, final String changeFireField )
    {
        try
        {
            final Field stringUpdateField = down.getClass().getField( fieldString );
            final Method changedFireMethod = changeFireObject.getClass().getMethod( changeFireField );

            sequenceVarField.setText( (String) stringUpdateField.get( down ) );

            sequenceVarField.getDocument().addDocumentListener( new AbstractDocumentListener()
            {

                @Override
                public void changed()
                {
                    try
                    {
                        stringUpdateField.set( down, sequenceVarField.getText() );
                        changedFireMethod.invoke(changeFireObject);
                    } catch ( Throwable th )
                    {
                        th.printStackTrace();
                    }
                }
            } );

        } catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
