/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package apresentacao;

import java.math.BigDecimal;
import java.util.UUID;
import negocio.*;
import persistencia.*;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) {
        //save
        Produto produto = new Produto("Borracha", BigDecimal.valueOf(2), "descricao da borracha");
        ProdutoDao produtoDao = new ProdutoDao();
        System.out.println(produtoDao.salvarProduto(produto));

        //atualizar
//        Produto produto = new Produto(UUID.fromString("4e8f4b5b-8569-4a62-985d-419954999f05"), "Borracha Mercur", BigDecimal.valueOf(5), "nova descricao da borracha");
//        ProdutoDao produtoDao = new ProdutoDao();       
//        System.out.println( produtoDao.atualizarProduto(produto));
//get
//        Produto produto = new Produto();
//       ProdutoDao produtoDao = new ProdutoDao();       
//       produto = produtoDao.getProduto(UUID.fromString("4e8f4b5b-8569-4a62-985d-419954999f05"));
//       System.out.println( produto.toString());
//        
//delete       
//       ProdutoDao produtoDao = new ProdutoDao();       
//       String resultado =  produtoDao.deletarProduto(UUID.fromString("4e8f4b5b-8569-4a62-985d-419954999f05"));
//       System.out.println( resultado);
    }
}
