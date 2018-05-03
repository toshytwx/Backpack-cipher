import encrypt.DESEncryptor;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class MyForm extends JDialog {
    private JPanel contentPane;
    private JButton openFileButton;
    private JButton buttonSaveAs;
    private JTextArea textArea;
    private JButton code;
    private JButton decode;
    private JButton aboutButton;
    private JButton exitButton;
    private JComboBox desMode;
    private JLabel keyTf;
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
                String codedText = controller.onEncryptDecrypt(textArea.getText(), Cipher.ENCRYPT_MODE);
                textArea.setText(codedText);
            }
        });

        decode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String decodedText = controller.onEncryptDecrypt(textArea.getText(), Cipher.DECRYPT_MODE);
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

        keyTf.setText(Arrays.toString(DESEncryptor.getKey().getEncoded()));
        tuneDesMode();
        controller.specializeEncryptor((String) desMode.getSelectedItem());
    }

    private void tuneDesMode() {
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        defaultComboBoxModel.addElement("/ECB/PKCS5Padding");
        defaultComboBoxModel.addElement("/CBC/PKCS5Padding");
        defaultComboBoxModel.addElement("/CBC/NoPadding");
        defaultComboBoxModel.addElement("/ECB/NoPadding");
        desMode.setModel(defaultComboBoxModel);
    }

    private void onAbout() {
        JOptionPane.showMessageDialog(this, "Developed by: Antonkin Dmytro TM-51", "About", JOptionPane.PLAIN_MESSAGE);
    }

}
