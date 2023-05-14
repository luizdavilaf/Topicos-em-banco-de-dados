/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package apresentacao;
import negocio.*;
import persistencia.*;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Contato luiz = new Contato();
        luiz.setName("Luiz");
        Endereco novoEndereco;
        novoEndereco = new Endereco("Rio Grande", "avenida", "cassino", 0, "");
        luiz.setEndereco(novoEndereco);
        ContatoDAO contatoDao = new ContatoDAO();
        contatoDao.create(luiz);
        
    }
}
