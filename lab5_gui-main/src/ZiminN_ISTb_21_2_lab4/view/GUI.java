package ZiminN_ISTb_21_2_lab4.view;

import ZiminN_ISTb_21_2_lab4.data.Dice;
import ZiminN_ISTb_21_2_lab4.data.MeleeWeapon;
import ZiminN_ISTb_21_2_lab4.data.RangeWeapon;
import ZiminN_ISTb_21_2_lab4.data.WeaponList;
import ZiminN_ISTb_21_2_lab4.data.SimpleModel;


import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI{

    private static JFrame jFrame;
    private static JTextArea myOutputText;
    private static WeaponList weaponList = new WeaponList();
    public static void MainWindow()
    {
        jFrame = new JFrame("Главное меню");
        myOutputText = new JTextArea("asd");
        MyTable();
        ButtonPanel();
        myOutputText = new JTextArea("");

        contents.add(myOutputText, BorderLayout.SOUTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private static JTable jTable;
    private static SimpleModel myTableModel;
    private static Box contents;
    private static void MyTable()
    {
        ReadArmory();
        jTable = new JTable();

        myTableModel = new SimpleModel(weaponList);
        jTable.setModel(myTableModel);
        contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(jTable));
        jFrame.getContentPane().add(contents, BorderLayout.CENTER);
    }

    private static JPanel myButtonPanel;
    private static void ButtonPanel()
    {
        myButtonPanel = new JPanel();
        JButton buttonAttack = new JButton("Атака");
        myButtonPanel.add(buttonAttack);
        jFrame.add(myButtonPanel, BorderLayout.WEST);
    }

    private static void ReadArmory()
    {
        String armory = "armory.bin";
        int weaponAmount;
        String weaponType;
        Dice[] diceList = Dice.values();
        int ID;
        weaponList.clearWeaponList();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(armory))) {
            weaponAmount = Integer.parseInt(dataInputStream.readUTF());
            for (int i = 0; i < weaponAmount; i++) {
                weaponType = dataInputStream.readUTF();
                if (weaponType.equals("MeleeWeapon"))
                {
                    MeleeWeapon meleeWeapon = new MeleeWeapon(dataInputStream.readUTF(), diceList[Integer.parseInt(dataInputStream.readUTF())], Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                    weaponList.addWeapon(meleeWeapon);
                }
                if (weaponType.equals("RangeWeapon"))
                {
                    RangeWeapon rangeWeapon = new RangeWeapon(dataInputStream.readUTF(), diceList[Integer.parseInt(dataInputStream.readUTF())], Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
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
