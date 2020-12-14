package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementOperation {

  public void fun(Connection connection) throws SQLException {

   PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM main WHERE id = (?)");

      pstmt.setInt(1, 1);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()){
        String name = rs.getString("name");
      }

  }

}
