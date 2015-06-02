import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Vector;

/**
 * Created by huangca on 2015/5/23.
 */

class TePanel{
    private JPanel ap;
    private Border ab;
    private JPanel apx;
    private JLabel labx;
    private JLabel label;
    private JButton named;
    // private JPanel lable_p;
    private JRadioButton unselect;
    private JRadioButton left;
    private JRadioButton right;
    private JButton delete;
    private JColorChooser jcc;
    private JButton choseC;
    //private  JButton add;
    private JComboBox cb;
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

       // panel.setPreferredSize(dimemsion);

        return panel;
    }




    public TePanel(String name,int count){
        tagcount=new JLabel(Integer.toString(count));
        ap=createPanel(name,0,0);
        ap.setLayout(new BoxLayout(ap, BoxLayout.PAGE_AXIS));
        ap.setOpaque(true);
      //  ap.setBackground(Color.lightGray);
        // ab=new TitledBorder("Dim");

        labx=new JLabel();

        labx.setText("                                   x");
        //Color[] colorlist={Color.RED,Color.green,Color.blue};
        //cb = new ComboBox(colorlist);
        //cb.setMaximumSize(cb.getPreferredSize());
        //cb.setSelectedIndex(0);
        label=new JLabel(name);
        label.setOpaque(true);
        named=new JButton("rename Dim");
        //lable_p=new JPanel();
        unselect=new JRadioButton("Unselected", true);
        unselect.setActionCommand("Unselected");
        //unselect.setBackground(Color.lightGray);
        left=new JRadioButton("Left");
        left.setActionCommand("Left");
        //left.setBackground(Color.lightGray);
        right=new JRadioButton("Right");
        right.setActionCommand("Right");
        //right.setBackground(Color.lightGray);
        delete=new JButton("Delete");
        jcc=new JColorChooser();
        choseC=new JButton("color");
        //inis button
        group=new ButtonGroup();
        group.add(unselect);
        group.add(left);
        group.add(right);
        ap.add(labx);
        // ap.add(label);

        // lable_p.add(label);
        //  lable_p.add(named);
        //ap.add(lable_p);
        ap.add(unselect);
        ap.add(left);
        ap.add(right);
        //ap.add(jcc);
        ap.add(choseC);
        ap.add(named);

        //ap.add(cb);
        // ap.add(delete);
        //ap.setBorder(ab);


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
    public JLabel getLabx(){return labx;}
    public JComboBox getCb(){return cb;}
    public JButton getChoseC(){return choseC;}
}

public class CCEditor {
   static  int count=1;
     static Vector<TePanel> panelv=new Vector();
    static String xmlPath="src\\Dim.xml";

    public static void updateXml(){
        ParseXml parsex=new ParseXml();
       // String xmlPath="F:\\1_study\\CCEditor_2\\src\\Dim.xml";

        //String xmlPath="src\\Dim.xml";

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

    public static void readXml(){
        ParseXml parsex=new ParseXml();
       // String xmlPath="F:\\1_study\\CCEditor_2\\src\\Dim.xml";
        parsex.readToXML(xmlPath);

    }



    public static void removeTransparencySlider(JColorChooser jc) throws Exception {

        AbstractColorChooserPanel[] colorPanels = jc.getChooserPanels();
        for (int i = 1; i < colorPanels.length; i++) {
            AbstractColorChooserPanel cp = colorPanels[i];

            Field f = cp.getClass().getDeclaredField("panel");
            f.setAccessible(true);

            Object colorPanel = f.get(cp);
            Field f2 = colorPanel.getClass().getDeclaredField("spinners");
            f2.setAccessible(true);
            Object spinners = f2.get(colorPanel);

            Object transpSlispinner = Array.get(spinners, 3);
            if (i == colorPanels.length - 1) {
                transpSlispinner = Array.get(spinners, 4);
            }
            Field f3 = transpSlispinner.getClass().getDeclaredField("slider");
            f3.setAccessible(true);
            JSlider slider = (JSlider) f3.get(transpSlispinner);
            slider.setEnabled(false);
            Field f4 = transpSlispinner.getClass().getDeclaredField("spinner");
            f4.setAccessible(true);
            JSpinner spinner = (JSpinner) f4.get(transpSlispinner);
            spinner.setEnabled(false);
        }
    }


    public static void runEditor(){
        JFrame frame = new JFrame("FrameDemo");
        frame.setSize(new Dimension(50,1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel rootp=new JPanel();



        rootp.setLayout(new BoxLayout(rootp, BoxLayout.PAGE_AXIS));
        JPanel panelt=new JPanel();
        JScrollPane scrPane = new JScrollPane(panelt);
        panelt.setLayout(new BoxLayout(panelt,BoxLayout.PAGE_AXIS));
        JPanel panelb=new JPanel();
        panelb.setLayout(new BoxLayout(panelb,BoxLayout.X_AXIS));






        MouseAdapter mouseAdapter2=new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //super.mouseEntered(e);
                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getAp()){
                       panelv.get(i).getNamed().setVisible(true);
                    }
                }
            }
           public void mouseExited(MouseEvent e){
               for (int i=0;i<panelv.size();i++){
                   if(e.getSource()==panelv.get(i).getAp()){
                       panelv.get(i).getNamed().setVisible(false);
                   }
               }

           }
        };



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


        ActionListener actionListener5=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<panelv.size();i++){
                    if(e.getSource()==panelv.get(i).getCb()){
                        panelv.get(i).getAp().setBackground((Color) panelv.get(i).getCb().getSelectedItem());
                    }
                }
            }
        };

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
                /*
                ParseXml parsex=new ParseXml();
                String xmlPath="F:\\1_study\\CCEditor_2\\src\\Dim.xml";
                parsex.saveToXML(xmlPath);
                for(int i=0;i<panelv.size();i++){
                    XmlNode x=new XmlNode();
                    TePanel t=panelv.get(i);
                    x.select=t.getGroup().getSelection().getActionCommand();

                    x.name=t.getLabel().getText();
                    x.id=t.getTagcount().getText();
                    parsex.alterToXml(xmlPath,x);
                }
*/
                updateXml();
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
               // panelv.get(panelv.size()-1).getAp().addMouseListener(mouseAdapter2);
                panelv.get(panelv.size() - 1).getUnselect().addActionListener(actionListener2);
                panelv.get(panelv.size() - 1).getLeft().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getRight().addActionListener(actionListener2);
                panelv.get(panelv.size()-1).getDelete().addActionListener(actionListener3);
                panelv.get(panelv.size()-1).getNamed().addActionListener(actionListener4);
              // panelv.get(panelv.size()-1).getNamed().setVisible(false);
                panelv.get(panelv.size() - 1).getLabx().addMouseListener(mouseAdapter1);
                //panelv.get(panelv.size()-1).getCb().addActionListener(actionListener5);
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
                    for (int i2 = 0; i2 < panelv.size(); i2++) {

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
               // String xmlPath="F:\\1_study\\CCEditor_2\\src\\Dim.xml";

                panelv=parsex.readToXML(xmlPath);
                //add actionLinsen
                for(int i=0;i<panelv.size();i++){
                    panelv.get(i).getUnselect().addActionListener(actionListener2);
                    panelv.get(i).getLeft().addActionListener(actionListener2);
                    panelv.get(i).getRight().addActionListener(actionListener2);
                    panelv.get(i).getDelete().addActionListener(actionListener3);
                    panelv.get(i).getNamed().addActionListener(actionListener4);
                    // panelv.get(panelv.size()-1).getNamed().setVisible(false);
                    panelv.get(i).getLabx().addMouseListener(mouseAdapter1);
                    //panelv.get(panelv.size()-1).getCb().addActionListener(actionListener5);
                    panelv.get(i).getChoseC().addActionListener(actionListener6);
                    panelt.add(panelv.get(i).getAp());
                }
                panelt.updateUI();

            }
        };




        addbutton.addActionListener(actionListener1);


       // xmlbutton.addActionListener(actionListenerEditor);
        JButton read=new JButton("Read");
      read.addActionListener(actionListener7);
        panelb.add(addbutton);
        panelb.add(read);
       // panelb.add(xmlbutton);
      //  panelt.add(addbutton);
        rootp.add(panelb);
        rootp.add(scrPane);
        //rootp.add(panelb);

        frame.getContentPane().add(rootp);

       // frame.pack();
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
