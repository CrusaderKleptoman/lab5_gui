package ZiminN_ISTb_21_2_lab4;

public class MeleeWeapon extends BaseWeapon{
    public MeleeWeapon()
    {
        super();
        this.setWeaponName("Ножка от стула");
        this.setDamageDice(Dice.D4);
        this.setDamageDiceAmount(1);
        this.setWeaponSharpening(0);
        this.setAttackRange(5);
    }

    public MeleeWeapon(String weaponName, Dice damageDice, int damageDiceAmount, int accuracy, int attackRange)
    {
        super(weaponName, damageDice, damageDiceAmount, accuracy, attackRange);
    }

    public MeleeWeapon(MeleeWeapon meleeWeapon)
    {
        super(meleeWeapon);
    }

    @Override
    public int Attack(int enemyArmour, int distance) {

        if (distance > this.getAttackRange())
        {
            System.out.println("Враг слишком далеко для удара, невозможно совершение атаки");
            return 0;
        }

        int rollDiceDamage = this.getWeaponSharpening();;
        int rollDiceHit = RollDice(Dice.D20) + this.getWeaponSharpening();
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
