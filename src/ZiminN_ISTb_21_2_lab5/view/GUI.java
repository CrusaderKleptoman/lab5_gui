package ZiminN_ISTb_21_2_lab5.view;

import ZiminN_ISTb_21_2_lab5.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class GUI{

    private static int distance = 5;
    private static byte armor = 10;
    private static JFrame jFrame;
    private static JTextArea myOutputText;
    private static JTextField myInputText;
    private static WeaponList weaponList = new WeaponList();
    public static void MainWindow()
    {
        jFrame = new JFrame("Главное меню");
        MyTable();
        ButtonPanel();
        myOutputText = new JTextArea("");
        myInputText = new JTextField("");

        myOutputText.setRows(10);
        contents.add(new JScrollPane(myOutputText));
        contents.add(myInputText, BorderLayout.SOUTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
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
        contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(jTableWeaponList));
        jFrame.getContentPane().add(contents, BorderLayout.CENTER);
    }
    private static JPanel myButtonPanel;
    private static void ButtonPanel()
    {
        myButtonPanel = new JPanel();
        myButtonPanel.setLayout(new BoxLayout(myButtonPanel, BoxLayout.Y_AXIS));
        myButtonPanel.setLayout(new GridLayout(0,1));

        JButton buttonAttack = new JButton("Атака");
        JButton buttonSetDistance = new JButton("Задать дистанцию");
        JButton buttonSetArmor = new JButton("Задать защиту цели");
        JButton buttonChangeWeapon = new JButton("Изменить оружие");
        JButton buttonAddWeapon = new JButton("Добавить оружие");
        JButton buttonDeleteWeapon = new JButton("Удалить оружие");
        JButton buttonClearText = new JButton("Очистить консоль");
        JButton buttonSaveArmory = new JButton("Сохранить арсенал");

        buttonAttack.addActionListener(e -> {
            try {
                if (jTableWeaponList.getSelectedRow() == -1) { myOutputText.append("Выберите оружие\n");}
                BaseWeapon weapon = weaponList.getWeapon(jTableWeaponList.getSelectedRow());
                System.out.println(jTableWeaponList.getSelectedRow());
                myOutputText.append(weapon.Attack(armor,distance).toString());
                myOutputText.append("\n");
                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        buttonSetDistance.addActionListener(e -> {
            if (myInputText.getText() != "" && isNumeric(myInputText.getText())) {distance = Integer.parseInt(myInputText.getText()); myOutputText.append("Дистанция задана\n"); myInputText.setText("");}
            else {myInputText.setText("Введите дистанцию до цели");}
        });
        buttonSetArmor.addActionListener(e -> {
            if (myInputText.getText() != "" && isNumeric(myInputText.getText())) {armor = Byte.parseByte(myInputText.getText()); myOutputText.append("Броня задана\n"); myInputText.setText("");}
            else {myInputText.setText("Введите броню цели");}
        });
        buttonChangeWeapon.addActionListener(e->{
            try {
                BaseWeapon customWeapon = weaponList.getWeapon(jTableWeaponList.getSelectedRow());
                JDialog jDialogChangeWeapon = new JDialog(jFrame, "Изменение оружия", true);
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(0, 6));

                JComboBox<String> comboBox = new JComboBox<String>(new String[]{"Ближнего боя", "Дальнего боя"});
                JTextArea weaponName = new JTextArea(customWeapon.getWeaponName());
                JTextArea dice = new JTextArea(customWeapon.getDamageDice());
                JTextArea weaponSharp = new JTextArea(String.valueOf(customWeapon.getWeaponSharpening()));
                JTextArea weaponRange = new JTextArea(String.valueOf(customWeapon.getAttackRange()));
                JTextArea weaponAmmunition = new JTextArea("");
                if (customWeapon.getClass() == MeleeWeapon.class) { weaponAmmunition.setText("0");}
                else if (customWeapon.getClass() == RangeWeapon.class){ weaponAmmunition.setText(String.valueOf(((RangeWeapon) customWeapon).getAmmunition()));}
                JButton changeWeapon = new JButton("Изменить");
                changeWeapon.addActionListener(e1 -> {
                    try {
                        if (comboBox.getSelectedIndex()==0) {
                            MeleeWeapon meleeWeapon = new MeleeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()));
                            weaponList.changeWeapon(meleeWeapon, jTableWeaponList.getSelectedRow());
                            jDialogChangeWeapon.dispose();
                        }
                        if (comboBox.getSelectedIndex()==1) {
                            RangeWeapon rangeWeapon = new RangeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()), Integer.parseInt(weaponAmmunition.getText()));
                            weaponList.changeWeapon(rangeWeapon, jTableWeaponList.getSelectedRow());
                            jDialogChangeWeapon.dispose();
                        }
                    }
                    catch (NumberFormatException io)
                    {
                        if (weaponSharp.getText().isEmpty() || !isNumeric(weaponSharp.getText())) {weaponSharp.setText("Напишите модификатор");}
                        if (weaponRange.getText().isEmpty() || !isNumeric(weaponRange.getText())) {weaponRange.setText("Напишите дистанцию");}
                        if ((weaponAmmunition.getText().isEmpty() || !isNumeric(weaponAmmunition.getText())) && comboBox.getSelectedIndex()==1) {weaponAmmunition.setText("Напишите аммуницию");}
                    }
                    catch (Exception io)
                    {}
                });

                dialogPanel.add(new JLabel("Тип оружия"));
                dialogPanel.add(new JLabel("Название оружия"));
                dialogPanel.add(new JLabel("Кость урона"));
                dialogPanel.add(new JLabel("Модификатор"));
                dialogPanel.add(new JLabel("Дистанция атаки"));
                dialogPanel.add(new JLabel("Аммуниция"));

                dialogPanel.add(comboBox);
                dialogPanel.add(weaponName);
                dialogPanel.add(dice);
                dialogPanel.add(weaponSharp);
                dialogPanel.add(weaponRange);
                dialogPanel.add(weaponAmmunition);
                dialogPanel.add(changeWeapon);

                jDialogChangeWeapon.setContentPane(dialogPanel);
                jDialogChangeWeapon.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jDialogChangeWeapon.setSize(800,110);
                jDialogChangeWeapon.setVisible(true);

                myTableModel.fireTableDataChanged();
            }
            catch (NullPointerException io)
            {
                myOutputText.append("Выберите оружие");
            }
            catch (Exception io)
            {

            }
        });
        buttonAddWeapon.addActionListener(e -> {
            try {
                JDialog jDialogAddWeapon = new JDialog(jFrame, "Создание оружия", true);
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(0, 6));

                JComboBox<String> comboBox = new JComboBox<String>(new String[]{"Ближнего боя", "Дальнего боя"});
                JTextArea weaponName = new JTextArea("Название оружия");
                JTextArea dice = new JTextArea("Кости урона(*D*)");
                JTextArea weaponSharp = new JTextArea("Модификатор");
                JTextArea weaponRange = new JTextArea("Дистанция");
                JTextArea weaponAmmunition = new JTextArea("Боеприпасы");
                JButton createWeapon = new JButton("Создать оружие");
                createWeapon.addActionListener(e1 -> {
                    try {
                        if (comboBox.getSelectedIndex()==0) {
                            MeleeWeapon meleeWeapon = new MeleeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()));
                            weaponList.addWeapon(meleeWeapon);
                            jDialogAddWeapon.dispose();
                        }
                        if (comboBox.getSelectedIndex()==1) {
                            RangeWeapon rangeWeapon = new RangeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()), Integer.parseInt(weaponAmmunition.getText()));
                            weaponList.addWeapon(rangeWeapon);
                            jDialogAddWeapon.dispose();
                        }
                    }
                    catch (NumberFormatException io)
                    {
                        if (weaponSharp.getText().isEmpty() || !isNumeric(weaponSharp.getText())) {weaponSharp.setText("Введите данные"); weaponSharp.setForeground(Color.RED);}
                        if (weaponRange.getText().isEmpty() || !isNumeric(weaponRange.getText())) {weaponRange.setText("Введите данные"); weaponRange.setForeground(Color.RED);}
                        if ((weaponAmmunition.getText().isEmpty() || !isNumeric(weaponAmmunition.getText())) && comboBox.getSelectedIndex()==1) {weaponAmmunition.setText("Введите данные"); weaponAmmunition.setForeground(Color.RED);}
                    }
                    catch (Exception io)
                    {}
                });

                dialogPanel.add(new JLabel("Тип оружия"));
                dialogPanel.add(new JLabel("Название оружия"));
                dialogPanel.add(new JLabel("Кость урона"));
                dialogPanel.add(new JLabel("Модификатор"));
                dialogPanel.add(new JLabel("Дистанция атаки"));
                dialogPanel.add(new JLabel("Аммуниция"));

                dialogPanel.add(comboBox);
                dialogPanel.add(weaponName);
                dialogPanel.add(dice);
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
                myOutputText.append(weaponList.removeWeaponWithID(jTableWeaponList.getSelectedRow()));
                myOutputText.append("\n");
                myTableModel.fireTableDataChanged();
            }
            catch (Exception io)
            {}
        });
        buttonClearText.addActionListener(e -> {
            myOutputText.setText("");
        });
        buttonSaveArmory.addActionListener(e -> {
            CloseArmory();
        });

        myButtonPanel.add(buttonAttack);
        myButtonPanel.add(buttonSetDistance);
        myButtonPanel.add(buttonSetArmor);
        myButtonPanel.add(buttonChangeWeapon);
        myButtonPanel.add(buttonAddWeapon);
        myButtonPanel.add(buttonDeleteWeapon);
        myButtonPanel.add(buttonClearText);
        myButtonPanel.add(buttonSaveArmory);

        jFrame.add(myButtonPanel, BorderLayout.WEST);
    }
    private static boolean isNumeric(String text)
    {
        boolean numeric = false;
        char symbol;
        for (int i = 0; i < text.length(); i++) {
            symbol = text.charAt(i);
            for (int j = 48; j < 58; j++) {
                if(symbol == (char)j) {numeric = true;}
            }
            if (!numeric) {return false;}
        }
        return numeric;
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
