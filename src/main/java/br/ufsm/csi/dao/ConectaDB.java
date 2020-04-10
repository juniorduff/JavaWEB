package br.ufsm.csi.dao;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConectaDB {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/CODVID19";
    private static final String USER = "postgres";
    private static final String SENHA = "postgres";

    public Connection getConexao() {
        Connection conn = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,SENHA);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;

    }
}
