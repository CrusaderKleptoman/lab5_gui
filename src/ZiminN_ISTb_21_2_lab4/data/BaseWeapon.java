package ZiminN_ISTb_21_2_lab4.data;

import javax.swing.*;
import java.util.HashMap;
import java.util.Random;

abstract public class BaseWeapon {
    private static Random random = new Random();
    private String weaponName;
    private String damageDice;
    private int weaponSharpening;
    private int attackRange;

    public BaseWeapon() {

    }

    public BaseWeapon(String weaponName, String damageDice, int weaponSharpening, int attackRange)
    {
        this.weaponName = weaponName;
        this.damageDice = damageDice;
        this.weaponSharpening = weaponSharpening;
        this.attackRange = attackRange;
    }

    public BaseWeapon(BaseWeapon weapon)
    {
        this.weaponName = weapon.weaponName;
        this.damageDice = weapon.damageDice;
        this.weaponSharpening = weapon.weaponSharpening;
        this.attackRange = weapon.attackRange;

    }

    public String getWeaponName() {return weaponName;}
    public String getDamageDice() {return damageDice;}
    public int getAttackRange() {return attackRange;}
    public int getWeaponSharpening() {return weaponSharpening;}

    public void setWeaponName(String weaponName) {this.weaponName = weaponName;}
    public void setDamageDice(String damageDice) {this.damageDice = damageDice;}
    public void setAttackRange(int attackRange) {this.attackRange = attackRange;}
    public void setWeaponSharpening(int weaponSharpening) {this.weaponSharpening = weaponSharpening;}

    public int RollDice(String dice)
    {
        int i = 0;
        int diceAmount = 0;
        int diceType = 0;
        int result = 0;
        while(dice.charAt(i)!='D')
        {
            diceAmount = diceAmount * 10 + Character.getNumericValue(dice.charAt(i));
            i++;
        }
        i++;
        while(i < dice.length())
        {
            diceType = diceType * 10 + Character.getNumericValue(dice.charAt(i));
            i++;
        }
        for (int j = 0; j < diceAmount; j++) {
            result+=random.nextInt(diceType)+1;
        }
        return result;
    }

    abstract public HashMap<String, Integer> Attack(int enemyArmour, int distance);
}
