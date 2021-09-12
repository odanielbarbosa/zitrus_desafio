package br.com.zitrus.zitrus.controller;

import br.com.zitrus.zitrus.model.Produto;
import br.com.zitrus.zitrus.model.SaidaProduto;
import br.com.zitrus.zitrus.repository.ProdutoRepository;
import br.com.zitrus.zitrus.repository.SaidaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/saidaproduto")
public class SaidaProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private SaidaProdutoRepository saidaProdutoRepository;

    @PutMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> saidaProduto(@PathVariable Long idProduto, @RequestBody @Valid SaidaProduto saidaProduto) {
        Produto produto = produtoRepository.findById(idProduto).get();

        if (saidaProduto.getQtSaida() > produto.getQtestoque()) {
            return ResponseEntity.ok("Quantidade do produto em estoque é menor que a quantidade solicitada para saida.");
        } else if (produto.getVlCusto() > saidaProduto.getVlUnitarioProduto()) {
            return ResponseEntity.ok("O valor de venda deve ser maior que o de custo.");
        }
        produto.setQtestoque(produto.getQtestoque() - saidaProduto.getQtSaida());

        saidaProduto.setVlTotalVenda(saidaProduto.getQtSaida() * saidaProduto.getVlUnitarioProduto());
        saidaProduto.setDataMovimentacao(new Date());
        saidaProduto.setTipoMovimentacao("SAIDA");
        saidaProduto.setProduto(produto);

        saidaProdutoRepository.save(saidaProduto);
        produtoRepository.save(produto);

        return ResponseEntity.ok("Produto: (" + produto.getDescricao() + "), " +
                "quantidade de saida(" + saidaProduto.getQtSaida() + ") " +
                "realizada com sucesso!!");
    }
}
