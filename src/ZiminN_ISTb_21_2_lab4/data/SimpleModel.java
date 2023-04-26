package ZiminN_ISTb_21_2_lab4.data;

import javax.swing.table.AbstractTableModel;

public class SimpleModel extends AbstractTableModel
{
    private WeaponList data;

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
}