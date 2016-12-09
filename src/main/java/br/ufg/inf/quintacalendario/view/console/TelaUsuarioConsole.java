package br.ufg.inf.quintacalendario.view.console;

import java.io.PrintStream;

import br.ufg.inf.quintacalendario.controller.Usuario;
import br.ufg.inf.quintacalendario.view.TelaInicial;
import br.ufg.inf.quintacalendario.view.console.util.EntradaConsole;


public class TelaUsuarioConsole extends AbstractTelaCabecalho implements TelaInicial {

    private EntradaConsole entradaConsole;

    public TelaUsuarioConsole(PrintStream output) {
        super(output);
        setEntradaConsole(new EntradaConsole());
    }

    @Override
    public void exibaOpcoes() throws Exception {
        exibaCabecalho();
        desenhaOpcoes();
        realizarLogin();
    }

    @Override
    public int pergunteOpcao() {
        return 0;
    }

    public EntradaConsole getEntradaConsole() {
        return entradaConsole;
    }

    public void setEntradaConsole(EntradaConsole entradaConsole) {
        this.entradaConsole = entradaConsole;
    }

    public String desenhaOpcoes() {
        StringBuilder tela = new StringBuilder();
        tela.append("1 - Listar usuários cadastrados \n")
                .append("2 - Cadastar um novo usuário \n")
                .append("3 - Remover usuário \n")
                .append("4 - Alterar dados de usuário");
        return tela.toString();
    }

    /**
     * Método que pergunta o login e a senha do usuário e retorna um objeto
     * com os dados que foram inseridos.
     *
     * @return Objeto usuário com o nome que corresponde ao login e a senha.
     * @throws Exception 
     */
    public Usuario realizarLogin() throws Exception {
        exibaCabecalho();
        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setNome(new EntradaConsole().pergunteString("- Login: \n"));
        usuarioLogin.setSenha(new EntradaConsole().pergunteString("- Senha: "));
        System.err.println("Opcao em desenvolvimento");
        return usuarioLogin;
    }

    public Usuario cadastrarUsuario() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(new EntradaConsole().pergunteString("Novo Login: "));
        novoUsuario.setSenha(new EntradaConsole().pergunteString("Nova Senha: "));
        String confirmaSenha = new EntradaConsole().pergunteString("Confirme a senha: ");

        if (novoUsuario.getSenha().equals(confirmaSenha)) {
            //Salvar no banco
        } else {
            System.err.println("As senhas não são iguais!\n");
            cadastrarUsuario();
        }
        return novoUsuario;
    }


}
