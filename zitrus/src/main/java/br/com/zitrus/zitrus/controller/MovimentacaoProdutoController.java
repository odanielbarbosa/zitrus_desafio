package br.com.zitrus.zitrus.controller;

import br.com.zitrus.zitrus.model.Produto;
import br.com.zitrus.zitrus.model.SaidaProduto;
import br.com.zitrus.zitrus.repository.ProdutoRepository;
import br.com.zitrus.zitrus.repository.SaidaProdutoRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movimentacaoproduto")
public class MovimentacaoProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private SaidaProdutoRepository saidaProdutoRepository;

    @GetMapping("/{tipoProduto}")
    public ResponseEntity<String> movimentacaoTipoProduto(@PathVariable String tipoProduto) {
        List<Produto> produtoList = produtoRepository.findProdutosByTipoProduto(tipoProduto);
        List<SaidaProduto> saidaProdutoList = saidaProdutoRepository.findSaidaProdutosByProduto_TipoProduto(tipoProduto);

        if (produtoList.isEmpty()) {
            return ResponseEntity.ok("Tipo de produto sem nenhum produto cadastrado.");
        }

        int qtDisponivel = produtoList.stream().map(Produto::getQtestoque).reduce(Integer::sum).get();

        int qtSaida = 0;
        if (!saidaProdutoList.isEmpty()) {
            qtSaida = saidaProdutoList.stream().map(SaidaProduto::getQtSaida).reduce(Integer::sum).get();
        }

        return ResponseEntity.ok("Tipo do Produto (" + tipoProduto + ")\n"
                + "Quantidade disponível:(" + qtDisponivel + ")\n"
                + "Quantidade de saída:(" + qtSaida + ")\n"
                + new Gson().toJson(produtoList));
    }

    @GetMapping("/lucro/{idProduto}")
    public ResponseEntity<String> qtSaidaELucroTotal(@PathVariable Long idProduto) {
        Produto produto = produtoRepository.findById(idProduto).get();
        List<SaidaProduto> saidaProdutoList = saidaProdutoRepository.findSaidaProdutosByProduto(produto);

        if (saidaProdutoList.isEmpty()) {
            return ResponseEntity.ok("Produto sem nenhuma saída registrada.");
        }

        Integer qtTotalSaida = saidaProdutoList.stream().map(SaidaProduto::getQtSaida).reduce(Integer::sum).get();
        Double vlTotalVenda = saidaProdutoList.stream().map(SaidaProduto::getVlTotalVenda).reduce(Double::sum).get();
        Double vlTotalLucro = vlTotalVenda - (qtTotalSaida * produto.getVlCusto());

        return ResponseEntity.ok("Produto(" + produto.getId() + " - " + produto.getDescricao() + ")\n"
                + "Quantidade total de saída:(" + qtTotalSaida + ")\n"
                + "Lucro total:(" + vlTotalLucro + ")");
    }
}
