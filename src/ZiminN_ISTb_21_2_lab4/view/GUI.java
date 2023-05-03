package ZiminN_ISTb_21_2_lab4.view;

import ZiminN_ISTb_21_2_lab4.Main;
import ZiminN_ISTb_21_2_lab4.data.*;


import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
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

        myOutputText.setRows(10);
        contents.add(new JScrollPane(myOutputText));
        contents.add(myInputText, BorderLayout.SOUTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        //jFrame.pack();
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);
    }

    private static JTable jTableWeaponList;
    private static SimpleModel myTableModel;
    private static Box contents;
    private static void MyTable()
    {
        ReadArmory();
        jTableWeaponList = new JTable();
        myTableModel = new SimpleModel(weaponList);
        jTableWeaponList.setModel(myTableModel);


        //jTableWeaponList.setDefaultEditor(String.class, new DefaultCellEditor());
        JComboBox<String> comboBox = new JComboBox<String>(new String[]{"Ближнего боя", "Дальнего боя"});
        comboBox.setEditable(true);
        DefaultCellEditor editor = new DefaultCellEditor(comboBox);

        jTableWeaponList.getColumnModel().getColumn(0).setCellEditor(editor);
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
        JButton buttonSaveArmory = new JButton("Сохранить арсенал");
        buttonAttack.addActionListener(e -> {
            try {
                BaseWeapon weapon = weaponList.getWeapon(jTableWeaponList.getSelectedRow());
                //BaseWeapon weapon = weaponList.getWeapon(Integer.parseInt(myInputText.getText()));
                System.out.println(jTableWeaponList.getSelectedRow());
                myOutputText.append(weapon.Attack(10,5).toString());
                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        buttonAddWeapon.addActionListener(e -> {
            try {
                JDialog jDialogAddWeapon = new JDialog(jFrame, "Создание оружия", true);


                JPanel dialogPanel = new JPanel();

                JComboBox<String> comboBox = new JComboBox<String>(new String[]{"Ближнего боя", "Дальнего боя"});
                JTextArea weaponName = new JTextArea("Название оружия");
                JTextArea diceAmount = new JTextArea("Количество костей");
                JTextArea diceType = new JTextArea("Размер кости");
                JTextArea weaponSharp = new JTextArea("Модификатор");
                JTextArea weaponRange = new JTextArea("Дистанция");
                JTextArea weaponAmmunition = new JTextArea("Боеприпасов(0 для ближнего)");
                JButton createWeapon = new JButton("Создать оружие");
                createWeapon.addActionListener(e1 -> {
                    if (comboBox.getSelectedIndex()==0) {
                        MeleeWeapon newWeapon = new MeleeWeapon(weaponName.getText(), diceAmount.getText()+"D"+diceType.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()));
                        weaponList.addWeapon(newWeapon);
                    }
                    if (comboBox.getSelectedIndex()==1) {
                        RangeWeapon newWeapon = new RangeWeapon(weaponName.getText(), diceAmount.getText()+"D"+diceType.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()), Integer.parseInt(weaponAmmunition.getText()));
                        weaponList.addWeapon(newWeapon);
                    }
                });

                weaponName.setColumns(10);
                diceAmount.setColumns(5);
                diceType.setColumns(5);
                weaponSharp.setColumns(5);
                weaponRange.setColumns(5);
                weaponAmmunition.setColumns(5);


                dialogPanel.add(comboBox);
                dialogPanel.add(weaponName);
                dialogPanel.add(diceAmount);
                dialogPanel.add(diceType);
                dialogPanel.add(weaponSharp);
                dialogPanel.add(weaponRange);
                dialogPanel.add(weaponAmmunition);
                dialogPanel.add(createWeapon);

                jDialogAddWeapon.setContentPane(dialogPanel);
                jDialogAddWeapon.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jDialogAddWeapon.setSize(800,110);
                jDialogAddWeapon.setVisible(true);

                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        buttonDeleteWeapon.addActionListener(e -> {
            try {
                weaponList.removeWeaponWithID(jTableWeaponList.getSelectedRow());
                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        buttonSaveArmory.addActionListener(e -> {
            try {
                CloseArmory();
            }
            catch (Exception io)
            {}
        });
        myButtonPanel.add(buttonAttack);
        myButtonPanel.add(buttonAddWeapon);
        myButtonPanel.add(buttonDeleteWeapon);
        myButtonPanel.add(buttonSaveArmory);

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
        }

    }
    private static void CloseArmory()
    {
        String armory = "armory.bin";
        int weaponAmount;
        String weaponType;
        int ID;
        try(DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(armory)))
        {
            BaseWeapon weaponOutput;
            dataOutputStream.writeUTF(Integer.toString(weaponList.weaponAmount()));
            for (int i = 0; i < weaponList.weaponAmount(); i++) {
                weaponOutput = weaponList.getWeapon(i);
                if(weaponOutput.getClass() == MeleeWeapon.class)
                {
                    dataOutputStream.writeUTF("MeleeWeapon");
                    dataOutputStream.writeUTF(weaponOutput.getWeaponName());
                    dataOutputStream.writeUTF(weaponOutput.getDamageDice());
                    dataOutputStream.writeUTF(Integer.toString(weaponOutput.getWeaponSharpening()));
                    dataOutputStream.writeUTF(Integer.toString(weaponOutput.getAttackRange()));
                }
                if(weaponOutput.getClass() == RangeWeapon.class)
                {
                    dataOutputStream.writeUTF("RangeWeapon");
                    dataOutputStream.writeUTF(weaponOutput.getWeaponName());
                    dataOutputStream.writeUTF(weaponOutput.getDamageDice());
                    dataOutputStream.writeUTF(Integer.toString(weaponOutput.getWeaponSharpening()));
                    dataOutputStream.writeUTF(Integer.toString(weaponOutput.getAttackRange()));
                    dataOutputStream.writeUTF(Integer.toString(((RangeWeapon) weaponOutput).getAmmunition()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Доступ к арсеналу отсутствует(не найден файл с данными)");;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Оружие успешно сохранено в арсенале");
    }

}
