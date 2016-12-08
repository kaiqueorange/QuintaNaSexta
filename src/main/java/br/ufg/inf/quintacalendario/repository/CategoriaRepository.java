package br.ufg.inf.quintacalendario.repository;

import br.ufg.inf.quintacalendario.model.Categoria;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaRepository extends AbstractRepository<Categoria> {

    public CategoriaRepository(Session session) {
        super(session);
    }

    @Override
    public List<Categoria> listarPorDescricao(String descricao) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from categoria t where lower(t.nome) like :descricao");

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("descricao", "%" + descricao.toLowerCase() + "%");

        List<Categoria> categorias = select(jpql.toString(), parametros);
        return categorias;
    }
}
