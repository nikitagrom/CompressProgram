package gromov.nikita.compress.gui_mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller {
    private View theView;
    private Model theModel;

    public Controller(View theView, Model theModel) {
        this.theView = theView;
        this.theModel = theModel;

        theView.addAddButtonListener(new ChooseFileListener());
        theView.addDelButtonListener(new ChooseFileListener());
        theView.addComboBoxListener(new ComboBoxListener());
        theView.addSubmitListener(new SubmitListener());
    }

    class ChooseFileListener implements ActionListener {
        JFileChooser fc = new JFileChooser();

        @Override
        public void actionPerformed(ActionEvent e) {
            fc.setPreferredSize(new Dimension(600, 500));
            if ("Добавить файлы".equals(e.getActionCommand())) {
                fc.setMultiSelectionEnabled(true);

                int result = fc.showOpenDialog(theView);
                if (result == JFileChooser.APPROVE_OPTION){
                    File files[] = fc.getSelectedFiles();
                    for (File file : files) {
                        theView.getFtm().addRowsToView(file);
                        theModel.addRowsToModel(file);
                    }
                    theView.setEnabledSubmitButton(true);
                }

            }
            else if ("Очистить".equals(e.getActionCommand())) {
                theView.getFtm().clearAllRowsFromView();
                theModel.clearAllRowsFromModel();

                theView.setEnabledSubmitButton(false);
            }
            theView.updateTableUI();
        }
    }
    class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String methodName = theView.getSelectedRadioButton();
            if ("LZ77".equals(methodName)) theModel.setTypeOfCompressor(Model.LZ77);
            if ("LZ78".equals(methodName)) theModel.setTypeOfCompressor(Model.LZ78);
            if ("LZW".equals(methodName)) theModel.setTypeOfCompressor(Model.LZW);
            if ("PPM".equals(methodName)) theModel.setTypeOfCompressor(Model.PPM);

            String actionName = theView.getSelectedItemComboBox();
            if ("Компрессия".equals(actionName)) {
                theModel.compress();
                theView.setLineInfoArea(theModel.getMsg());
            }
            if ("Декомпрессия".equals(actionName)) {
                theModel.decompress();
                theView.setLineInfoArea(theModel.getMsg());
            }
        }
    }
    class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            JComboBox jcb = (JComboBox)e.getSource();
//            if ("Компрессия".equals( (String)jcb.getSelectedItem()))
//                theView.setEnabledAlgsButtons(true);
//
//            else if ("Декомпрессия".equals( (String)jcb.getSelectedItem()))
//                theView.setEnabledAlgsButtons(false);
        }

    }
}

