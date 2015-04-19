package gromov.nikita.compress;

import gromov.nikita.compress.gui_mvc.Controller;
import gromov.nikita.compress.gui_mvc.Model;
import gromov.nikita.compress.gui_mvc.View;

public class Main {
    public static void main(String[] args) {
        View theView = new View();
        Model theModel = new Model();
        Controller theController = new Controller(theView, theModel);
        theView.setVisible(true);
    }
}
