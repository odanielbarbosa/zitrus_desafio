package br.com.zitrus.zitrus.repository;

import br.com.zitrus.zitrus.model.EntradaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaProdutoRepository extends JpaRepository<EntradaProduto, Long> {
}
