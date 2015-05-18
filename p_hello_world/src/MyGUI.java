import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by huangca on 2015/4/30.
 */
public class MyGUI {
   private   JPanel rootp;
    public MyGUI(){
       rootp=new JPanel();
        rootp.setLayout(new BoxLayout(rootp,BoxLayout.PAGE_AXIS));
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        JLabel l1=new JLabel("Dim 1");
        JRadioButton rUn= new JRadioButton("Unselected", true);
        JRadioButton rLeft = new JRadioButton("Left");
        rLeft.setActionCommand("Left");
        JRadioButton rRight = new JRadioButton("Right");
        //rUn.setMnemonic(KeyEvent.VK_0);
       // rLeft.setMnemonic(KeyEvent.VK_1);
       // rRight.setMnemonic(KeyEvent.VK_2);
        ButtonGroup group = new ButtonGroup();
        group.add(rUn);
        group.add(rLeft);
        group.add(rRight);
        p.add(l1);
        p.add(rUn);
        p.add(rLeft);
        p.add(rRight);
        JButton XMLButton=new JButton("XML");
        XMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test print");
                ParseXml parsex = new ParseXml();
                String xmlPath = "F:\\1_study\\p_hello_world\\src\\Dim.xml";
                parsex.saveToXML(xmlPath);

                //ButtonModel A= GroupA.getSelection();
                //ButtonModel B=GroupB.getSelection();
                XmlNode A = new XmlNode();
                ButtonModel TA =group.getSelection();
                A.select = TA.getActionCommand();
                //System.out.println(GroupA.getSelection().getActionCommand());
                // XmlNode B=new XmlNode();
                //B.select=GroupB.getSelection().getActionCommand();
                parsex.alterToXml(xmlPath, A);
                //parsex.alterToXml(xmlPath,B);


            }
        });
        p.add(XMLButton);
       rootp.add(p);

    }

    public JPanel getRoot(){return rootp;}

}
