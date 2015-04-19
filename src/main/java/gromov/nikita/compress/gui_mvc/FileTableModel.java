package gromov.nikita.compress.gui_mvc;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTableModel extends AbstractTableModel {
    private int columnsCount = 4;
    private List<File> rows = new ArrayList<>();

    @Override
    public int getRowCount() {
        return rows.size();
    }
    @Override
    public int getColumnCount() {
        return columnsCount;
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "№";
            case 1: return "Имя файла";
            case 2: return "Размер, кБ";
            case 3: return "Путь к файлу";
        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > rows.size())
            throw new IllegalArgumentException("Error: rowIndex must be <= rows.size, rowIndex = " + rowIndex);

        File file = rows.get(rowIndex);
        switch (columnIndex) {
            case 0: return rows.indexOf(file);
            case 1: return file.getName();
            case 2: return file.length()/(float)1024;
            case 3: return file.getAbsolutePath();
        }
        return "None";
    }

    public void addRowsToView(File file) {
        rows.add(file);
        fireTableDataChanged();
    }
    public void clearAllRowsFromView() {
        rows = new ArrayList<>();
    }
}
