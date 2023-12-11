import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
        while (!success) {
            System.out.println("Введите следующие данные через пробел: Фамилия Имя Отчество Датарождения Номертелефона Пол");
            String input = scanner.nextLine();

            String[] params = input.split(" ");
            try {
                validateParams(params);
            } catch (RuntimeException e) {
                System.out.println("В ходе проверки параметров обнаружена ошибка: " + e.getMessage());
                continue;
            }

            String text = String.format("<%s><%s><%s><%s><%s><%s>\n", params[0], params[1], params[2], params[3], params[4], params[5]);
            try {
                if (!Files.exists(Paths.get(params[0]))) {
                    Files.createFile(Paths.get(params[0]));
                }
                Files.write(Paths.get(params[0]), text.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println(e);
                continue;
            }
            success = true;
        }
    }

    private static void validateParams(String[] params) {
        if (params.length > 6) {
            throw new RuntimeException("Количество входных параметров больше 6: " + params.length);
        }

        if (params.length < 6) {
            throw new RuntimeException("Количество входных параметров меньше 6: " + params.length);
        }

        String lettersRegex = "[а-яА-Я]+";
        String lastName = params[0];
        if (!lastName.matches(lettersRegex)) {
            throw new RuntimeException("Фамилия должна быть строкой");
        }

        String name = params[1];
        if (!name.matches(lettersRegex)) {
            throw new RuntimeException("Имя должно быть строкой");
        }

        String surname = params[2];
        if (!surname.matches(lettersRegex)) {
            throw new RuntimeException("Отчество должно быть строкой");
        }

        String birthDateStr = params[3];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            dateFormat.parse(birthDateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Дата рождения не соответствует формату dd.MM.yyyy");
        }

        String phoneStr = params[4];
        String numbersRegex = "\\d+";
        if (!phoneStr.matches(numbersRegex)) {
            throw new RuntimeException("Номер телефона должен быть числом");
        }

        String sex = params[5];
        if (!sex.equalsIgnoreCase("f") && !sex.equalsIgnoreCase("m")) {
            throw new RuntimeException("Пол должен быть f или m");
        }
    }
}