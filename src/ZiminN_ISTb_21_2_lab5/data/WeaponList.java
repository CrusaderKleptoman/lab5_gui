package ZiminN_ISTb_21_2_lab5.data;

import java.util.ArrayList;

public class WeaponList {
    private ArrayList<BaseWeapon> weaponList = new ArrayList<>();

    public WeaponList()
    { this.weaponList = new ArrayList<>();}
    public void addWeapon(BaseWeapon weapon) {
        if(weapon.getClass() == MeleeWeapon.class)
        {
            weaponList.add((MeleeWeapon)weapon);
        }
        if(weapon.getClass() == RangeWeapon.class)
        {
            weaponList.add((RangeWeapon)weapon);
        }
    }
    public void changeWeapon(BaseWeapon weapon, int id)
    {
        if(weapon.getClass() == MeleeWeapon.class)
        {
            weaponList.set(id,((MeleeWeapon)weapon));
        }
        if(weapon.getClass() == RangeWeapon.class)
        {
            weaponList.set(id,((RangeWeapon)weapon));
        }
    }

    public String removeWeaponWithID(int weaponID) {
        try {
            weaponList.remove(weaponList.get(weaponID));
            return "Оружие убрано из арсенала";
        }
        catch (IndexOutOfBoundsException exception)
        {
            return "Выберите оружие";
        }
    }
    public void writeWeapon(int weaponID)
    {

        try {
            BaseWeapon weapon = weaponList.get(weaponID);
            if(weapon.getClass() == MeleeWeapon.class)
            {
                System.out.printf("Оружие ближнего боя, название оружия %s, урон оружия %s, модификатор попадания и урона %d, дистанция удара %d \n", weapon.getWeaponName(), weapon.getDamageDice().toString(), weapon.getWeaponSharpening(), weapon.getAttackRange());
            }
            if (weapon.getClass() == RangeWeapon.class)
            {
                System.out.printf("Оружие дальнего боя, название оружия %s, урон оружия %s, модификатор попадания и урона %d, дистанция выстрела %d, запас аммуниции %d \n", weapon.getWeaponName(), weapon.getDamageDice().toString(), weapon.getWeaponSharpening(), weapon.getAttackRange(), ((RangeWeapon) weapon).getAmmunition());
            }
        }
        catch (IndexOutOfBoundsException exception)
        {
            System.out.println("Оружия под данным номером нет");
        }
    }

    public BaseWeapon getWeapon(int weaponID)
    {
        try {
        return  weaponList.get(weaponID);
        }
        catch (IndexOutOfBoundsException exception)
        {
        System.out.println("Оружия под данным номером нет");
        }
        return null;
    }
    public int weaponAmount() { return weaponList.size();}
    public void writeWeaponList()
    {
        int count = 0;
        for (BaseWeapon weapon: weaponList)
        {
            count++;
            if (weapon.getClass() == MeleeWeapon.class) {
                System.out.printf("%d) Оружие ближнего боя, название оружия %s, урон оружия %s, модификатор попадания и урона %d, дистанция удара %d \n", count, weapon.getWeaponName(), weapon.getDamageDice().toString(), weapon.getWeaponSharpening(), weapon.getAttackRange());
            }
            if (weapon.getClass() == RangeWeapon.class)
            {
                System.out.printf("%d) Оружие дальнего боя, название оружия %s, урон оружия %s, модификатор попадания и урона %d, дистанция выстрела %d, запас аммуниции %d \n", count, weapon.getWeaponName(), weapon.getDamageDice().toString(), weapon.getWeaponSharpening(), weapon.getAttackRange(), ((RangeWeapon) weapon).getAmmunition());
            }
        }
    }
    public String getWeaponType(int weaponID)
    {
        try {
            BaseWeapon weapon = weaponList.get(weaponID);
            if(weapon.getClass() == MeleeWeapon.class)
            {
                return "Ближнего боя";
            }
            if (weapon.getClass() == RangeWeapon.class)
            {
                return "Дальнего боя";
            }
        }
        catch (IndexOutOfBoundsException exception)
        {
            System.out.println("Оружия под данным номером нет");
        }
        return "-";
    }
    public void clearWeaponList()
    {
        this.weaponList.clear();
    }
}