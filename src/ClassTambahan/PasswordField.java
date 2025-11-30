package ClassTambahan;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class PasswordField extends JPasswordField{
    
    private Icon icon;
    private int iconX = 10;
    private int iconY = -1;

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomRight = 0;
    private int roundBottomLeft = 0;

    private String placeholder = "Password";
    private boolean showingPlaceholder = true;

    public PasswordField() {
        setOpaque(false);
        setBorder(new EmptyBorder(5, 35, 5, 10));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setForeground(Color.GRAY);
        setEchoChar((char) 0);
        setText(placeholder);

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                String text = String.valueOf(getPassword());
                if (text.equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                    setEchoChar('•');
                    showingPlaceholder = false;
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String text = String.valueOf(getPassword());
                if (text.isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    setEchoChar((char) 0);
                    showingPlaceholder = true;
                }
            }
        });
    }

    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int roundTopLeft) { this.roundTopLeft = roundTopLeft; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int roundTopRight) { this.roundTopRight = roundTopRight; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int roundBottomRight) { this.roundBottomRight = roundBottomRight; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int roundBottomLeft) { this.roundBottomLeft = roundBottomLeft; repaint(); }

    public void setIcon(Icon icon) {
        this.icon = icon;
        if (icon != null) {
            setBorder(new EmptyBorder(5, icon.getIconWidth() + 15, 5, 10));
        } else {
            setBorder(new EmptyBorder(5, 10, 5, 10));
        }
        repaint();
    }

    public Icon getIcon() { return icon; }

    public int getIconX() { return iconX; }
    public void setIconX(int iconX) { this.iconX = iconX; repaint(); }

    public int getIconY() { return iconY; }
    public void setIconY(int iconY) { this.iconY = iconY; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        setOpaque(false);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // background
        g2.setColor(getBackground());
        g2.fill(createRoundShape(width, height));

        // border
        g2.setColor(new Color(200, 200, 200));
        g2.draw(createRoundShape(width - 1, height - 1));

        g2.dispose();

        super.paintComponent(g);

        // icon
        if (icon != null) {
            Graphics2D g3 = (Graphics2D) g.create();
            int iconY = (height - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g3, 10, iconY);
            g3.dispose();
        }
    }

    private Shape createRoundShape(int width, int height) {
        int x = 0, y = 0;
        int tl = Math.min(roundTopLeft, Math.min(width, height));
        int tr = Math.min(roundTopRight, Math.min(width, height));
        int br = Math.min(roundBottomRight, Math.min(width, height));
        int bl = Math.min(roundBottomLeft, Math.min(width, height));

        Path2D path = new Path2D.Double();
        path.moveTo(x + tl, y);
        path.lineTo(x + width - tr, y);
        path.quadTo(x + width, y, x + width, y + tr);
        path.lineTo(x + width, y + height - br);
        path.quadTo(x + width, y + height, x + width - br, y + height);
        path.lineTo(x + bl, y + height);
        path.quadTo(x, y + height, x, y + height - bl);
        path.lineTo(x, y + tl);
        path.quadTo(x, y, x + tl, y);
        path.closePath();

        return path;
    }

    public static void main(String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame("Demo PasswordField");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(null);

        PasswordField passField = new PasswordField();
        passField.setBounds(50, 60, 300, 40);
        passField.setIcon(new ImageIcon(PasswordField.class.getResource("/Ikon_white/gembok_ogin.png")));
        passField.setRoundTopLeft(25);
        passField.setRoundTopRight(25);
        passField.setRoundBottomLeft(25);
        passField.setRoundBottomRight(25);

        frame.add(passField);
        frame.setVisible(true);
        
    } 
}
