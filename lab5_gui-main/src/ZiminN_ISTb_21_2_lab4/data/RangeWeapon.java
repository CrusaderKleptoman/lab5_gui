package ZiminN_ISTb_21_2_lab4.data;

import javax.swing.*;
import java.util.Scanner;

public class RangeWeapon extends BaseWeapon {
    private int ammunition;
    public RangeWeapon()
    {
        super();
        this.setWeaponName("Камень");
        this.setDamageDice(Dice.D4);
        this.setDamageDiceAmount(1);
        this.setWeaponSharpening(0);
        this.setAttackRange(20);
        this.ammunition = 20;
    }

    public RangeWeapon(String weaponName, Dice damageDice, int damageDiceAmount, int accuracy, int attackRange)
    {
        super(weaponName, damageDice, damageDiceAmount, accuracy, attackRange);
        this.ammunition = 20;
    }

    public RangeWeapon(String weaponName, Dice damageDice, int damageDiceAmount, int accuracy, int attackRange, int ammunition)
    {
        super(weaponName, damageDice, damageDiceAmount, accuracy, attackRange);
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
    public int Attack(int enemyArmour, int distance, JTextArea outputText) {
        Scanner scanner = new Scanner(System.in);
        Boolean meleeShot = false;
        if (ammunition == 0)
        {
            outputText.append("Отсутствуют боеприпасы для выстрела, завершение выстрела\n");
            return 0;
        }
        if (distance > this.getAttackRange())
        {
            outputText.append("Враг слишком далеко для выстрела, невозможно совершение атаки\n");
            return 0;
        }
        if (distance <= 5)
        {
            outputText.append("Враг слишком близко для точного выстрела, выстрел с -2 к попаданию\n");
            meleeShot = true;
        }
        ammunition--;
        int rollDiceDamage = this.getWeaponSharpening();
        int rollDiceHit = RollDice(Dice.D20) + this.getWeaponSharpening();
        if (meleeShot)
        {
            rollDiceHit -= 2;
        }
        outputText.append("Бросок на попадание " + rollDiceHit + "\n");
        if (rollDiceHit < enemyArmour)
        {
            outputText.append("Броня не пробита\n");
            return 0;
        }
        else
        {
            outputText.append("Броня пробита\n");
            for (int i = 0; i < this.getDamageDiceAmount(); i++) {
                rollDiceDamage += RollDice(this.getDamageDice());
            }
        }

        outputText.append("Нанесено урона " + rollDiceDamage + "\n");
        return rollDiceDamage;
    }
}
