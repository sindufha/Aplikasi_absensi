
package ClassAbsensi;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ComboBoxEditor extends DefaultCellEditor {
    
    public ComboBoxEditor(String[] items) {
        super(new JComboBox<>(items));
    }
    
    // Opsional: Custom styling
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                   boolean isSelected, int row, int column) {
        JComboBox<String> comboBox = (JComboBox<String>) getComponent();
        comboBox.setSelectedItem(value);
        return comboBox;
    }
}