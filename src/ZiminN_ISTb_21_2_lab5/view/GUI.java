package ZiminN_ISTb_21_2_lab5.view;

import ZiminN_ISTb_21_2_lab5.data.*;

import javax.swing.*;
import java.awt.*;

public class GUI{

    private static int distance = 5;
    private static byte armor = 10;
    private static JFrame jFrame;
    private static JTextArea myOutputText;
    private static JTextField myInputText;
    public static void MainWindow()
    {
        jFrame = new JFrame("Главное меню");
        MyTable();
        ButtonPanel();
        myOutputText = new JTextArea("");
        myInputText = new JTextField("");

        myOutputText.setRows(10);
        tableContents.add(new JScrollPane(myOutputText));
        tableContents.add(myInputText, BorderLayout.SOUTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);
    }
    private static JTable jTableWeaponList;
    private static SimpleModel myTableModel;
    private static Box tableContents;
    private static void MyTable()
    {
        jTableWeaponList = new JTable();
        myTableModel = new SimpleModel();
        jTableWeaponList.setModel(myTableModel);
        tableContents = new Box(BoxLayout.Y_AXIS);
        tableContents.add(new JScrollPane(jTableWeaponList));
        jFrame.getContentPane().add(tableContents, BorderLayout.CENTER);
    }
    private static JPanel myButtonPanel;
    private static void ButtonPanel()
    {
        myButtonPanel = new JPanel();
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
                int id = jTableWeaponList.getSelectedRow();
                BaseWeapon weapon = myTableModel.getWeapon(id);
                System.out.println(id);
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
                int id = jTableWeaponList.getSelectedRow();
                BaseWeapon customWeapon = myTableModel.getWeapon(id);
                JDialog jDialogChangeWeapon = new JDialog(jFrame, "Изменение оружия", true);
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(0, 6));

                JComboBox<String> comboBox = new JComboBox<String>(new String[]{"Ближнего боя", "Дальнего боя"});
                JTextField weaponName = new JTextField(customWeapon.getWeaponName());
                JTextField dice = new JTextField(customWeapon.getDamageDice());
                JTextField weaponSharp = new JTextField(String.valueOf(customWeapon.getWeaponSharpening()));
                JTextField weaponRange = new JTextField(String.valueOf(customWeapon.getAttackRange()));
                JTextField weaponAmmunition = new JTextField("");

                if (customWeapon.getClass() == MeleeWeapon.class) { weaponAmmunition.setText("0");}
                else if (customWeapon.getClass() == RangeWeapon.class){ weaponAmmunition.setText(String.valueOf(((RangeWeapon) customWeapon).getAmmunition()));}

                JButton changeWeapon = new JButton("Изменить");
                changeWeapon.addActionListener(e1 -> {
                    boolean wrongInput = false;
                    weaponName.setForeground(Color.BLACK);
                    dice.setForeground(Color.BLACK);
                    weaponSharp.setForeground(Color.BLACK);
                    weaponRange.setForeground(Color.BLACK);
                    weaponAmmunition.setForeground(Color.BLACK);

                    if (weaponName.getText().isEmpty()) {weaponName.setText("Введите данные"); weaponName.setForeground(Color.RED); wrongInput = true;}
                    if (dice.getText().isEmpty()) {dice.setText("Введите данные"); dice.setForeground(Color.RED); wrongInput = true;}
                    if (weaponSharp.getText().isEmpty() || !isNumeric(weaponSharp.getText())) {weaponSharp.setText("Введите данные"); weaponSharp.setForeground(Color.RED); wrongInput = true;}
                    if (weaponRange.getText().isEmpty() || !isNumeric(weaponRange.getText())) {weaponRange.setText("Введите данные"); weaponRange.setForeground(Color.RED); wrongInput = true;}
                    if ((weaponAmmunition.getText().isEmpty() || !isNumeric(weaponAmmunition.getText())) && comboBox.getSelectedIndex()==1) {weaponAmmunition.setText("Введите данные"); weaponAmmunition.setForeground(Color.RED); wrongInput = true;}

                    if (wrongInput) return;

                    if (comboBox.getSelectedIndex()==0) {
                        MeleeWeapon meleeWeapon = new MeleeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()));
                        myTableModel.setWeapon(meleeWeapon, id);
                        jDialogChangeWeapon.dispose();
                    }
                    if (comboBox.getSelectedIndex()==1) {
                        RangeWeapon rangeWeapon = new RangeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()), Integer.parseInt(weaponAmmunition.getText()));
                        myTableModel.setWeapon(rangeWeapon, id);
                        jDialogChangeWeapon.dispose();
                    }
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
                myOutputText.append("Выберите оружие\n");
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
                JTextField weaponName = new JTextField("Название оружия");
                JTextField dice = new JTextField("Кости урона(*D*)");
                JTextField weaponSharp = new JTextField("Модификатор");
                JTextField weaponRange = new JTextField("Дистанция");
                JTextField weaponAmmunition = new JTextField("Боеприпасы");
                JButton createWeapon = new JButton("Создать оружие");
                createWeapon.addActionListener(e1 -> {
                    boolean wrongInput = false;
                    weaponName.setForeground(Color.BLACK);
                    dice.setForeground(Color.BLACK);
                    weaponSharp.setForeground(Color.BLACK);
                    weaponRange.setForeground(Color.BLACK);
                    weaponAmmunition.setForeground(Color.BLACK);

                    if (weaponName.getText().isEmpty()) {weaponName.setText("Введите данные"); weaponName.setForeground(Color.RED); wrongInput = true;}
                    if (dice.getText().isEmpty()) {dice.setText("Введите данные"); dice.setForeground(Color.RED); wrongInput = true;}
                    if (weaponSharp.getText().isEmpty() || !isNumeric(weaponSharp.getText())) {weaponSharp.setText("Введите данные"); weaponSharp.setForeground(Color.RED); wrongInput = true;}
                    if (weaponRange.getText().isEmpty() || !isNumeric(weaponRange.getText())) {weaponRange.setText("Введите данные"); weaponRange.setForeground(Color.RED); wrongInput = true;}
                    if ((weaponAmmunition.getText().isEmpty() || !isNumeric(weaponAmmunition.getText())) && comboBox.getSelectedIndex()==1) {weaponAmmunition.setText("Введите данные"); weaponAmmunition.setForeground(Color.RED); wrongInput = true;}

                    if (wrongInput) return;

                    if (comboBox.getSelectedIndex()==0) {
                        MeleeWeapon meleeWeapon = new MeleeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()));
                        myTableModel.addWeapon(meleeWeapon);
                        jDialogAddWeapon.dispose();
                    }
                    if (comboBox.getSelectedIndex()==1) {
                        RangeWeapon rangeWeapon = new RangeWeapon(weaponName.getText(), dice.getText(), Integer.parseInt(weaponSharp.getText()), Integer.parseInt(weaponRange.getText()), Integer.parseInt(weaponAmmunition.getText()));
                        myTableModel.addWeapon(rangeWeapon);
                        jDialogAddWeapon.dispose();
                    }
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
                myOutputText.append(myTableModel.removeWeapon(jTableWeaponList.getSelectedRow()));
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
            SimpleModel.CloseArmory();
            myOutputText.append("Арсенал сохранён\n");
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

}
