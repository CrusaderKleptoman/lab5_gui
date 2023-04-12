package ZiminN_ISTb_21_2_lab4;

import java.util.Scanner;

public class RangeWeapon extends BaseWeapon{
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
    public int Attack(int enemyArmour, int distance) {
        Scanner scanner = new Scanner(System.in);
        Boolean meleeShot = false;
        if (ammunition == 0)
        {
            System.out.println("Отсутствуют боеприпасы для выстрела, завершение выстрела");
            return 0;
        }
        if (distance > this.getAttackRange())
        {
            System.out.println("Враг слишком далеко для выстрела, невозможно совершение атаки");
            return 0;
        }
        if (distance <= 5)
        {
            System.out.println("Враг слишком близко для точного выстрела, выстрелить с -2 к попаданию? Для подтверждения введите 1");
            if(scanner.nextLine().equals("1"))
            {
                System.out.println("Выстрел в ближнем бою подтверждён");
                meleeShot = true;
            }
            else
            {
                System.out.println("Завершение атаки");
                return 0;
            }
        }
        ammunition--;
        int rollDiceDamage = this.getWeaponSharpening();
        int rollDiceHit = RollDice(Dice.D20) + this.getWeaponSharpening();
        if (meleeShot)
        {
            rollDiceHit -= 2;
        }
        System.out.printf("Бросок на попадание %d\n", rollDiceHit);
        if (rollDiceHit < enemyArmour)
        {
            System.out.println("Броня не пробита");
            return 0;
        }
        else
        {
            System.out.println("Броня пробита");
            for (int i = 0; i < this.getDamageDiceAmount(); i++) {
                rollDiceDamage += RollDice(this.getDamageDice());
            }
        }

        System.out.printf("Нанесено урона %d\n", rollDiceDamage);
        return rollDiceDamage;
    }
}
