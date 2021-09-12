package br.com.zitrus.zitrus.controller;

import br.com.zitrus.zitrus.model.EntradaProduto;
import br.com.zitrus.zitrus.model.Produto;
import br.com.zitrus.zitrus.repository.EntradaProdutoRepository;
import br.com.zitrus.zitrus.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/api/entradaproduto")
public class EntradaProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EntradaProdutoRepository entradaProdutoRepository;

    @PutMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Produto entradaProduto(@PathVariable Long idProduto, @RequestBody @Valid EntradaProduto entradaProduto) {
        Produto produto = produtoRepository.findById(idProduto).get();
        produto.setQtestoque(produto.getQtestoque() + entradaProduto.getQtEntrada());

        entradaProduto.setProduto(produto);
        entradaProduto.setDataMovimentacao(new Date());
        entradaProduto.setTipoMovimentacao("ENTRADA");

        entradaProdutoRepository.save(entradaProduto);
        return produtoRepository.save(produto);
    }

}
