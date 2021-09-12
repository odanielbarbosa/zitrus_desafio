package br.com.zitrus.zitrus.repository;

import br.com.zitrus.zitrus.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findProdutosByTipoProduto(String tipoProduto);
}
