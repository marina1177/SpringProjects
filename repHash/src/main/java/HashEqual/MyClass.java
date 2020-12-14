package HashEqual;

import java.util.Objects;

public class MyClass {

  int id;
  String name;

  public MyClass(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyClass myClass = (MyClass) o;
    return Objects.equals(id, myClass.id) &&
        Objects.equals(name, myClass.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash( id,name);
  }



}
