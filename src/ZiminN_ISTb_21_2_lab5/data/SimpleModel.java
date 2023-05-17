package ZiminN_ISTb_21_2_lab5.data;

import javax.swing.table.AbstractTableModel;
import java.io.*;

public class SimpleModel extends AbstractTableModel
{
    private static WeaponList data = new WeaponList();
    public SimpleModel() {ReadArmory();}
    public SimpleModel(WeaponList weaponList)
    {
        this.data = weaponList;
    }

    // Количество строк
    @Override
    public int getRowCount() {
        return data.weaponAmount();
    }
    // Количество столбцов
    @Override
    public int getColumnCount() {
        return 6;
    }

    // Тип хранимых в столбцах данных
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: return BaseWeapon.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return Integer.class;
            case 4: return Integer.class;
            case 5: return Integer.class;
            default: return Object.class;
        }
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Тип оружия";
            case 1: return "Название";
            case 2: return "Урон";
            case 3: return "Модификатор";
            case 4: return "Дистанция";
            case 5: return "Аммуниция";
            default: return "";
        }
    }
    // Функция определения данных ячейки
    @Override
    public Object getValueAt(int row, int column)
    {
        // Данные для стобцов
        switch (column) {
            case 0: return data.getWeaponType(row);
            case 1: return data.getWeapon(row).getWeaponName();
            case 2: return data.getWeapon(row).getDamageDice();
            case 3: return data.getWeapon(row).getWeaponSharpening();
            case 4: return data.getWeapon(row).getAttackRange();
            case 5: {
                if (data.getWeapon(row).getClass() == MeleeWeapon.class) {return 0;}
                else {RangeWeapon weapon = (RangeWeapon) data.getWeapon(row); return weapon.getAmmunition();}
            }

        }
        return "Не определена";
    }

    public BaseWeapon getWeapon(int id) {return data.getWeapon(id);}
    public void setWeapon(BaseWeapon weapon, int id) {data.changeWeapon(weapon, id);}
    public void addWeapon(BaseWeapon weapon) {data.addWeapon(weapon);}
    public String removeWeapon(int id) {return data.removeWeaponWithID(id);}


    private static void ReadArmory()
    {
        String armory = "armory.bin";
        int weaponAmount;
        String weaponType;
        int ID;
        data.clearWeaponList();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(armory))) {
            weaponAmount = Integer.parseInt(dataInputStream.readUTF());
            for (int i = 0; i < weaponAmount; i++) {
                weaponType = dataInputStream.readUTF();
                if (weaponType.equals("MeleeWeapon"))
                {
                    MeleeWeapon meleeWeapon = new MeleeWeapon(dataInputStream.readUTF(), dataInputStream.readUTF(), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                    data.addWeapon(meleeWeapon);
                }
                if (weaponType.equals("RangeWeapon"))
                {
                    RangeWeapon rangeWeapon = new RangeWeapon(dataInputStream.readUTF(), dataInputStream.readUTF(), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                    data.addWeapon(rangeWeapon);
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
    public static void CloseArmory()
    {
        String armory = "armory.bin";
        int weaponAmount;
        String weaponType;
        int ID;
        try(DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(armory)))
        {
            BaseWeapon weaponOutput;
            dataOutputStream.writeUTF(Integer.toString(data.weaponAmount()));
            for (int i = 0; i < data.weaponAmount(); i++) {
                weaponOutput = data.getWeapon(i);
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