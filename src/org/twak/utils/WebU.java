/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.twak.utils;

import java.net.URI;

/**
 *
 * @author twak
 */
public class WebU {
    public static void showBrowser( String url )
    {
        try
            {

                // java 1.5....
                Class c = Class.forName( "java.awt.Desktop" );
                Object o = c.getMethod( "getDesktop").invoke( null );
                o.getClass().getMethod( "browse", URI.class ).invoke( o, new URI(url) );

                // vs 1.6....
                //Desktop.getDesktop().browse( new URI( url ) );
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
            return;
    }
}
