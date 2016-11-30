package gent.timdemey.migtool;

import gent.timdemey.migtool.creators.IMigPairCreator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gent.timdemey.migtool.creators.MigPairButtonCreator;
import gent.timdemey.migtool.creators.MigPairCheckBoxCreator;
import gent.timdemey.migtool.creators.MigPairComboBoxCreator;
import gent.timdemey.migtool.creators.MigPairIconLabelCreator;
import gent.timdemey.migtool.creators.MigPairLabelCreator;
import gent.timdemey.migtool.creators.MigPairPanelCreator;
import gent.timdemey.migtool.creators.MigPairRadioButtonCreator;
import gent.timdemey.migtool.creators.MigPairSeparatorCreator;
import gent.timdemey.migtool.creators.MigPairTextFieldCreator;
import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairPanel;
import net.miginfocom.swing.MigLayout;

public class MigBuildingTool extends JPanel {

    private List<IMigPair> comps = new ArrayList<>();
    private final JFrame parent;
    private final JPanel panelLayout;
    private final JPanel panelColRow;
    private final JPanel panelComps;
    private final JPanel content;
    private final JTextField layoutConstraints;
    private final JTextField columnConstraints;
    private final JTextField rowConstraints;
    private final JLabel labelLoading;
    private final JCheckBox checkPack;
    private final JButton buttonGenerate;
    private final Color colorOK = Color.green;
    private final Color colorError = Color.red;
    private final Border borderOK = new LineBorder(colorOK);
    private final Border borderNOK = new LineBorder(colorError);
    private final LAFChoice[] lookAndFeels;
    private static final Executor executor = Executors.newSingleThreadExecutor();

    private static class LAFChoice {
        private final LookAndFeelInfo info;
        
        private LAFChoice(LookAndFeelInfo info){
            this.info = info;
        }
        
        private LookAndFeelInfo getInfo (){
            return info;
        }

        @Override
        public String toString() {
            return info.getName();
        }
    }
    
    private MigBuildingTool(final JFrame parent, final JFrame builder) {
        super(new MigLayout("fill", "[][]10:10:100:push[]", "[][grow, fill][]"));
        this.parent = parent;
        this.content = new JPanel(new MigLayout("fill", "[fill, grow]", "[fill, grow]"));
        this.panelLayout = new JPanel(new MigLayout("", "[][grow, fill]", "[]"));
        this.panelColRow = new JPanel(new MigLayout("", "[][grow, fill]", "[][]"));
        this.panelComps = new JPanel(new MigLayout("", "[][grow, fill][][][][]", "[]"));
        this.layoutConstraints = new JTextField();
        this.columnConstraints = new JTextField();
        this.rowConstraints = new JTextField();        
        
        LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        this.lookAndFeels = new LAFChoice[infos.length];
        for (int i = 0; i < infos.length; i++){
            lookAndFeels[i] = new LAFChoice(infos[i]);
        }
        
        builder.setContentPane(content);

        final JComboBox<IMigPairCreator> compChoices = new JComboBox<>(new IMigPairCreator[]{
            new MigPairButtonCreator(), new MigPairLabelCreator(), new MigPairComboBoxCreator(),
            new MigPairTextFieldCreator(), new MigPairCheckBoxCreator(), new MigPairRadioButtonCreator(),
            new MigPairIconLabelCreator(), new MigPairSeparatorCreator(), new MigPairPanelCreator()
        });
       
        final JComboBox<LAFChoice> compLAFChoices = new JComboBox<>(lookAndFeels);
        
        compLAFChoices.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                // invokeLater: make sure the combobox is done with handling events.
                // otherwise we might get nullpointers if navigating the CB with
                // the keyboard arrow keys
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            UIManager.setLookAndFeel(((LAFChoice) compLAFChoices.getSelectedItem()).getInfo().getClassName());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                        }

                        SwingUtilities.updateComponentTreeUI(parent);
                        SwingUtilities.updateComponentTreeUI(builder);
                    }
                });
            }
        });

        JButton buttonAdd = new JButton(new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addTo((IMigPairCreator) compChoices.getSelectedItem());
            }
        });

        buttonGenerate = new JButton(new AbstractAction("Generate code...") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                labelLoading.setVisible(true);
                buttonGenerate.setEnabled(false);

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Map<Class<?>, int[]> compCounts = new HashMap<>();
                        final StringBuilder code = new StringBuilder();

                        code.append("JPanel panel = new JPanel(new MigLayout(\"")
                                .append(layoutConstraints.getText())
                                .append("\",\"")
                                .append(columnConstraints.getText())
                                .append("\",\"")
                                .append(rowConstraints.getText())
                                .append("\"));\n\n");

                        for (IMigPair pair : comps) {
                            Class<?> clazz = pair.getComponent().getClass();
                            if (!compCounts.containsKey(clazz)) {
                                compCounts.put(clazz, new int[1]);
                            }
                            int[] counter = compCounts.get(clazz);
                            counter[0]++;
                            int count = counter[0];

                            code.append("panel.add(");
                            code.append("my").append(clazz.getSimpleName()).append(count);
                            code.append(", \"").append(pair.getConstraints()).append("\");\n");
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MigBuildingTool.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JTextArea textArea = new JTextArea(code.toString());
                                textArea.setEditable(false);
                                textArea.setBorder(new LineBorder(Color.black, 1));
                                textArea.setFont(Font.decode("Consolas-14"));
                                labelLoading.setVisible(false);
                                buttonGenerate.setEnabled(true);
                                final JDialog dialog = new JDialog(parent, "Generated code", true);
                                JPanel dialogContent = new JPanel(new MigLayout("insets 10 10 10 10, wrap"));
                                dialogContent.add(textArea, "push, grow");
                                JButton buttonOK = new JButton(new AbstractAction("OK") {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dialog.dispose();
                                    }
                                });
                                dialog.getRootPane().setDefaultButton(buttonOK);
                                dialogContent.add(buttonOK, "pushx, center");
                                dialog.setContentPane(dialogContent);
                                dialog.pack();
                                dialog.setResizable(false);
                                dialog.setLocationRelativeTo(parent);
                                dialog.setVisible(true);
                            }
                        });
                    }
                };
                executor.execute(runnable);
            }
        });

        JTabbedPane tabs = new JTabbedPane();
        panelLayout.add(new JLabel("Layout constraints:"), "");
        panelLayout.add(layoutConstraints);
        panelColRow.add(new JLabel("Column constraints:"), "");
        panelColRow.add(columnConstraints, "wrap");
        panelColRow.add(new JLabel("Row constraints:"), "");
        panelColRow.add(rowConstraints, "");

        DocumentListener layColRowListener = new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent arg0) {
                rebuild();
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                rebuild();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }
        };
        layoutConstraints.getDocument().addDocumentListener(layColRowListener);
        columnConstraints.getDocument().addDocumentListener(layColRowListener);
        rowConstraints.getDocument().addDocumentListener(layColRowListener);

        JScrollPane scroll = new JScrollPane(panelComps);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(4);
        tabs.addTab("Layout", panelLayout);
        tabs.addTab("Columns/Rows", panelColRow);
        tabs.addTab("Components", scroll);

        checkPack = new JCheckBox("Pack on change");
        checkPack.setBorder(BorderFactory.createEmptyBorder());
        checkPack.setSelected(true);

        labelLoading = new JLabel(getIcon("loading.gif"));

        add(compChoices, "span 2, split 2");
        add(buttonAdd, "");
        add(compLAFChoices, "gapbefore push, wrap");
        add(tabs, "span 3, push, grow, wrap");
        add(checkPack, "span, split 3, gapafter push");
        add(labelLoading, "width 16!");
        add(buttonGenerate, "");

        labelLoading.setVisible(false);
        rebuild();
    }

    private void addTo(IMigPairCreator creator) {
        final IMigPair pair = creator.createComponent();

        JButton labelUp = pair.getButtonUp();
        JButton labelDown = pair.getButtonDown();
        JButton labelEdit = pair.getButtonEdit();
        JButton labelDelete = pair.getButtonDelete();

        labelUp.setAction(new AbstractAction("", getIcon("up")) {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = comps.indexOf(pair);
                if (index > 0) {
                    comps.remove(index);
                    comps.add(--index, pair);
                    for (int i = index; i < comps.size(); i++) {
                        IMigPair _pair = comps.get(i);
                        JLabel label = _pair.getLabel();
                        JTextField textfield = _pair.getTextField();
                        JButton buttonUp = _pair.getButtonUp();
                        JButton buttonDown = _pair.getButtonDown();
                        JButton buttonEdit = _pair.getButtonEdit();
                        JButton buttonDelete = _pair.getButtonDelete();

                        panelComps.add(label, "cell 0 " + i);
                        panelComps.add(textfield, "cell 1 " + i);

                        panelComps.add(buttonUp, "width 20!,height 20!,cell 2 " + i);
                        panelComps.add(buttonDown, "width 20!,height 20!,cell 3 " + i);
                        panelComps.add(buttonEdit, "width 20!,height 20!,cell 4 " + i);
                        panelComps.add(buttonDelete, "width 20!,height 20!,cell 5 " + i);
                    }
                    panelComps.revalidate();
                    rebuild();
                }
            }
        });
        labelDown.setAction(new AbstractAction("", getIcon("down")) {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = comps.indexOf(pair);
                if (index < comps.size() - 1) {
                    comps.remove(index);
                    comps.add(++index, pair);
                    for (int i = index - 1; i < comps.size(); i++) {
                        IMigPair _pair = comps.get(i);
                        JLabel label = _pair.getLabel();
                        JTextField textfield = _pair.getTextField();
                        JButton buttonUp = _pair.getButtonUp();
                        JButton buttonDown = _pair.getButtonDown();
                        JButton buttonEdit = _pair.getButtonEdit();
                        JButton buttonDelete = _pair.getButtonDelete();

                        panelComps.add(label, "cell 0 " + i);
                        panelComps.add(textfield, "cell 1 " + i);

                        panelComps.add(buttonUp, "width 20!,height 20!,cell 2 " + i);
                        panelComps.add(buttonDown, "width 20!,height 20!,cell 3 " + i);
                        panelComps.add(buttonEdit, "width 20!,height 20!,cell 4 " + i);
                        panelComps.add(buttonDelete, "width 20!,height 20!,cell 5 " + i);
                    }
                    panelComps.revalidate();
                    rebuild();
                }
            }
        });
        labelEdit.setAction(new AbstractAction("", getIcon("edit")) {
            {
                setEnabled(pair.isEditable());
            }

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String input = (String) JOptionPane.showInputDialog(parent, pair.getEditMessage(), "Edit the component",
                        JOptionPane.PLAIN_MESSAGE, null, null, pair.getContentValue());
                if (input != null) {
                    pair.setContentValue(input);
                }
            }
        });
        labelDelete.setAction(new AbstractAction("", getIcon("delete")) {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = comps.indexOf(pair);

                comps.remove(index);
                panelComps.remove(pair.getLabel());
                panelComps.remove(pair.getTextField());
                panelComps.remove(pair.getButtonUp());
                panelComps.remove(pair.getButtonDown());
                panelComps.remove(pair.getButtonEdit());
                panelComps.remove(pair.getButtonDelete());
                for (int i = index; i < comps.size(); i++) {
                    IMigPair _pair = comps.get(i);
                    JLabel label = _pair.getLabel();
                    JTextField textfield = _pair.getTextField();
                    JButton buttonUp = _pair.getButtonUp();
                    JButton buttonDown = _pair.getButtonDown();
                    JButton buttonEdit = _pair.getButtonEdit();
                    JButton buttonDelete = _pair.getButtonDelete();

                    panelComps.add(label, "cell 0 " + i);
                    panelComps.add(textfield, "cell 1 " + i);

                    panelComps.add(buttonUp, "width 20!,height 20!,cell 2 " + i);
                    panelComps.add(buttonDown, "width 20!,height 20!,cell 3 " + i);
                    panelComps.add(buttonEdit, "width 20!,height 20!,cell 4 " + i);
                    panelComps.add(buttonDelete, "width 20!,height 20!,cell 5 " + i);
                }
                panelComps.revalidate();
                panelComps.repaint();
                rebuild();
            }
        });
        panelComps.add(pair.getLabel(), "cell 0 " + comps.size());
        panelComps.add(pair.getTextField(), "cell 1 " + comps.size());
        panelComps.add(labelUp, "width 20!,height 20!,cell 2 " + comps.size());
        panelComps.add(labelDown, "width 20!,height 20!,cell 3 " + comps.size());
        panelComps.add(labelEdit, "width 20!,height 20!,cell 4 " + comps.size());
        panelComps.add(labelDelete, "width 20!,height 20!,cell 5 " + comps.size());

        panelComps.revalidate();

        pair.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                pair.setConstraints(pair.getTextField().getText());
                rebuild();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                pair.setConstraints(pair.getTextField().getText());
                rebuild();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        comps.add(pair);

        rebuild();
    }

    private void rebuild() {
        content.removeAll();

        String layout = layoutConstraints.getText();
        String cols = columnConstraints.getText();
        String rows = rowConstraints.getText();

        MigLayout mig = new MigLayout();

        JTabbedPane pane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, layoutConstraints);

        boolean layoutError = false;
        boolean colrowError = false;
        boolean compError = false;

        try {
            mig.setLayoutConstraints(layout);
            layoutConstraints.setBorder(borderOK);
            pane.setIconAt(0, getIcon("green2.gif"));
        } catch (RuntimeException e) {
            layoutConstraints.setBorder(borderNOK);
            pane.setIconAt(0, getIcon("red"));
            layoutError = true;
        }

        try {
            mig.setColumnConstraints(cols);
            columnConstraints.setBorder(borderOK);
        } catch (RuntimeException e) {
            colrowError = true;
            columnConstraints.setBorder(borderNOK);
        }
        try {
            mig.setRowConstraints(rows);
            rowConstraints.setBorder(borderOK);
        } catch (RuntimeException e) {
            colrowError = true;
            rowConstraints.setBorder(borderNOK);
        }
        if (colrowError) {
            pane.setIconAt(1, getIcon("red"));
        } else {
            pane.setIconAt(1, getIcon("green2.gif"));
        }

        JPanel panel = new JPanel(mig);
        for (IMigPair _pair : comps) {
            try {
                panel.add(_pair.getComponent(), _pair.getConstraints());
                _pair.getTextField().setBorder(borderOK);
            } catch (IllegalArgumentException e) {
                _pair.getTextField().setBorder(borderNOK);
                compError = true;
            }
        }
        if (compError) {
            pane.setIconAt(2, getIcon("red"));
        } else {
            pane.setIconAt(2, getIcon("green2.gif"));
        }
        content.add(panel);
        content.revalidate();
        final JFrame buildFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, content);

        if (checkPack.isSelected()) {
            buildFrame.setMinimumSize(null);
            buildFrame.pack();
            buildFrame.setMinimumSize(buildFrame.getSize());
        }

        content.setBackground(layoutError || colrowError || compError ? colorError : colorOK);
    }

    public static ImageIcon getIcon(String name) {
        return new ImageIcon(MigBuildingTool.class.getResource(name.contains(".") ? name : name + ".png"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame("MiG Layout Building Tool");
                final JFrame buildFrame = new JFrame("Showcase");

                buildFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                Image icon = getIcon("builder").getImage();
                frame.setIconImage(icon);
                frame.setContentPane(new MigBuildingTool(frame, buildFrame));
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.setMinimumSize(new Dimension(400, 300));
                frame.pack();
                frame.setLocationRelativeTo(null);

                WindowListener listener = new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int answer = JOptionPane.showOptionDialog(
                                e.getWindow(), "Do you really want to quit?", "Confirmation",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                new String[]{"Yes", "No"}, "No");
                        if (answer == 0) {
                            frame.dispose();
                            buildFrame.dispose();
                            System.exit(0);
                        }
                    }
                };
                frame.addWindowListener(listener);
                buildFrame.addWindowListener(listener);

                frame.setVisible(true);

                Point location = frame.getLocation();
                location.translate(frame.getWidth(), 0);
                buildFrame.setIconImage(getIcon("random").getImage());
                buildFrame.setLocation(location);
                buildFrame.setMinimumSize(new Dimension(200, 150));
                buildFrame.setVisible(true);
            }
        });
    }
}
