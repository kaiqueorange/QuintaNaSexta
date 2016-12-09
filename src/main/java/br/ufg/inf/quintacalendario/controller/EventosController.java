package br.ufg.inf.quintacalendario.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Categoria;
import br.ufg.inf.quintacalendario.model.Evento;
import br.ufg.inf.quintacalendario.model.Instituto;
import br.ufg.inf.quintacalendario.model.Regional;
import br.ufg.inf.quintacalendario.service.CategoriaService;
import br.ufg.inf.quintacalendario.service.EventoService;
import br.ufg.inf.quintacalendario.service.InstitutoService;
import br.ufg.inf.quintacalendario.service.RegionalService;
import br.ufg.inf.quintacalendario.view.console.TelaEventoConsole;

public class EventosController {
	private static final Logger logger = Logger.getLogger(EventosController.class);
	private TelaEventoConsole tela;
	private SessionFactory sessionFactory;
	
	public EventosController() {
		setTela(new TelaEventoConsole(System.err));
		setSessionFactory(Application.getInstance().getSessionFactory());
	}
	
	public boolean cadastrar(String descricao, String titulo, String dataInicial, String dataFinal, Integer codigoCategoria
			               , Integer codigoRegional, Integer codigoInstituto) {

		try {
			Evento evento = new Evento();
			
			evento.setDescricao(descricao);
			evento.setTitulo(titulo);
			
			Date data = converterStringParaDate(dataInicial);
			evento.setDataInicial(data);
			
			data = converterStringParaDate(dataFinal);
			evento.setDataFinal(data);
			
			evento.setCategoria(new CategoriaService(getSessionFactory()).listarPorId(codigoCategoria));
			
			List<Instituto> institutos = new ArrayList<>();
			institutos.add(new InstitutoService(getSessionFactory()).listarPorId(codigoInstituto));
			
			evento.setInstitutos(institutos);
			
			List<Regional> regionais = new ArrayList<>();
			regionais.add(new RegionalService(getSessionFactory()).listarPorId(codigoRegional));
			
			evento.setRegionais(regionais);
			
			EventoService service = new EventoService(getSessionFactory());
			service.salvar(evento);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public void exibaOpcoes() throws Exception {
		getTela().exibaOpcoes();
	}
	
	public Date converterStringParaDate(String pData){
		if (pData == null || "".equals(pData)) {
			return null;
		}
		
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = formatter.parse(pData);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date;
	}
	
	public List<Evento> listar(){
		EventoService service = new EventoService(getSessionFactory());
		return service.listar();
	}
	
	public List<Evento> listar(String descricao){
		EventoService service = new EventoService(getSessionFactory());
		return service.listarPorDescricao(descricao);
	}
	
	public Evento listarPorId(Integer codigo){
		EventoService service = new EventoService(getSessionFactory());
		return service.listarPorId(codigo);
	}
	
	public List<Evento> listarPorPeriodo(String dataInicial, String dataFinal){
		EventoService service = new EventoService(getSessionFactory());
		return service.listarEventosPorPeriodo(converterStringParaDate(dataInicial), converterStringParaDate(dataFinal));
	}
	
	public List<Regional> listarRegionais() {
		RegionalService service = new RegionalService(getSessionFactory());
		return service.listar();
	}
	
	public List<Instituto> listarInstitutos() {
		InstitutoService service = new InstitutoService(getSessionFactory());
		return service.listar();
	}
	
	public List<Categoria> listarCategorias() {
		CategoriaService service = new CategoriaService(getSessionFactory());
		return service.listar();
	}
	
	public List<Evento> listarPorInstituto(int codigoInstituto) {
		EventoService service = new EventoService(getSessionFactory());
		return service.listarPorInstituto(codigoInstituto);
	}

	public List<Evento> listarPorCategoria(int codigoCategoria) {
		EventoService service = new EventoService(getSessionFactory());
		return service.listarPorCategoria(codigoCategoria);
	}

	public List<Evento> listarPorRegional(int codigoRegional) {
		EventoService service = new EventoService(getSessionFactory());
		return service.listarPorRegional(codigoRegional);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public TelaEventoConsole getTela() {
		return tela;
	}

	public void setTela(TelaEventoConsole tela) {
		this.tela = tela;
	}
}
