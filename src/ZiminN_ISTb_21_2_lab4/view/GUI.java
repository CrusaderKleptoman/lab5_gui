package ZiminN_ISTb_21_2_lab4.view;

import ZiminN_ISTb_21_2_lab4.data.*;


import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EventObject;

public class GUI{

    private static JFrame jFrame;
    private static JTextArea myOutputText;
    private static JTextField myInputText;
    private static String inputText;
    private static WeaponList weaponList = new WeaponList();
    public static void MainWindow()
    {
        jFrame = new JFrame("Главное меню");
        myOutputText = new JTextArea("asd");
        MyTable();
        ButtonPanel();
        myOutputText = new JTextArea("");
        myInputText = new JTextField("");
        myInputText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {inputText = myInputText.getText();}
            }
        });

        contents.add(myOutputText, BorderLayout.SOUTH);
        contents.add(myInputText, BorderLayout.SOUTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private static JTable jTableWeaponList;
    private static SimpleModel myTableModel;
    private static Box contents;
    private static void MyTable()
    {
        ReadArmory();
        jTableWeaponList = new JTable();
        jTableWeaponList.setDefaultEditor(String.class, new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return false;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }
        });
        myTableModel = new SimpleModel(weaponList);
        jTableWeaponList.setModel(myTableModel);
        jTableWeaponList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(jTableWeaponList));
        jFrame.getContentPane().add(contents, BorderLayout.CENTER);
    }

    private static JPanel myButtonPanel;
    private static void ButtonPanel()
    {
        myButtonPanel = new JPanel();
        myButtonPanel.setLayout(new BoxLayout(myButtonPanel, BoxLayout.Y_AXIS));
        JButton buttonAttack = new JButton("Атака");
        JButton buttonAddWeapon = new JButton("Добавить оружие");
        JButton buttonDeleteWeapon = new JButton("Удалить оружие");
        buttonAttack.addActionListener(e -> {
            try {
                BaseWeapon weapon = weaponList.getWeapon(jTableWeaponList.getSelectedRow());
                //BaseWeapon weapon = weaponList.getWeapon(Integer.parseInt(myInputText.getText()));
                System.out.println(jTableWeaponList.getSelectedRow());
                weapon.Attack(10,5, myOutputText);
            }
            catch (Exception io)
            {}
        });
        buttonAddWeapon.addActionListener(e -> {
            try {
                MeleeWeapon newWeapon = new MeleeWeapon();
                weaponList.addWeapon(newWeapon);
                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        buttonDeleteWeapon.addActionListener(e -> {
            try {
                MeleeWeapon newWeapon = new MeleeWeapon();
                weaponList.removeWeaponWithID(jTableWeaponList.getSelectedRow());
                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        myButtonPanel.add(buttonAttack);
        myButtonPanel.add(buttonAddWeapon);
        myButtonPanel.add(buttonDeleteWeapon);

        jFrame.add(myButtonPanel, BorderLayout.WEST);
    }

    private static void ReadArmory()
    {
        String armory = "armory.bin";
        int weaponAmount;
        String weaponType;
        int ID;
        weaponList.clearWeaponList();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(armory))) {
            weaponAmount = Integer.parseInt(dataInputStream.readUTF());
            for (int i = 0; i < weaponAmount; i++) {
                weaponType = dataInputStream.readUTF();
                if (weaponType.equals("MeleeWeapon"))
                {
                    MeleeWeapon meleeWeapon = new MeleeWeapon(dataInputStream.readUTF(), dataInputStream.readUTF(), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                    weaponList.addWeapon(meleeWeapon);
                }
                if (weaponType.equals("RangeWeapon"))
                {
                    RangeWeapon rangeWeapon = new RangeWeapon(dataInputStream.readUTF(), dataInputStream.readUTF(), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                    weaponList.addWeapon(rangeWeapon);
                }
            }
        }
        catch (NumberFormatException exception)
        {
            System.out.println("Неверный формат данных в файле или внезапный конец файла, возвращение в меню \n");
        }
        catch (FileNotFoundException e) {
            System.out.println("Доступ к арсеналу отсутствует(не найден файл с данными)");;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
