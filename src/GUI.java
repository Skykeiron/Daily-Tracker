import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GUI implements ActionListener {

    private final JFrame frame = new JFrame("Daily Manager");

    private final List<JButton> buttons = new ArrayList<>();

    private final JTextArea text = new JTextArea("Awaiting printed lines... \n");
    private final JScrollPane dailyScroll =  new JScrollPane(text);

    private boolean firstLine;

    private static final String DAILY_PATH = "./dailies.txt";
    private static final String HERB_PATH = "./herb.txt";
    private static final String BAKS_PATH = "./baks.txt";
    private static final String PULP_PATH = "./pulp.txt";
    private static final String EXTRA_PATH = "./extra.txt";



    private boolean extra = false, herb = false, dailies = false, baks = false, pulp = false;

    @NotNull
    private final ActionListener COMPLETE = e -> {
        try {
            deleteDaily(false);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    };

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            System.out.println("Error loading skin");
        }
        new GUI();
    }

    public GUI() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 312, 157);
        frame.setResizable(false);
        frame.setContentPane(new JPanel());
        frame.getContentPane().setLayout(null);
        ((JComponent) frame.getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
        String imagePath = "icon.png";
        InputStream imgStream = GUI.class.getResourceAsStream(imagePath);
        BufferedImage myImg = null;
        try {
            myImg = ImageIO.read(imgStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(myImg);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        build();
    }


    private void build() {
        for (int i = 0; i < 1; i++)
            buttons.add(new JButton());
        for (JButton b : buttons) {
            frame.getContentPane().add(b);
        }
        // JMenu
        JMenu menu = new JMenu("Toggle");
        JMenuBar mb = new JMenuBar();
        String[] mainButtons = new String[] {"Dailies", "Herb Run", "Pulp Run", "Bakriminel bolts", "Extra shops"};
        String[] menuBar = mainButtons;
        int jmenubar = mainButtons.length;
        for (int var7 = 0; var7 < jmenubar; ++var7) {
            String name = menuBar[var7];
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(name);
            if (name.equalsIgnoreCase("-")) {
                menu.addSeparator();
            } else {
                menuItem.addActionListener(this);
                menu.add(menuItem);
            }
        }
        menu.setForeground(Color.WHITE);
        mb.add(menu);
        mb.add(buttons.get(0));
        buttons.get(0).setText("Complete");
        buttons.get(0).setForeground(Color.WHITE);
        buttons.get(0).addActionListener(COMPLETE);
        frame.setJMenuBar(mb);
        text.setForeground(Color.WHITE);
        dailyScroll.setBounds(2, 2, 300, 100);
        text.setEditable(false);
        text.setFont(text.getFont().deriveFont(Font.BOLD, 12f));
        frame.getContentPane().add(dailyScroll);
    }

    public void append(String lines) {
        if (firstLine) {
            text.append(lines + "\n");
            return;
        }
        text.setText("-> " + lines + "\n");
        firstLine = true;
    }

    public void load(int stage) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader((stage == 0 ? DAILY_PATH : stage == 1 ? HERB_PATH : stage == 2 ? BAKS_PATH : stage == 3 ? PULP_PATH : EXTRA_PATH)));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty())
                continue;
            append(line);
        }
        br.close();
        text.setCaretPosition(0);
    }

    public void deleteDaily(boolean all) throws BadLocationException {
        if (all)
            text.selectAll();
        else
            text.select(text.getLineStartOffset(0), text.getLineEndOffset(0));
        text.replaceSelection("" + (all ? "" : "-> "));
        firstLine = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd != null) {
            if (cmd.equalsIgnoreCase("Dailies")) {
                dailies = !dailies;
                try {
                    if (dailies)
                        load(0);
                    else
                        deleteDaily(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (cmd.equalsIgnoreCase("Herb Run")) {
                herb = !herb;
                try {
                    if (herb)
                        load(1);
                    else
                        deleteDaily(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (cmd.equalsIgnoreCase("Pulp Run")) {
                pulp = !pulp;
                try {
                    if (pulp)
                        load(3);
                    else
                        deleteDaily(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (cmd.equalsIgnoreCase("Bakriminel bolts")) {
                baks = !baks;
                try {
                    if (baks)
                        load(2);
                    else
                        deleteDaily(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (cmd.equalsIgnoreCase("Extra shops")) {
                baks = !baks;
                try {
                    if (baks)
                        load(4);
                    else
                        deleteDaily(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}