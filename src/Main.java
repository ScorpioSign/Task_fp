import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //Напишите приложение, которое на входе получает через консоль текст, а в ответ выдает статистику:
    //1. Количество слов в тексте.
    //2. Топ-10 самых часто упоминаемых слов, упорядоченных по количеству упоминаний в
    //обратном порядке. В случае одинакового количества упоминаний слова должны быть отсортированы по алфавиту.
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите текст: ");
        String text = scn.nextLine();
        System.out.println();
        System.out.printf("В тексте %d слов", countWords(Arrays.asList((text))));
        System.out.println();
        System.out.println("TOP-10: ");
        System.out.println(countStatistics(Arrays.asList((text))));

    }

    public static Long countWords(List<String> sumWords) {
        return sumWords
                .stream()
                .map(string -> string.replaceAll("[^\\p{L}\\p{N}]", " ")) //убираем ненужные символы
                .flatMap(string -> Arrays.stream(string.split("\\s+"))) // делим по пробелу
                .count();
    }

    public static String countStatistics(List<String> words) {
        return words
                .stream()
                .map(line -> line.toLowerCase()
                        .replaceAll("[^\\p{L}\\p{N}]", " "))
                .flatMap(line -> Arrays.stream(line.split("\\s+"))) // поток слов в нижнем регистре без символов
                .collect(Collectors.groupingBy(
                        word -> word,
                        TreeMap::new, // TreeMap - сортировка по ключу по умолчанию
                        Collectors.counting() ))
                // HashMap<String, Long> - словарь со значениями частот
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(e -> e.getValue() + " - " + e.getKey())
                .collect(Collectors.joining("\n")); // результат
    }

}