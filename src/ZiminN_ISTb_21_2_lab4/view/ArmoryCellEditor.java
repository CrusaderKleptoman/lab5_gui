package ZiminN_ISTb_21_2_lab4.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;

public class ArmoryCellEditor extends AbstractCellEditor implements TableCellEditor
{
    // Редактор
    private JSpinner editor;
    // Конструктор
    public ArmoryCellEditor() {
        // Настройка прокручивающегося списка


    }
    // Метод получения компонента для прорисовки
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        // Изменение значения
        editor.setValue(value);
        return editor;
    }
    // Функция текущего значения в редакторе
    public Object getCellEditorValue() {
        return editor.getValue();
    }
}