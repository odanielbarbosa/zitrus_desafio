package br.com.zitrus.zitrus.repository;

import br.com.zitrus.zitrus.model.Produto;
import br.com.zitrus.zitrus.model.SaidaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaidaProdutoRepository extends JpaRepository<SaidaProduto, Long> {

    List<SaidaProduto> findSaidaProdutosByProduto_TipoProduto(String tipoProduto);

    List<SaidaProduto> findSaidaProdutosByProduto(Produto produto);
}
