import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import sun.security.provider.certpath.Vertex;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Created by huangca on 2015/4/14.
 */

class BoardTile{
    JPanel panel;
    Border border;

    public BoardTile(){

        panel=new JPanel();
        border=new TitledBorder("Dim");
        panel.setBorder(border);

    }

}


class TePanel{
    private JPanel ap;
    private Border ab;
    private JLabel label;
    private JButton named;
   // private JPanel lable_p;
    private JRadioButton unselect;
    private JRadioButton left;
    private JRadioButton right;
    private JButton delete;
    //private  JButton add;
    private ButtonGroup group;
    private JLabel tagcount;

    public TePanel(String name,int count){
        tagcount=new JLabel(Integer.toString(count));
        ap=new JPanel();
        ap.setLayout(new BoxLayout(ap, BoxLayout.PAGE_AXIS));
        ab=new TitledBorder("Dim");
        label=new JLabel(name);
        label.setOpaque(true);
        named=new JButton("rename Dim");
        //lable_p=new JPanel();
        unselect=new JRadioButton("Unselected", true);
        unselect.setActionCommand("Unselected");
        left=new JRadioButton("Left");
        left.setActionCommand("Left");
        right=new JRadioButton("Right");
        right.setActionCommand("Right");
        delete=new JButton("Delete");
        //inis button
        group=new ButtonGroup();
        group.add(unselect);
        group.add(left);
        group.add(right);
        ap.add(label);
        ap.add(named);
       // lable_p.add(label);
      //  lable_p.add(named);
        //ap.add(lable_p);
        ap.add(unselect);
        ap.add(left);
        ap.add(right);
        ap.add(delete);
        ap.setBorder(ab);


    }
    public JPanel getAp(){return ap;}
    public ButtonGroup getGroup(){return group;}
    public JLabel getLabel(){return label;}
    public JRadioButton getUnselect(){return unselect;}
    public JRadioButton getLeft(){return left;}
    public JRadioButton getRight(){return right;}
    public JButton getDelete(){return delete;}
    public JButton getNamed(){return named;}
    public JLabel getTagcount(){return tagcount;}
}

public class MyComponent implements ApplicationComponent,ToolWindowFactory {
   private int count;
    private Vector<TePanel> panelv;
    public MyComponent() {
        count=0;
        panelv=new Vector();

    }

    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "MyComponent";
    }

    public void sayHello() {

        // Show dialog with message

        Messages.showMessageDialog(

                "cc test",

                "Sample",

                Messages.getInformationIcon()

        );

    }

    public void updateXml(){
        ParseXml parsex=new ParseXml();
        String xmlPath="F:\\1_study\\p_hello_world\\src\\Dim.xml";
        parsex.saveToXML(xmlPath);
        for(int i=0;i<panelv.size();i++) {
            XmlNode x = new XmlNode();
            TePanel t = panelv.get(i);
            x.select = t.getGroup().getSelection().getActionCommand();
            x.name = t.getLabel().getText();
            x.id=t.getTagcount().getText();
            parsex.alterToXml(xmlPath, x);
        }

    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {

        Component component = toolWindow.getComponent();
        JPanel rootp=new JPanel();

        rootp.setLayout(new BoxLayout(rootp, BoxLayout.PAGE_AXIS));
        JPanel panelt=new JPanel();
        panelt.setLayout(new BoxLayout(panelt,BoxLayout.PAGE_AXIS));
        JPanel panelb=new JPanel();
        panelb.setLayout(new BoxLayout(panelb, BoxLayout.PAGE_AXIS));






        ActionListener actionListenerEditor=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempstring=FileEditorManager.getInstance(project).getSelectedTextEditor().getDocument().getText();
                System.out.println(tempstring);

            }
        };

        ActionListener actionListener4=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getNamed()){
                        String new_name=JOptionPane.showInputDialog("enter new Dim name: ");
                        panelv.get(i).getLabel().setText(new_name);
                        updateXml();
                        panelt.updateUI();
                    }
                }

            }
        };

        ActionListener actionListener3=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getDelete()){
                        panelt.remove(panelv.get(i).getAp());
                        panelv.remove(i);
                        updateXml();
                        panelt.updateUI();
                    }
                }
            }
        };

        JButton xmlbutton=new JButton("Editor");
        ActionListener actionListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ParseXml parsex=new ParseXml();
                String xmlPath="F:\\1_study\\p_hello_world\\src\\Dim.xml";
                parsex.saveToXML(xmlPath);
                for(int i=0;i<panelv.size();i++){
                    XmlNode x=new XmlNode();
                    TePanel t=panelv.get(i);
                    x.select=t.getGroup().getSelection().getActionCommand();
                    if(t.getGroup().getSelection().getActionCommand()=="Unselected"){
                       t.getLabel().setBackground(Color.gray);
                        //t.getAp().setBackground(Color.gray);
                    }
                    else if(t.getGroup().getSelection().getActionCommand()=="Left"){
                        t.getLabel().setBackground(Color.blue);
                    }
                    else if (t.getGroup().getSelection().getActionCommand()=="Right"){
                        t.getLabel().setBackground(Color.cyan);

                    }
                    x.name=t.getLabel().getText();
                    x.id=t.getTagcount().getText();
                    parsex.alterToXml(xmlPath,x);
                }

            }
        };

        JButton addbutton=new JButton("Add");
        ActionListener actionListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String name="Dim"+panelv.size();
                TePanel t=new TePanel(name,count);
                count++;
               // System.out.print(panelv.size());
                panelv.add(t);
                panelv.get(panelv.size()-1).getUnselect().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getLeft().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getRight().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getDelete().addActionListener(actionListener3);
                panelv.get(panelv.size()-1).getNamed().addActionListener(actionListener4);
                panelt.add(panelv.get(panelv.size()-1).getAp());
                panelt.updateUI();
                updateXml();

            }
        };
        addbutton.addActionListener(actionListener1);


        xmlbutton.addActionListener(actionListenerEditor);

        panelb.add(addbutton);
        panelb.add(xmlbutton);
        rootp.add(panelt);
        rootp.add(panelb);
     // DimGUI ui=new DimGUI();


        component.getParent().add(rootp);

       // MyGUI gui=new MyGUI();
       // component.getParent().add(gui.getRoot());

    }



}
