package org.twak.utils.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * @author twak
 */
public abstract class JLazyMenu extends JMenu {
    public JLazyMenu(String name)
    {
        setText(name);
        addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                removeAll();
                for (final Runnable r : getEntries()) {
                    
                    if (r instanceof SubMenu)
                    {
                        add(((SubMenu) r).create());
                    }
                    else
                    {
                    JMenuItem item = new JMenuItem(r.toString());
                    item.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            r.run();
                        }
                    });
                    
                    
                    add(item);
                    }
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
    }

    public abstract List<Runnable> getEntries();
    public abstract class SubMenu implements Runnable
    {
        public abstract JLazyMenu create();
        
        public void run()
        {
            throw new UnsupportedOperationException("Nothing to run");
        }
    }
}
