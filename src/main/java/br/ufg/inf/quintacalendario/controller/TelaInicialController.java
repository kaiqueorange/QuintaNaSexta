package br.ufg.inf.quintacalendario.controller;

import br.ufg.inf.quintacalendario.view.console.TelaInicialConsole;

public class TelaInicialController {

    public void redirect(int opcao) throws Exception {
        switch (opcao) {
            case 1:
                new EventosController().exibaOpcoes();
                break;
            case 2:
                new RegionalController().exibaOpcoes();
                break;
            case 3:
                new CategoriaController().exibaOpcoes();
                break;
            case 4:
                new InstitutoController().exibaOpcoes();
                break;
            case 5:
                new UsuarioController().exibaOpcoes();
                break;
            case 6:
            	new TelaInicialConsole(System.out).exibaOpcoes();
                break;
            default:
                break;
        }
    }

}
