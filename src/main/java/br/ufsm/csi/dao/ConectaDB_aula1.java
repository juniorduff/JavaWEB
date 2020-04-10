package br.ufsm.csi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConectaDB_aula1 {

    public static  void main(String args[]){

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/CODVID19",
                            "postgres",
                            "postgres");
            System.out.println("conexao realizada com sucesso");

            // Instanciando o objeto statement (stmt)
            Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery("select * from usuario");

        while (rs.next()){
            System.out.println("id: "+ rs.getInt("id_usuario"));
            System.out.println("nome: "+rs.getString("nome"));
            System.out.println("email: "+rs.getString("email"));
        }
        stmt.close();
        conn.close();

        } catch (Exception e){
            e.printStackTrace();

        }

    }

}
