package br.ufg.inf.quintacalendario.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.ufg.inf.quintacalendario.model.Categoria;
import br.ufg.inf.quintacalendario.repository.CategoriaRepository;

public class CategoriaService {
	
	private SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(CategoriaService.class);
	
	public CategoriaService(SessionFactory session) {
		super();
		sessionFactory = session;
	}

	public boolean salvar(Categoria categoria){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			validarCategoria(categoria);
			
			new CategoriaRepository(session).salvar(categoria);
			transaction.commit();
			session.close();
			
			return true;
		} catch (IllegalArgumentException e) {
			transaction.rollback();
			session.close();
			logger.error(e.getMessage());
			return false;
		}
	}
	
	public void validarCategoria(Categoria categoria){
		if (categoria.getNome().trim().isEmpty()) {
			throw new IllegalArgumentException("O nome da categoria nao pode ser vazio");
		}
		
		if ((categoria.getNome().trim().length()) < 4) {
			throw new IllegalArgumentException("O node da categoria deve ter no minimo 4 caracteres");
		}
	}
	
	public List<Categoria> listar(){
		Session session = sessionFactory.openSession();
		return new CategoriaRepository(session).listar();
	}
	
	public void limparTabela(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		new CategoriaRepository(session).limparTabela();
		transaction.commit();
		session.close();
	}

	public List<Categoria> listar(String descricao) {
		Session session = sessionFactory.openSession();
		return new CategoriaRepository(session).listarPorDescricao(descricao);
	}

	public Categoria listarPorId(Integer codigo) {
		Session session = sessionFactory.openSession();
		return new CategoriaRepository(session).listarPorId(codigo);
	}

	public void editar(Integer codigo, String nome) {
		Session session = sessionFactory.openSession();
		CategoriaRepository repository = new CategoriaRepository(session);
		Categoria categoria = repository.listarPorId(codigo);
		
		Transaction transaction = session.beginTransaction();
		
		categoria.setNome(nome);
		repository.atualizar(categoria);
		
		transaction.commit();
		session.close();
	}

	public void remover(Integer codigo) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		new CategoriaRepository(session).remover(codigo);
		transaction.commit();
		session.close();
	}
}
