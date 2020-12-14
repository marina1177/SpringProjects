package com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementOperation {

  public void fun(Connection connection) throws SQLException, ClassNotFoundException {

    // создаем объект Statement
    Statement stmt = connection.createStatement();

    // формулируем запрос;
    String sql = "CREATE TABLE IF NOT EXISTS main"
        + "(id      INT  PRIMARY  KEY      NOT NULL,"
        + " name         VARCHAR (128)     NOT NULL,\n"
        + " age          INT               NOT NULL,"
        + " address      CHAR(50),"
        + " salary       REAL)";

    // создать таблицу
    stmt.executeUpdate(sql);

    // освободить ресурсы
    stmt.close();

    // закоммитить несколько атомарных изменений
    connection.setAutoCommit(false);

    stmt = connection.createStatement();

    sql = "INSERT INTO MAIN (ID, NAME, AGE, ADDRESS, SALARY) VALUES (10, 'Karl',21,'California',1200.00);";
    stmt.executeUpdate(sql);

    sql = "INSERT INTO MAIN (ID, NAME, AGE, ADDRESS, SALARY) VALUES (22, 'Klara',22,'Texas',2000.00);";
    stmt.executeUpdate(sql);

    sql = "INSERT INTO MAIN (ID, NAME, AGE, ADDRESS, SALARY) VALUES (33, 'Teddy', 23,'Norway', 20000.00);";
    stmt.executeUpdate(sql);

    sql = "INSERT INTO MAIN (ID, NAME, AGE, ADDRESS, SALARY) VALUES (44, 'Mark', 25,'Rich-Mond', 65000.00);";
    stmt.executeUpdate(sql);

    stmt.close();
    connection.commit();

    // проверим, все ли записалось
    stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT  * FROM  MAIN;");

    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      int age = rs.getInt("age");
      String address = rs.getString("address");
      float salary = rs.getFloat("salary");

      System.out.println("ID: " + id);
      System.out.println("NAME: " + name);
      System.out.println("AGE: " + age);
      System.out.println("ADDRESS: " + address);
      System.out.println("SALARY: " + salary + "\n");
    }

    // освобождение ресурсов
    rs.close();
    stmt.close();

    System.out.println("\n**************\n");
    // внести изменения в поле БД
    stmt = connection.createStatement();
    sql = "UPDATE MAIN SET SALARY = 3000.00 WHERE ID = 1;";
    stmt.executeUpdate(sql);
    connection.commit();

    // проверим, все ли записалось
    rs = stmt.executeQuery("SELECT  * FROM  MAIN;");

    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      int age = rs.getInt("age");
      String address = rs.getString("address");
      float salary = rs.getFloat("salary");

      System.out.println("ID: " + id);
      System.out.println("NAME: " + name);
      System.out.println("AGE: " + age);
      System.out.println("ADDRESS: " + address);
      System.out.println("SALARY: " + salary);
    }
    // освобождение ресурсов
    rs.close();
    stmt.close();

    System.out.println("\n**************\n");
    // внести изменения в поле БД
    stmt = connection.createStatement();
    sql = "DELETE FROM MAIN WHERE ID=2;";
    stmt.executeUpdate(sql);
    connection.commit();

    rs = stmt.executeQuery("SELECT  * FROM  MAIN;");
    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      int age = rs.getInt("age");
      String address = rs.getString("address");
      float salary = rs.getFloat("salary" );

      System.out.println("ID: " + id);
      System.out.println("NAME: " + name);
      System.out.println("AGE: " + age);
      System.out.println("ADDRESS: " + address);
      System.out.println("SALARY: " + salary);

    }

    // освобождение ресурсов
    rs.close();
    stmt.close();
    connection.close();
  }

}
