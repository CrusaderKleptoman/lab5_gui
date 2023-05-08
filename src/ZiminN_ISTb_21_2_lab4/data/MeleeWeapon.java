package ZiminN_ISTb_21_2_lab4.data;

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
    public HashMap<String, Integer> Attack(int enemyArmour, int distance) {

        HashMap<String, Integer> message = new HashMap<>();
        String text = new String();
        if (distance > this.getAttackRange())
        {
            text += "Враг слишком далеко для удара, невозможно совершение атаки\n";
            message.put(text, 0);
            return message;
        }

        int rollDiceDamage = RollDice(this.getDamageDice()) + this.getWeaponSharpening();;
        int rollDiceHit = RollDice("1D20") + this.getWeaponSharpening();
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

        }

        text += "Нанесено урона " + rollDiceDamage + "\n";
        message.put(text, rollDiceDamage);
        if(RollDice(this.getDamageDice())==0){ message.put("Не указана кость урона", 0);}
        return message;
    }
}
