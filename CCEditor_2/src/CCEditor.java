import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

/**
 * Created by huangca on 2015/5/23.
 */

class TePanel{
    private JPanel ap;
    private JPanel panel_in;
    private JLabel labx;
    private JLabel label;
    private JButton named;

    private JRadioButton unselect;
    private JRadioButton left;
    private JRadioButton right;
    private JButton choseC;
    private ButtonGroup group;
    private JLabel tagcount;

    private JPanel createPanel(String name, int width, int height) {
        JPanel panel = new JPanel();

        Border border = null;
        if (name == "") {
            border = new TitledBorder("");
        } else {
            border = new TitledBorder("Dim: " + name);
        }

        panel.setBorder(border);

        Dimension dimemsion = null;
        if (width == 0 && height == 0) {
            dimemsion = new Dimension(45, 30);
        } else {
            dimemsion = new Dimension(width + 30, height + 45);
        }


        return panel;
    }




    public TePanel(String name,int count){
        tagcount=new JLabel(Integer.toString(count));
        ap=createPanel(name,0,0);
        ap.setLayout(new BoxLayout(ap, BoxLayout.PAGE_AXIS));
        ap.setAlignmentY(Component.LEFT_ALIGNMENT);
        ap.setOpaque(true);
        panel_in=new JPanel();
        panel_in.setLayout(new BoxLayout(panel_in,BoxLayout.X_AXIS));
        labx=new JLabel();

        labx.setText("                                   x");
        label=new JLabel(name);
        label.setOpaque(true);
        named=new JButton("rename Dim");
        unselect=new JRadioButton("Unselected", true);
        unselect.setActionCommand("Unselected");
        left=new JRadioButton("Left");
        left.setActionCommand("Left");
        right=new JRadioButton("Right");
        right.setActionCommand("Right");


        choseC=new JButton("color");
        //inis button
        group=new ButtonGroup();
        group.add(unselect);
        group.add(left);
        group.add(right);
        ap.add(labx);
        ap.add(unselect);
        ap.add(left);
        ap.add(right);
        ap.add(choseC);
        ap.add(named);


    }
    public JPanel getAp(){return ap;}
    public ButtonGroup getGroup(){return group;}
    public JLabel getLabel(){return label;}
    public JRadioButton getUnselect(){return unselect;}
    public JRadioButton getLeft(){return left;}
    public JRadioButton getRight(){return right;}
    public JButton getNamed(){return named;}
    public JLabel getTagcount(){return tagcount;}
    public JLabel getLabx(){return labx;}
    public JButton getChoseC(){return choseC;}
}

public class CCEditor {
   static  int count=1;
     static Vector<TePanel> panelv=new Vector();
    static String xmlPath="src\\Dim.xml";

    public static void updateXml(){
        ParseXml parsex=new ParseXml();

        parsex.saveToXML(xmlPath);
        for(int i=0;i<panelv.size();i++) {
            XmlNode x = new XmlNode();
            TePanel t = panelv.get(i);
            x.select = t.getGroup().getSelection().getActionCommand();
            x.name = t.getLabel().getText();
            x.id=t.getTagcount().getText();
            //x.color=Integer.toString(t.getAp().getBackground().getRGB());
            int red = t.getAp().getBackground().getRed();
            int green = t.getAp().getBackground().getGreen();
            int blue = t.getAp().getBackground().getBlue();
            String hex = String.format("0x%02x%02x%02x",red,green,blue);
            x.color=hex;
            parsex.alterToXml(xmlPath, x);
        }

    }







    public static void runEditor(){
        JFrame frame = new JFrame("CCEditorDemo");
        frame.setSize(new Dimension(50,1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //have 3 Jpanel rootp is the root panel of the UI, panelt is the container for each TePanel, panelb is container for "Add" and "Read" button
        JPanel rootp=new JPanel();



        rootp.setLayout(new BoxLayout(rootp, BoxLayout.PAGE_AXIS));
        JPanel panelt=new JPanel();
        JScrollPane scrPane = new JScrollPane(panelt);
        panelt.setLayout(new BoxLayout(panelt,BoxLayout.PAGE_AXIS));
        JPanel panelb=new JPanel();
        panelb.setLayout(new BoxLayout(panelb,BoxLayout.X_AXIS));








        // There are 1 mouseAdapter and 6 action listener, actionListener 1-7 but don't have3, 5.

        //mouseAdapter is for remove one dim, it add by the "x" label
        MouseAdapter mouseAdapter1=new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getLabx()){
                        panelt.remove(panelv.get(i).getAp());
                        panelv.remove(i);
                        updateXml();
                        panelt.updateUI();
                    }
                }
            }
        };






        //actionListener6 is for color choice, add by the "color" button
        ActionListener actionListener6=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getChoseC()){
                        Color initialcolor=Color.RED;
                        JColorChooser chooser=new JColorChooser();

                        Color color;

                                boolean state;
                                do {
                                    state=false;
                                    color = chooser.showDialog(new JFrame(), "Select a color", initialcolor);
                                    for (int i2 = 0; i2 < panelv.size(); i2++) {

                                        if (i2 != i && color.equals(panelv.get(i2).getAp().getBackground())) {
                                            state=true;
                                            JOptionPane.showMessageDialog(frame,"color replace");
                                            System.out.println("color replace");
                                        }


                                    }
                                }while(state);


                        panelv.get(i).getAp().setBackground(color);
                        panelv.get(i).getUnselect().setBackground(color);
                        panelv.get(i).getLeft().setBackground(color);
                        panelv.get(i).getRight().setBackground(color);
                        updateXml();
                    }
                }
            }
        };


        //actionListener4 is for rename the dim, add by "rename" button
        ActionListener actionListener4=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getNamed()){
                        String new_name=JOptionPane.showInputDialog("enter new Dim name: ");
                        panelv.get(i).getLabel().setText(new_name);
                        panelv.get(i).getAp().setBorder(new TitledBorder(new_name));
                        updateXml();
                        panelt.updateUI();
                    }
                }

            }
        };
        //actionListener2 add by each radio button, each choice will update the xml file
        ActionListener actionListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                updateXml();
            }
        };

        //actionLinstener1 is listener for "Add" button,
        ActionListener actionListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String name="Dim"+panelv.size();
                TePanel t=new TePanel(name,count);
                count++;
                panelv.add(t);
                panelv.get(panelv.size() - 1).getUnselect().addActionListener(actionListener2);
                panelv.get(panelv.size() - 1).getLeft().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getRight().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getNamed().addActionListener(actionListener4);
                panelv.get(panelv.size() - 1).getLabx().addMouseListener(mouseAdapter1);
                panelv.get(panelv.size()-1).getChoseC().addActionListener(actionListener6);
                panelt.add(panelv.get(panelv.size()-1).getAp());

                //random color
                Color randomc;
                boolean state;
                do {
                    state=false;
                    int R = (int) (Math.random( )*256);
                    int G = (int)(Math.random( )*256);
                    int B= (int)(Math.random( )*256);
                    randomc=new Color(R,G,B);
                    for (int i2 = 0; i2 < panelv.size(); i2++) {//check the random color is replace or not, if yes random again

                        if (randomc.equals(panelv.get(i2).getAp().getBackground())) {
                            state=true;

                        }


                    }
                }while(state);
                panelv.get(panelv.size()-1).getAp().setBackground(randomc);
                panelv.get(panelv.size()-1).getUnselect().setBackground(randomc);
                panelv.get(panelv.size()-1).getLeft().setBackground(randomc);
                panelv.get(panelv.size()-1).getRight().setBackground(randomc);
                //update
                panelt.updateUI();
                updateXml();

            }
        };

        //actionListener7 is add by "read" button, do the  similar thing for actionListener1
        ActionListener actionListener7=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParseXml parsex=new ParseXml();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(new JFrame("Select File"));
                if (result == JFileChooser.APPROVE_OPTION) {
                    File xmlFile=fileChooser.getSelectedFile();
                    xmlPath=xmlFile.getAbsolutePath();
                }else{
                    System.out.println("no select");
                }

                panelv=parsex.readToXML(xmlPath);
                panelt.removeAll();
                count=Integer.parseInt(panelv.get(panelv.size()-1).getTagcount().getText())+1;
                //add actionLinsen
                for(int i=0;i<panelv.size();i++){
                    panelv.get(i).getUnselect().addActionListener(actionListener2);
                    panelv.get(i).getLeft().addActionListener(actionListener2);
                    panelv.get(i).getRight().addActionListener(actionListener2);
                    panelv.get(i).getNamed().addActionListener(actionListener4);
                    panelv.get(i).getLabx().addMouseListener(mouseAdapter1);

                    panelv.get(i).getChoseC().addActionListener(actionListener6);
                    panelt.add(panelv.get(i).getAp());
                }
                panelt.updateUI();

            }
        };



        JButton addbutton=new JButton("Add");
        addbutton.addActionListener(actionListener1);


        JButton read=new JButton("Read");
      read.addActionListener(actionListener7);
        panelb.add(addbutton);
        panelb.add(read);
        rootp.add(panelb);
        rootp.add(scrPane);

        frame.getContentPane().add(rootp);

        frame.setVisible(true);

    }



    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runEditor();
            }
        });
    }
}
