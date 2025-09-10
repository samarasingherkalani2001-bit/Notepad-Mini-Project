import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class notepad extends JFrame {

    private final JTextArea area = new JTextArea();
    private File currentFile = null;

    public notepad() {
        super("Notepad ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        area.setFont(new Font("Consolas", Font.PLAIN, 16));
        add(new JScrollPane(area), BorderLayout.CENTER);


        setJMenuBar(buildMenuBar());
    }

    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();


        JMenu mFile = new JMenu("File");
        mFile.setMnemonic('F');

        JMenuItem miNew = item("New", KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> newFile());
        JMenuItem miOpen = item("Open…", KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> openFile());
        JMenuItem miSave = item("Save", KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> saveFile(false));
        JMenuItem miSaveAs = item("Save As…", null, e -> saveFile(true));
        JMenuItem miExit = item("Exit", KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> dispose());

        mFile.add(miNew);
        mFile.add(miOpen);
        mFile.addSeparator();
        mFile.add(miSave);
        mFile.add(miSaveAs);
        mFile.addSeparator();
        mFile.add(miExit);

        JMenu mEdit = new JMenu("Edit");
        mEdit.setMnemonic('E');

        JMenuItem miCut   = item("Cut",   KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> area.cut());
        JMenuItem miCopy  = item("Copy",  KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> area.copy());
        JMenuItem miPaste = item("Paste", KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> area.paste());
        JMenuItem miSelAll= item("Select All", KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), e -> area.selectAll());

        mEdit.add(miCut);
        mEdit.add(miCopy);
        mEdit.add(miPaste);
        mEdit.addSeparator();
        mEdit.add(miSelAll);


        JMenu mFormat = new JMenu("Format");
        JMenuItem miFont = item("Font…", null, e -> showFontDialog());
        JMenuItem miColor = item("Text Color…", null, e -> {
            Color chosen = JColorChooser.showDialog(this, "Choose Text Color", area.getForeground());
            if (chosen != null) area.setForeground(chosen);
        });
        mFormat.add(miFont);
        mFormat.add(miColor);


        JMenu mHelp = new JMenu("Help");
        JMenuItem miAbout = item("About Notepad Mini", null, e -> {
            String Name = "Kalani sewmini";
            String ID   = "200s19253";
            JOptionPane.showMessageDialog(this,
                    "Notepad Mini\n\nCreated by: " + "Kalani sewmini" + "\nID: " + "2022s19253",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });
        mHelp.add(miAbout);

        bar.add(mFile);
        bar.add(mEdit);
        bar.add(mFormat);
        bar.add(mHelp);
        return bar;
    }

    private JMenuItem item(String text, KeyStroke ks, ActionListener a) {
        JMenuItem it = new JMenuItem(text);
        if (ks != null) it.setAccelerator(ks);
        it.addActionListener(a);
        return it;
    }


    private void newFile() {
        if (confirmLoseChanges()) {
            area.setText("");
            currentFile = null;
            setTitle("Notepad Mini - Untitled");
        }
    }

    private void openFile() {
        if (!confirmLoseChanges()) return;

        JFileChooser ch = chooser("Text Files", "txt", "md", "log", "java", "csv");
        if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = ch.getSelectedFile();
            try {
                String content = Files.readString(f.toPath(), StandardCharsets.UTF_8);
                area.setText(content);
                currentFile = f;
                setTitle("Notepad Mini - " + f.getName());
            } catch (IOException ex) {
                error("Could not open file:\n" + ex.getMessage());
            }
        }
    }

    private void saveFile(boolean forceChoose) {
        try {
            if (forceChoose || currentFile == null) {
                JFileChooser ch = chooser("Text Files", "txt");
                if (currentFile != null) ch.setSelectedFile(currentFile);
                if (ch.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
                File sel = ch.getSelectedFile();

                if (!sel.getName().contains(".")) {
                    sel = new File(sel.getParentFile(), sel.getName() + ".txt");
                }
                currentFile = sel;
            }
            Files.writeString(currentFile.toPath(), area.getText(), StandardCharsets.UTF_8);
            setTitle("Notepad Mini - " + currentFile.getName());
        } catch (IOException ex) {
            error("Could not save file:\n" + ex.getMessage());
        }
    }

    private boolean confirmLoseChanges() {


        if (area.getText().isEmpty()) return true;
        int r = JOptionPane.showConfirmDialog(this, "Discard current text?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return r == JOptionPane.YES_OPTION;
    }

    private JFileChooser chooser(String title, String... exts) {
        JFileChooser ch = new JFileChooser();
        ch.setDialogTitle(title);
        ch.setFileFilter(new FileNameExtensionFilter(title, exts));
        return ch;
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showFontDialog() {
        JDialog dlg = new JDialog(this, "Choose Font", true);
        dlg.setLayout(new BorderLayout(10,10));


        String[] families = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        JComboBox<String> cbFamily = new JComboBox<>(families);
        cbFamily.setSelectedItem(area.getFont().getFamily());


        String[] styles = { "Plain", "Bold", "Italic", "Bold Italic" };
        JComboBox<String> cbStyle = new JComboBox<>(styles);
        cbStyle.setSelectedIndex(styleToIndex(area.getFont().getStyle()));

        Integer[] sizes = { 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 36, 48 };
        JComboBox<Integer> cbSize = new JComboBox<>(sizes);
        cbSize.setEditable(true);
        cbSize.setSelectedItem(area.getFont().getSize());


        JTextField preview = new JTextField("The quick brown fox jumps over the lazy dog.");
        preview.setEditable(false);
        preview.setHorizontalAlignment(SwingConstants.CENTER);
        preview.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        ItemListener updater = e -> {
            Font f = new Font(
                    (String) cbFamily.getSelectedItem(),
                    indexToStyle(cbStyle.getSelectedIndex()),
                    parseSize(cbSize.getSelectedItem(), area.getFont().getSize()));
            preview.setFont(f);
        };
        cbFamily.addItemListener(updater);
        cbStyle.addItemListener(updater);
        cbSize.addItemListener(updater);
        updater.itemStateChanged(null);

        JPanel grid = new JPanel(new GridLayout(3,2,8,8));
        grid.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        grid.add(new JLabel("Family:"));
        grid.add(cbFamily);
        grid.add(new JLabel("Style:"));
        grid.add(cbStyle);
        grid.add(new JLabel("Size:"));
        grid.add(cbSize);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        south.add(ok);
        south.add(cancel);

        ok.addActionListener(e -> {
            Font f = new Font(
                    (String) cbFamily.getSelectedItem(),
                    indexToStyle(cbStyle.getSelectedIndex()),
                    parseSize(cbSize.getSelectedItem(), area.getFont().getSize()));
            area.setFont(f);
            dlg.dispose();
        });
        cancel.addActionListener(e -> dlg.dispose());

        dlg.add(grid, BorderLayout.NORTH);
        dlg.add(preview, BorderLayout.CENTER);
        dlg.add(south, BorderLayout.SOUTH);
        dlg.pack();
        dlg.setSize(Math.max(480, dlg.getWidth()), 260);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    private int parseSize(Object val, int fallback) {
        try {
            return Integer.parseInt(String.valueOf(val).trim());
        } catch (Exception e) {
            return fallback;
        }
    }
    private int styleToIndex(int style) {
        switch (style) {
            case Font.BOLD: return 1;
            case Font.ITALIC: return 2;
            case Font.BOLD | Font.ITALIC: return 3;
            default: return 0;
        }
    }
    private int indexToStyle(int idx) {
        switch (idx) {
            case 1: return Font.BOLD;
            case 2: return Font.ITALIC;
            case 3: return Font.BOLD | Font.ITALIC;
            default: return Font.PLAIN;
        }
    }


    public static void main(String[] args) {

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new notepad().setVisible(true));
    }
}
