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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TextField extends JTextField {

    private Icon icon;
    private int iconX = 10;
    private int iconY = -1;

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomRight = 0;
    private int roundBottomLeft = 0;

    private String placeholder = "Username";
    private Color placeholderColor = new Color(150, 150, 150);
    private Color textColor = Color.BLACK;

    public TextField() {
        setOpaque(false);
        setBorder(new EmptyBorder(5, 35, 5, 10));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBackground(Color.WHITE);

        setText(placeholder);
        setForeground(placeholderColor);

        addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(textColor);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                }
            }
        });
    }

    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int v) { roundTopLeft = v; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int v) { roundTopRight = v; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int v) { roundBottomRight = v; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int v) { roundBottomLeft = v; repaint(); }

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
    public void setIconX(int v) { iconX = v; repaint(); }

    public int getIconY() { return iconY; }
    public void setIconY(int v) { iconY = v; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        setOpaque(false);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(getBackground());
        g2.fill(createRoundShape(w, h));

        g2.setColor(new Color(200, 200, 200));
        g2.draw(createRoundShape(w - 1, h - 1));

        g2.dispose();

        super.paintComponent(g);

        if (icon != null) {
            Graphics2D g3 = (Graphics2D) g.create();
            int iy = (h - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g3, iconX, iy);
            g3.dispose();
        }
    }

    private Shape createRoundShape(int w, int h) {
        int tl = Math.min(roundTopLeft, Math.min(w, h));
        int tr = Math.min(roundTopRight, Math.min(w, h));
        int br = Math.min(roundBottomRight, Math.min(w, h));
        int bl = Math.min(roundBottomLeft, Math.min(w, h));

        Path2D p = new Path2D.Double();
        p.moveTo(tl, 0);
        p.lineTo(w - tr, 0);
        p.quadTo(w, 0, w, tr);
        p.lineTo(w, h - br);
        p.quadTo(w, h, w - br, h);
        p.lineTo(bl, h);
        p.quadTo(0, h, 0, h - bl);
        p.lineTo(0, tl);
        p.quadTo(0, 0, tl, 0);
        p.closePath();

        return p;
    }
}
