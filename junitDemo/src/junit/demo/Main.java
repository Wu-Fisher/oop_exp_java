public class Main {

    public static void main(String[] args) {
        Man a = new Man();
        System.out.println(a instanceof Human);

    }

}

class Human {

}

class Man extends Human {

}