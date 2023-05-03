package ZiminN_ISTb_21_2_lab4.data;

import java.util.HashMap;

public class RangeWeapon extends BaseWeapon {
    private int ammunition;
    public RangeWeapon()
    {
        super();
        this.setWeaponName("Камень");
        this.setDamageDice("1D4");
        this.setWeaponSharpening(0);
        this.setAttackRange(20);
        this.ammunition = 20;
    }

    public RangeWeapon(String weaponName, String damageDice, int accuracy, int attackRange)
    {
        super(weaponName, damageDice, accuracy, attackRange);
        this.ammunition = 20;
    }

    public RangeWeapon(String weaponName, String damageDice, int accuracy, int attackRange, int ammunition)
    {
        super(weaponName, damageDice, accuracy, attackRange);
        this.ammunition = ammunition;
    }

    public RangeWeapon(RangeWeapon rangeWeapon)
    {
        super(rangeWeapon);
        this.ammunition = rangeWeapon.ammunition;
    }

    public int getAmmunition() {return ammunition;}
    public void setAmmunition(int ammunition) {this.ammunition = ammunition;}

    @Override
    public HashMap<String, Integer> Attack(int enemyArmour, int distance) {
        HashMap<String, Integer> message = new HashMap<>();
        String text = new String();
        Boolean meleeShot = false;
        if (ammunition == 0)
        {
            text += "Отсутствуют боеприпасы для выстрела, завершение выстрела\n";
            message.put(text, 0);
            return message;
        }
        if (distance > this.getAttackRange())
        {
            text += "Враг слишком далеко для выстрела, невозможно совершение атаки\n";
            message.put(text, 0);
            return message;
        }
        if (distance <= 5)
        {
            text += "Враг слишком близко для точного выстрела, выстрел с -2 к попаданию\n";
            meleeShot = true;
        }
        ammunition--;
        int rollDiceDamage = RollDice(this.getDamageDice()) + this.getWeaponSharpening();
        int rollDiceHit = RollDice("1D20") + this.getWeaponSharpening();
        if (meleeShot)
        {
            rollDiceHit -= 2;
        }
        text += "Бросок на попадание " + rollDiceHit + "\n";
        if (rollDiceHit < enemyArmour)
        {
            text += "Броня не пробита\n";
            message.put(text, 0);
            return message;
        }
        else
        {
            text += "Броня пробита\n";
            rollDiceDamage += RollDice(this.getDamageDice());
        }

        text += "Нанесено урона " + rollDiceDamage + "\n";
        message.put(text, rollDiceDamage);
        return message;
    }
}
