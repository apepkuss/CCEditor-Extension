import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by huangca on 2015/4/15.
 */


public class HelloWorld implements ToolWindowFactory {
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        Component component = toolWindow.getComponent();
        //component.getParent().add(new JLabel("Hello, World!"));
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        JLabel l1=new JLabel("Dim 1");
        JRadioButton rUn= new JRadioButton("Unselected", true);
        JRadioButton rLeft = new JRadioButton("Left");
        JRadioButton rRight = new JRadioButton("Right");
        rUn.setMnemonic(KeyEvent.VK_0);
        rLeft.setMnemonic(KeyEvent.VK_1);
        rRight.setMnemonic(KeyEvent.VK_2);
        ButtonGroup group = new ButtonGroup();
        group.add(rUn);
        group.add(rLeft);
        group.add(rRight);
        p.add(l1);
        p.add(rUn);
        p.add(rLeft);
        p.add(rRight);


        //JPanel p2 = new JPanel();
        //p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        JLabel l2=new JLabel("Dim 2");
        JRadioButton rUn2= new JRadioButton("Unselected", true);
        JRadioButton rLeft2 = new JRadioButton("Left");
        JRadioButton rRight2 = new JRadioButton("Right");
        rUn2.setMnemonic(KeyEvent.VK_0);
        rLeft2.setMnemonic(KeyEvent.VK_1);
        rRight2.setMnemonic(KeyEvent.VK_2);
        ButtonGroup group2 = new ButtonGroup();
        group2.add(rUn2);
        group2.add(rLeft2);
        group2.add(rRight2);
        p.add(l2);
        p.add(rUn2);
        p.add(rLeft2);
        p.add(rRight2);

        component.getParent().add(p);

    }
}
