package ZiminN_ISTb_21_2_lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    public void MainWindow()
    {
        setTitle("Главное меню");
        setSize(640, 640);
        JPanel panel = new JPanel();
        JButton button = new JButton("ok");

        JTextArea textArea = new JTextArea("asd");
        button.addActionListener(e -> System.out.println(textArea.getText()));
        add(panel, BorderLayout.CENTER);
        panel.add(button);
        panel.add(textArea);
        Label textLog = new Label("");
        add(textLog, BorderLayout.EAST);

        //add(button);
        button.setSize(100, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }

}
