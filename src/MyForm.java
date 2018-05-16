import encrypt.BackPackEncryptor;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyForm extends JDialog {
    private JPanel contentPane;
    private JButton openFileButton;
    private JButton buttonSaveAs;
    private JTextArea textArea;
    private JButton code;
    private JButton decode;
    private JButton aboutButton;
    private JButton exitButton;
    private JTextField keyTf;
    private JFileChooser jFileChooser;

    public MyForm(Controller controller) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(openFileButton);

        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                String output = controller.onOpenFile(jFileChooser);
                textArea.setText(output);
            }
        });

        buttonSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                String input = textArea.getText();
                controller.onSaveFile(jFileChooser, input);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        code.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codedText = controller.onEncrypt(getClosedKey(), textArea.getText());
                textArea.setText(codedText);
            }
        });

        decode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String decodedText = controller.onDecrypt(getClosedKey(), textArea.getText());
                textArea.setText(decodedText);
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAbout();
            }
        });

    }

    private List<Integer> getClosedKey() {
        List<Integer> closedKey = Stream.of(keyTf.getText().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        if (closedKey.size() < 8 || closedKey.size() > 8) {
            JOptionPane.showMessageDialog(this, "Key must be 8 digits long", "Error", JOptionPane.ERROR_MESSAGE);
            keyTf.setText("");
        }
        for (int i = 0; i < closedKey.size(); i++) {
            List<Integer> closedElementKey = closedKey.stream().limit(i != 0 ? i - 1 : 0).collect(Collectors.toList());
                if (closedKey.get(i) < closedElementKey.stream().mapToInt(Integer::intValue).sum()) {
                    JOptionPane.showMessageDialog(this,"Each element must be bigger than the sum of previous elements!\nProblem at "+ closedKey.get(i), "Error", JOptionPane.ERROR_MESSAGE);
                    keyTf.setText("");
                    break;
                }
            }
        return closedKey;
    }

    private void onAbout() {
        JOptionPane.showMessageDialog(this, "Developed by: Antonkin Dmytro TM-51", "About", JOptionPane.PLAIN_MESSAGE);
    }

}
