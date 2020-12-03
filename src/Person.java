public class Person {
    private String name;
    private Integer age;

    Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "["+this.name+", "+this.age+"]";
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
