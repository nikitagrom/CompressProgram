package gromov.nikita.compress.gui_mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    // North components
    private JPanel northPanel = new JPanel();
    private JLabel actionLabel = new JLabel("Выберете действие");
    private String s[] = {"Компрессия", "Декомпрессия"};
    private JComboBox actionComboBox = new JComboBox(s);
    private JButton addButton = new JButton("Добавить файлы");
    private JButton delButton = new JButton("Очистить");

    // Center components
    private  JPanel centerPanel = new JPanel();
    private FileTableModel ftm = new FileTableModel();
    private  JTable table = new JTable(ftm);
    private JScrollPane jScrollPane = new JScrollPane(table);

    // South components
    private JPanel southPanel = new JPanel();
    private JPanel sub1Panel = new JPanel();
    private JPanel sub2Panel = new JPanel();
    private JLabel chooseLabel =  new JLabel("Выберете алгоритм:");
    private JRadioButton LZ77 = new JRadioButton("LZ77");
    private JRadioButton LZ78 = new JRadioButton("LZ78");
    private JRadioButton LZW = new JRadioButton("LZW");
    private JRadioButton PPM = new JRadioButton("PPM");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JCheckBox deleteCheckBox = new JCheckBox("Удалить исходные файлы");
    private JButton submit = new JButton("Запуск");
    private JTextArea infoArea = new JTextArea();

    public View() throws HeadlessException {

        // North init
        setEnabledSubmitButton(false);
        actionComboBox.setSelectedIndex(0);
        northPanel.add(actionLabel);
        northPanel.add(actionComboBox);
        northPanel.add(addButton);
        northPanel.add(delButton);

        // Center init

        jScrollPane.setPreferredSize(new Dimension(600,200));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(290);

        centerPanel.add(jScrollPane);


        // South init
        sub1Panel.setLayout(new GridLayout(6, 1));
        sub1Panel.add(chooseLabel);
        buttonGroup.add(LZ77);
        buttonGroup.add(LZ78);
        buttonGroup.add(LZW);
        buttonGroup.add(PPM);
        LZ77.setSelected(true);
        sub1Panel.add(LZ77);
        sub1Panel.add(LZ78);
        sub1Panel.add(LZW);
        sub1Panel.add(PPM);
        //sub1Panel.add(deleteCheckBox);
        sub1Panel.add(submit);

        infoArea.setPreferredSize(new Dimension(250, 200));
        JScrollPane jsp = new JScrollPane(infoArea);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Информация"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        jsp.getBorder()));
        //infoArea.setText("");
        //infoArea.setTabSize(12);

        sub2Panel.add(jsp);

        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(sub1Panel);
        southPanel.add(sub2Panel);

        // Root components
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(3, 3));
        this.setLocationRelativeTo(null);
        this.setTitle("Программа для сжатия файлов");
        this.setSize(650, 530);
        this.setResizable(false);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    // View API
    public FileTableModel getFtm() {
        return ftm;
    }
    public void setLineInfoArea(String msgLine) {
        infoArea.setText(msgLine);
    }
    public String getSelectedItemComboBox(){
        return (String) actionComboBox.getSelectedItem();
    }
    public String getSelectedRadioButton(){
        if (LZ77.isSelected()) return LZ77.getText();
        if (LZ78.isSelected()) return LZ78.getText();
        if (LZW.isSelected()) return LZW.getText();
        if (PPM.isSelected()) return PPM.getText();
        return "None";
    }
    public boolean getSelectedCheckBox(){
        return deleteCheckBox.isSelected();
    }
    public void updateTableUI(){
        table.updateUI();

    }
    public void setEnabledAlgsButtons(boolean isVisible){
        if (isVisible) {
            chooseLabel.setEnabled(true);
            LZ77.setEnabled(true);
            LZ78.setEnabled(true);
            LZW.setEnabled(true);
            PPM.setEnabled(true);
        } else {
            chooseLabel.setEnabled(false);
            LZ77.setEnabled(false);
            LZ78.setEnabled(false);
            LZW.setEnabled(false);
            PPM.setEnabled(false);
        }
    }
    public void setEnabledSubmitButton(boolean isVisible) {
        if (isVisible) submit.setEnabled(true);
        else submit.setEnabled(false);
    }

    public void addAddButtonListener(ActionListener listener){
        addButton.addActionListener(listener);
    }
    public void addDelButtonListener(ActionListener listener){
        delButton.addActionListener(listener);
    }
    public void addSubmitListener(ActionListener listener){
        submit.addActionListener(listener);
    }
    public void addComboBoxListener(ActionListener listener) {
        actionComboBox.addActionListener(listener);
    }
}