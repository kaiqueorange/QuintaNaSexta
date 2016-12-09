package br.ufg.inf.quintacalendario.repository;

import br.ufg.inf.quintacalendario.model.Instituto;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstitutoRepository extends AbstractRepository<Instituto> {

    public InstitutoRepository(Session session) {
        super(session);
    }

    @Override
    public List<Instituto> listarPorDescricao(String descricao) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("Select t from instituto t where lower(t.nome) like :descricao");

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("descricao", "%" + descricao.toLowerCase() + "%");

        return select(jpql.toString(), parametros);
    }

}
