import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by huangca on 2015/4/28.
 */
public class DimGUI implements ActionListener {
    private JPanel rootpanel;
    private JEditorPane dimBEditorPane;
    private JRadioButton unselectedRadioButton;
    private JRadioButton leftRadioButton;
    private JRadioButton rightRadioButton;
    private JRadioButton unselectRadioButton;
    private JRadioButton left1RadioButton;
    private JRadioButton right1RadioButton;
    private JPanel leftpanel;
    private JPanel rightpanel;
    private JPanel panela;
    private JPanel panelb;
    private JLabel DimA;
    private JLabel DimB;
    private JButton XMLButton;
    private ButtonGroup GroupB;
     ButtonGroup GroupA;


    public DimGUI() {
        XMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test print");
                ParseXml parsex=new ParseXml();
                String xmlPath="F:\\1_study\\p_hello_world\\src\\Dim.xml";
                parsex.saveToXML(xmlPath);

                //ButtonModel A= GroupA.getSelection();
                //ButtonModel B=GroupB.getSelection();
                XmlNode A=new XmlNode();
              ButtonModel TA=GroupA.getSelection();
                A.select=TA.getActionCommand();
                //System.out.println(GroupA.getSelection().getActionCommand());
               // XmlNode B=new XmlNode();
                //B.select=GroupB.getSelection().getActionCommand();
                parsex.alterToXml(xmlPath,A);
                //parsex.alterToXml(xmlPath,B);


            }
        });


        unselectedRadioButton.setActionCommand("unselect");

        unselectedRadioButton.addActionListener(this);
        leftRadioButton.addActionListener(this);
        rightRadioButton.addActionListener(this);




    }

    public void actionPerformed(ActionEvent e) {
     /*   if (e.getSource() == leftRadioButton) {
            ButtonModel model = leftRadioButton.getModel();
            String ac = model.getActionCommand();
            ac = left1RadioButton.getActionCommand();
            GroupA.setSelected(model, true);
        } else if (e.getSource() == right1RadioButton) {
            // todo
        }


*/
       // GroupA.setSelected(leftRadioButton.getModel(),true);
    System.out.println(GroupA.getSelection().getActionCommand());
    }


    public JPanel getRoot(){return rootpanel;}
}
