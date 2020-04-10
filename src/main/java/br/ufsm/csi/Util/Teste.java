package br.ufsm.csi.Util;

import br.ufsm.csi.dao.ConectaDB;
import br.ufsm.csi.dao.UsuarioDAO;
import br.ufsm.csi.model.Permissao;
import br.ufsm.csi.model.Usuario;

public class Teste {
    public static void main(String args[]) {
          //TestarCadastrarUsuario();
       // TestaGetUsuario();
         // TestaGetUsuarioEmail();
        //TestaDeletar();
      //  TestaUpdate();


    }

    public static void TestarCadastrarUsuario() {
        Permissao p = new Permissao(1, "MEDICO");
        //   Permissao p = new Permissao();

        Usuario usuario = new Usuario("UsuarioEmail2", "email@teste2.com", "1234", true, p);
        new UsuarioDAO().CadastrarUsuario(usuario);
    }

    public static void TestaGetUsuario() {

        for (Usuario u : new UsuarioDAO().getUsuarios()) {
            System.out.println("nome: " + u.getNome());
            System.out.println("permissao: " + u.getPermissao().getNome());

        }

    }
    // NAO CONSEGUI RESOLVER O ERRO
    public static void TestaGetUsuarioEmail() {

        for (Usuario u : new UsuarioDAO().BuscarUsuario("email@teste2.com")) {
            System.out.println("nome: " + u.getNome());
            System.out.println("permissao: " + u.getPermissao().getNome());

        }

    }
//          INFORMA O ID A SER DELETADO  E EXECUTA DELETE IN CASCATE
    public static void TestaDeletar() {
        Usuario usuario = new Usuario(27);
        new UsuarioDAO().deleter(usuario);

        }
        //  INFORMA O ID A SER REALIZADO UPDATE
    public static void TestaUpdate() {
        Usuario usuario = new Usuario(6);
        usuario.setNome("alterou");
        new UsuarioDAO().alterar(usuario);

    }


}
