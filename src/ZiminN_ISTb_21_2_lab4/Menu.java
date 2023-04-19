package ZiminN_ISTb_21_2_lab4;

import ZiminN_ISTb_21_2_lab4.data.*;

import java.io.*;
import java.util.Scanner;

public class Menu {

    private static WeaponList weaponList = new WeaponList();
    private static BaseWeapon weapon;
    private static int readCommand() {
        Scanner scanner = new Scanner(System.in);
        int command = -1;
        try {
            command = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("Неверное значение команды, повторите ввод");
            command = readCommand();
        }
        return command;
    }

    public static void MainMenu()
    {
        int command = -1;
        do {
            System.out.println("_______________________________________________________________");
            System.out.println("Главное меню");
            System.out.println("1 - перейти в арсенал");
            System.out.println("2 - перейти в тренировочный зал");
            System.out.println("0 - закрыть программу");
            command = readCommand();

            switch (command) {
                case 1:
                    WeaponListMenu();
                    break;
                case 2:
                    WeaponMenu();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Команды нет в списке, повторите ввод");
            }

        } while (command != 0);
    }

    private static void WeaponListMenu()
    {
        String armory = "armory.bin";
        int weaponAmount;
        String weaponType;
        Dice[] diceList = Dice.values();
        int ID;
        Scanner scanner = new Scanner(System.in);
        int command = -1;
        do {
            System.out.println("_______________________________________________________________");
            System.out.println("Арсенал");
            System.out.println("1 - проверить арсенал(считать оружие из файла)");
            System.out.println("2 - добавить оружие в арсенал");
            System.out.println("3 - убрать оружие из арсенала по номеру");
            System.out.println("4 - проверить оружие по номеру");
            System.out.println("5 - закрыть арсенал(с добавлением всего нового оружия в файл)");
            System.out.println("0 - вернуться в главное меню");
            command = readCommand();

            switch (command) {
                case 1:
                    weaponList.clearWeaponList();
                    try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(armory))) {
                        weaponAmount = Integer.parseInt(dataInputStream.readUTF());
                        for (int i = 0; i < weaponAmount; i++) {
                            weaponType = dataInputStream.readUTF();
                            if (weaponType.equals("MeleeWeapon"))
                            {
                                MeleeWeapon meleeWeapon = new MeleeWeapon(dataInputStream.readUTF(), diceList[Integer.parseInt(dataInputStream.readUTF())], Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                                weaponList.addWeapon(meleeWeapon);
                            }
                            if (weaponType.equals("RangeWeapon"))
                            {
                                RangeWeapon rangeWeapon = new RangeWeapon(dataInputStream.readUTF(), diceList[Integer.parseInt(dataInputStream.readUTF())], Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()), Integer.parseInt(dataInputStream.readUTF()));
                                weaponList.addWeapon(rangeWeapon);
                            }
                        }
                    }
                    catch (NumberFormatException exception)
                    {
                        System.out.println("Неверный формат данных в файле или внезапный конец файла, возвращение в меню \n");
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("Доступ к арсеналу отсутствует(не найден файл с данными)");;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Арсенал успешно проверен");
                    weaponList.writeWeaponList();
                    break;
                case 2:
                    String name;
                    int dice;
                    int diceAmount;
                    int sharp;
                    int range;
                    int ammunition;
                    System.out.println("Выберите тип оружия, введите 1 для ближнего, 2 для дальнего");
                    try {
                        while(true) {
                            weaponType = scanner.nextLine();
                            if (weaponType.equals("1")) {
                                System.out.println("Введите название оружие");
                                name = scanner.nextLine();
                                System.out.println("Введите номер кости урона");
                                System.out.println("1 - d2\n2 - d4\n3 - d6\n4 - d8\n5 - d10\n6 - d12");
                                dice = readCommand();
                                System.out.println("Введите количество костей урона");
                                diceAmount = readCommand();
                                System.out.println("Введите модификатор оружия");
                                sharp = readCommand();
                                System.out.println("Введите дистанцию атаки оружия");
                                range = readCommand();
                                MeleeWeapon meleeWeapon = new MeleeWeapon(name, diceList[dice - 1], diceAmount, sharp, range);
                                weaponList.addWeapon(meleeWeapon);
                                System.out.println("Оружие добавлено в арсенал");
                                break;
                            }
                            if (weaponType.equals("2")) {
                                System.out.println("Введите название оружие");
                                name = scanner.nextLine();
                                System.out.println("Введите номер кости урона");
                                System.out.println("1 - d2\n2 - d4\n3 - d6\n4 - d8\n5 - d10\n6 - d12");
                                dice = readCommand();
                                System.out.println("Введите количество костей урона");
                                diceAmount = readCommand();
                                System.out.println("Введите модификатор оружия");
                                sharp = readCommand();
                                System.out.println("Введите дистанцию атаки оружия");
                                range = readCommand();
                                System.out.println("Введите запас аммуниции");
                                ammunition = readCommand();
                                RangeWeapon rangeWeapon = new RangeWeapon(name, diceList[Math.min(5, dice - 1)], diceAmount, sharp, range, ammunition);
                                weaponList.addWeapon(rangeWeapon);
                                System.out.println("Оружие добавлено в арсенал");
                                break;
                            }
                            else {
                                System.out.println("Неверный класс оружия, повторите ввод");
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException)
                    {
                        System.out.println("Неверное значение кости урона");
                    }
                    break;
                case 3:
                    System.out.println("Введите номер оружия");
                    ID = readCommand();
                    weaponList.removeWeaponWithID(ID - 1);
                    break;
                case 4:
                    System.out.println("Введите номер оружия");
                    ID = readCommand();
                    weaponList.writeWeapon(ID - 1);
                    break;
                case 5:
                    try(DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(armory)))
                    {
                        BaseWeapon weaponOutput;
                        dataOutputStream.writeUTF(Integer.toString(weaponList.weaponAmount()));
                        for (int i = 0; i < weaponList.weaponAmount(); i++) {
                            weaponOutput = weaponList.getWeapon(i);
                            if(weaponOutput.getClass() == MeleeWeapon.class)
                            {
                                dataOutputStream.writeUTF("MeleeWeapon");
                                dataOutputStream.writeUTF(weaponOutput.getWeaponName());
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getDamageDice().ordinal()));
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getDamageDiceAmount()));
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getWeaponSharpening()));
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getAttackRange()));
                            }
                            if(weaponOutput.getClass() == RangeWeapon.class)
                            {
                                dataOutputStream.writeUTF("RangeWeapon");
                                dataOutputStream.writeUTF(weaponOutput.getWeaponName());
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getDamageDice().ordinal()));
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getDamageDiceAmount()));
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getWeaponSharpening()));
                                dataOutputStream.writeUTF(Integer.toString(weaponOutput.getAttackRange()));
                                dataOutputStream.writeUTF(Integer.toString(((RangeWeapon) weaponOutput).getAmmunition()));
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Доступ к арсеналу отсутствует(не найден файл с данными)");;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Оружие успешно сохранено в арсенале");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Команды нет в списке, повторите ввод");
            }

        } while (command != 0);
    }

    private static void WeaponMenu()
    {
        int ID = -1;
        int command = -1;
        do {
            System.out.println("_______________________________________________________________");
            System.out.println("Тренировочный зал");
            System.out.println("1 - взять по номеру оружие на проверку");
            System.out.println("2 - оценить характеристики оружия");
            System.out.println("3 - проверить оружие на манекене");
            System.out.println("0 - вернуться в главное меню");
            command = readCommand();

            switch (command) {
                case 1:
                        System.out.println("Введите номер оружия");
                        ID = readCommand() - 1;
                        weapon = weaponList.getWeapon(ID);
                        if(weapon == null) {break;}
                        weaponList.writeWeapon(ID);
                    break;
                case 2:
                    if (ID == -1) {System.out.println("Оружие ещё не выбрано");}
                    else {weaponList.writeWeapon(ID);}
                    break;
                case 3:
                    try {
                        System.out.println("Введите уровень защиты на манекене и дистанцию до него");
                        System.out.println("Уровень защиты = "); int armoryLevel = readCommand();
                        System.out.println("Дистанция в футах = "); int distance = readCommand();
                        weapon.Attack(armoryLevel, distance);
                    }
                    catch (NullPointerException nullPointerException)
                    {
                        System.out.println("Оружие ещё не выбрано");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Команды нет в списке, повторите ввод");
            }
        } while (command != 0);
    }
}
