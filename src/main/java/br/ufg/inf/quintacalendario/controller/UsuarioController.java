package br.ufg.inf.quintacalendario.controller;

import org.hibernate.SessionFactory;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.view.console.TelaUsuarioConsole;


public class UsuarioController {
    private TelaUsuarioConsole tela;

    public UsuarioController() {
        tela = new TelaUsuarioConsole(System.err);
    }

    public void exibaOpcoes() throws Exception {
        getTela().exibaOpcoes();
    }

    public TelaUsuarioConsole getTela() {
        return tela;
    }
}
