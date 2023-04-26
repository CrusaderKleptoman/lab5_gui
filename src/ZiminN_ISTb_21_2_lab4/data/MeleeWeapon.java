package ZiminN_ISTb_21_2_lab4.data;

import javax.swing.*;
import java.util.HashMap;

public class MeleeWeapon extends BaseWeapon {
    public MeleeWeapon()
    {
        super();
        this.setWeaponName("Ножка от стула");
        this.setDamageDice("1D4");
        this.setWeaponSharpening(0);
        this.setAttackRange(5);
    }

    public MeleeWeapon(String weaponName, String damageDice,  int accuracy, int attackRange)
    {
        super(weaponName, damageDice, accuracy, attackRange);
    }

    public MeleeWeapon(MeleeWeapon meleeWeapon)
    {
        super(meleeWeapon);
    }

    @Override
    public int Attack(int enemyArmour, int distance, JTextArea outputText) {

        if (distance > this.getAttackRange())
        {
            outputText.append("Враг слишком далеко для удара, невозможно совершение атаки\n");
            return 0;
        }

        int rollDiceDamage = this.getWeaponSharpening();;
        int rollDiceHit = RollDice("1D20") + this.getWeaponSharpening();
        outputText.append("Бросок на попадание " + rollDiceHit + "\n");
        if (rollDiceHit < enemyArmour)
        {
            outputText.append("Броня не пробита\n");
            return 0;
        }
        else
        {
            outputText.append("Броня пробита\n");

        }

        outputText.append("Нанесено урона " + rollDiceDamage + "\n");
        return rollDiceDamage;
    }
}
