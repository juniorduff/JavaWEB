package br.ufsm.csi.dao;

import br.ufsm.csi.model.Permissao;
import br.ufsm.csi.model.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {
    private String sql ;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String status;

        public ArrayList<Usuario> getUsuariosSemPermissao(){
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
            try(Connection connection = new ConectaDB().getConexao()){
                this.sql = "SELECT * FROM USUARIO";
                this.statement = connection.createStatement();
                this.resultSet = statement.executeQuery(sql);

                while (this.resultSet.next()){
                    Usuario usuario = new Usuario();
                    usuario.setId(this.resultSet.getInt("id_usuario"));
                    usuario.setNome(this.resultSet.getString("nome"));
                    usuario.setEmail(this.resultSet.getString("email"));
                    usuario.setAtivo(this.resultSet.getBoolean("ativo"));
                    this.preparedStatement.execute();

                    usuarios.add(usuario);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

            return   usuarios;

        }

        public ArrayList<Usuario> getUsuarios(){
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
            try(Connection connection = new ConectaDB().getConexao()){
                this.sql = "SELECT * FROM USUARIO  us, permissao pe , usuario_permissao up " +
                        "WHERE us.id_usuario = up.id_usuario and pe.id_permissao = up.id_permissao";

                this.statement = connection.createStatement();
                this.resultSet = statement.executeQuery(sql);

                while (this.resultSet.next()){
                    Usuario usuario = new Usuario();
                    usuario.setId(this.resultSet.getInt("id_usuario"));
                    usuario.setNome(this.resultSet.getString("nome"));
                    usuario.setEmail(this.resultSet.getString("email"));
                    usuario.setAtivo(this.resultSet.getBoolean("ativo"));

                    Permissao permissao = new Permissao();
                    permissao.setId(this.resultSet.getInt("id_permissao"));
                    permissao.setNome(this.resultSet.getString("nome_permissao"));

                    usuario.setPermissao(permissao);
                    usuarios.add(usuario);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

            return   usuarios;

        }
        public String CadastrarUsuario(Usuario usuario){
            try(Connection connection = new ConectaDB().getConexao()){

                connection.setAutoCommit(false);
                this.sql = "INSERT INTO USUARIO(nome,email,senhaa,data_cadastro,ativo)" +
                                " VALUES (?,?,?,CURRENT_DATE,?)";

                this.preparedStatement = connection.prepareStatement(this.sql,preparedStatement.RETURN_GENERATED_KEYS);
                this.preparedStatement.setString(1,usuario.getNome());
                this.preparedStatement.setString(2,usuario.getEmail());
                this.preparedStatement.setString(3,usuario.getSenha());
                this.preparedStatement.setBoolean(4,usuario.isAtivo());

                this.preparedStatement.execute();
                this.resultSet = this.preparedStatement.getGeneratedKeys();
                this.resultSet.next();


                if(this.resultSet.getInt(1)>0){

                       usuario.setId(this.resultSet.getInt(1));
                        this.status = "OK";
                }
                if (this.status.equals("OK")){
                    this.sql ="INSERT INTO usuario_permissao (id_usuario,id_permissao) " +
                            "values (?,?)";

                    this.preparedStatement=connection.prepareStatement(this.sql,preparedStatement.RETURN_GENERATED_KEYS);
                    this.preparedStatement.setInt(1,usuario.getId());
                    this.preparedStatement.setInt(2,usuario.getPermissao().getId());

                    this.preparedStatement.execute();
                    this.resultSet = this.preparedStatement.getGeneratedKeys();
                    this.resultSet.next();

                    if(this.resultSet.getInt(1)>0){
                        connection.commit();
                        this.status ="OK";
                        System.out.println("dentro do if insert usuario_permissao");
                    }


                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.status= "problema";

            }
            return this.status;
        }


        public ArrayList<Usuario> BuscarUsuario(String email){
            System.out.println(email);
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
         try(Connection connection = new ConectaDB().getConexao()) {
             this.sql = "SELECT * FROM USUARIO  us, permissao pe , usuario_permissao up " +
                     "WHERE  email = ? " +
                     "and us.id_usuario = up.id_usuario and pe.id_permissao = up.id_permissao";
             this.preparedStatement.setString(1,email);

             this.statement = connection.createStatement();
             this.resultSet = statement.executeQuery(sql);
//

             while (this.resultSet.next()) {

                 Usuario usuario = new Usuario();
                 usuario.setId(this.resultSet.getInt("id_usuario"));
                 usuario.setNome(this.resultSet.getString("nome"));
                 usuario.setEmail(this.resultSet.getString("email"));
                 usuario.setAtivo(this.resultSet.getBoolean("ativo"));

                 Permissao permissao = new Permissao();
                 permissao.setId(this.resultSet.getInt("id_permissao"));
                 permissao.setNome(this.resultSet.getString("nome_permissao"));

                 usuario.setPermissao(permissao);
                 usuarios.add(usuario);
             }


            }catch (SQLException e){
                e.printStackTrace();
            }
            return usuarios;


        }
        public String alterar (Usuario usuario){

            try(Connection connection = new ConectaDB().getConexao()) {
                this.sql = "UPDATE USUARIO SET nome= ?" +
                        " WHERE id_usuario = ?";
                this.preparedStatement=connection.prepareStatement(this.sql);
                this.preparedStatement.setString(1,usuario.getNome());
                this.preparedStatement.setInt(2,usuario.getId());
                this.preparedStatement.execute();
                System.out.println(usuario.getNome());
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }
        public String deleter (Usuario usuario){

            try(Connection connection = new ConectaDB().getConexao()) {

                this.sql = "DELETE  FROM  USUARIO" +
                       " WHERE id_usuario = ?";
                this.preparedStatement=connection.prepareStatement(this.sql);
                this.preparedStatement.setInt(1,usuario.getId());
                this.preparedStatement.execute();



                System.out.println("USUARIO DELETADO ");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

}
