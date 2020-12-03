import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Java Stream API!");
        List<String> strings = Arrays.asList("Bob", "Alice", "Joe", "John");
        strings.stream() // source
                .filter(s -> s.startsWith("J")) // op 1
                .map(String::toUpperCase) // op 2
                .sorted() // op 3
                .forEach(System.out::println); // terminal
        // stream sources
        Arrays.asList(1, 2, 3, 4).stream()
                .forEach(System.out::println);
        Stream.of("a", "b", "c")
                .findAny() // terminal
                .ifPresent(System.out::println); // not a stream
        IntStream.range(1, 4)
                .average() // terminal
                .ifPresentOrElse(System.out::println, () -> System.out.println("Value does not present")); // not a stream
        Arrays.stream(new int[] {3, 2, 1})
                .max() // terminal
                .ifPresent(System.out::println); // not a stream
        // processing order
        System.out.println("--- Processing Order ---");
        Stream.of("John", "Bob", "Alice", "Susan", "Joe", "Jaghan")
                .sorted((s1, s2) -> {
                    System.out.println("sort: "+s1+"; "+s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: "+s);
                    return s.startsWith("J");
                })
                .map(s -> {
                    System.out.println("map: "+s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: "+s));
        System.out.println("--- Processing Order Optimized ---");
        Stream.of("John", "Bob", "Alice", "Susan", "Joe", "Jaghan")
                .filter(s -> { // filter first
                    System.out.println("filter: "+s);
                    return s.startsWith("J");
                })
                .sorted((s1, s2) -> { // then sorting is less operations
                    System.out.println("sort: "+s1+"; "+s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: "+s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: "+s));
        // collect terminal
        System.out.println("--- Collect Terminal ---");
        List<Person> people = Arrays.asList(
                new Person("John", 12),
                new Person("Jack", 23),
                new Person("Alice", 18),
                new Person("Susan", 50),
                new Person("Jack", 5)
        );
        // collect tot List
        List<Person> filtered = people.stream()
                .filter(p -> p.getName().startsWith("J"))
                .collect(Collectors.toList());
        System.out.println("Filtered by J: "+filtered);
        // collect grouping by name
        Map<String, List<Person>> groupedByName = people.stream()
                .collect(Collectors.groupingBy(Person::getName)); // ctrl+alt+v
        System.out.println("Grouped: "+groupedByName);
        // collect joining strings
        String phrase = people.stream().parallel()
                .filter(p -> p.getAge() >= 18)
                .map(Person::getName)
                .collect(Collectors.joining(" and ", "In Poland ", " are of legal age."));
        System.out.println(phrase);
    }
}
