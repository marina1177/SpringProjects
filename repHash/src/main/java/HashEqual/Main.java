package HashEqual;


import java.util.HashSet;
import java.util.Set;

public class Main {

  public static void main(String[] args) {

    MyClass o1 = new MyClass(1, "name");
    MyClass o2 = new MyClass(1, "name");


    System.out.println("equals: " + o1.equals(o2));
    System.out.println("o1HashCode: "+o1.hashCode()+"\no2HashCode: "+o2.hashCode());

    Set<MyClass> set = new HashSet<>();

    set.add(o1);
    System.out.println(set.size());
    System.out.println(set.contains(o2));

  }






}
