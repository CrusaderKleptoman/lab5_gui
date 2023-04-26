package ZiminN_ISTb_21_2_lab4.data;

import javax.swing.*;
import java.util.Random;

abstract public class BaseWeapon {
    private static Random random = new Random();
    private String weaponName;
    private Dice damageDice;
    private int damageDiceAmount;
    private int weaponSharpening;
    private int attackRange;

    public BaseWeapon() {

    }

    public BaseWeapon(String weaponName, Dice damageDice, int damageDiceAmount, int weaponSharpening, int attackRange)
    {
        this.weaponName = weaponName;
        this.damageDice = damageDice;
        this.damageDiceAmount = damageDiceAmount;
        this.weaponSharpening = weaponSharpening;
        this.attackRange = attackRange;

    }

    public BaseWeapon(BaseWeapon weapon)
    {
        this.weaponName = weapon.weaponName;
        this.damageDice = weapon.damageDice;
        this.damageDiceAmount = weapon.damageDiceAmount;
        this.weaponSharpening = weapon.weaponSharpening;
        this.attackRange = weapon.attackRange;

    }

    public String getWeaponName() {return weaponName;}
    public Dice getDamageDice() {return damageDice;}
    public int getDamageDiceAmount() {return damageDiceAmount;}
    public int getAttackRange() {return attackRange;}
    public int getWeaponSharpening() {return weaponSharpening;}

    public void setWeaponName(String weaponName) {this.weaponName = weaponName;}
    public void setDamageDice(Dice damageDice) {this.damageDice = damageDice;}
    public void setDamageDiceAmount(int damageDiceAmount) {this.damageDiceAmount = damageDiceAmount;}
    public void setAttackRange(int attackRange) {this.attackRange = attackRange;}
    public void setWeaponSharpening(int weaponSharpening) {this.weaponSharpening = weaponSharpening;}

    public int RollDice(Dice dice)
    {
        switch (dice){
            case D2: return (random.nextInt(2) + 1);
            case D4: return (random.nextInt(4) + 1);
            case D6: return (random.nextInt(6) + 1);
            case D8: return (random.nextInt(8) + 1);
            case D10: return (random.nextInt(10) + 1);
            case D12: return (random.nextInt(12) + 1);
            case D20: return (random.nextInt(20) + 1);
            case D100: return (random.nextInt(100) + 1);
        }
        return 0;
    }

    abstract public int Attack(int enemyArmour, int distance, JTextArea outputText);
}
