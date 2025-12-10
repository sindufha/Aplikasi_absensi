package ClassAbsensi;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ComboBoxRenderer extends JComboBox<String> implements TableCellRenderer {
    
    public ComboBoxRenderer(String[] items) {
        super(items);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        setSelectedItem(value);
        
        // Styling berdasarkan status
        if (value != null) {
            String status = value.toString();
            switch (status) {
                case "Hadir":
                    setBackground(new Color(220, 252, 231)); // #DCFCE7
                    setForeground(new Color(22, 163, 74));   // #16A34A
                    break;
                case "Sakit":
                    setBackground(new Color(254, 243, 199)); // #FEF3C7
                    setForeground(new Color(202, 138, 4));   // #CA8A04
                    break;
                case "Izin":
                    setBackground(new Color(219, 234, 254)); // #DBEAFE
                    setForeground(new Color(37, 99, 235));   // #2563EB
                    break;
                case "Alfa":
                    setBackground(new Color(254, 226, 226)); // #FEE2E2
                    setForeground(new Color(220, 38, 38));   // #DC2626
                    break;
                default:
                    setBackground(Color.WHITE);
                    setForeground(Color.GRAY);
            }
        }
        
        return this;
    }
}