package br.ufg.inf.quintacalendario.controller;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Categoria;
import br.ufg.inf.quintacalendario.service.CategoriaService;
import br.ufg.inf.quintacalendario.view.console.TelaCategoriaConsole;
import org.hibernate.SessionFactory;

import java.util.List;

public class CategoriaController {

    private TelaCategoriaConsole tela;
    private SessionFactory sessionFactory;

    public CategoriaController() {
        tela = new TelaCategoriaConsole(System.err);
        sessionFactory = Application.getInstance().getSessionFactory();
    }

    public void exibaOpcoes() throws Exception {
        getTela().exibaOpcoes();
    }

    public boolean cadastrar(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);

        CategoriaService service = new CategoriaService(getSessionFactory());
        return service.salvar(categoria);
    }

    public List<Categoria> listar() {
        CategoriaService service = new CategoriaService(getSessionFactory());
        return service.listar();
    }

    public List<Categoria> listar(String descricao) {
        CategoriaService service = new CategoriaService(getSessionFactory());
        return service.listar(descricao);
    }

    public Categoria listarPorId(Integer codigo) {
        CategoriaService service = new CategoriaService(getSessionFactory());
        return service.listarPorId(codigo);
    }

    public void editar(Integer codigo, String nome) {
        CategoriaService service = new CategoriaService(getSessionFactory());
        service.editar(codigo, nome);
    }

    public void remover(Integer codigo) throws Exception {
        CategoriaService service = new CategoriaService(getSessionFactory());
        Categoria categoria = service.listarPorId(codigo);
        if (categoria == null) {
            System.err.println("*******Codigo invalido*******");
            System.err.println("");
            getTela().remover();
        } else {
            service.remover(codigo);
        }
    }

    public TelaCategoriaConsole getTela() {
        return tela;
    }

    public void setTela(TelaCategoriaConsole tela) {
        this.tela = tela;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
