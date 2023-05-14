/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;
import java.util.ArrayList;
import org.bson.types.ObjectId;


/**
 *
 * @author luizd
 */
public class Contato {
      private ObjectId id;
    private String name;
    private ArrayList<String> telefones;
    private Endereco endereco;

    public Contato() {
        this.telefones = new ArrayList();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(ArrayList<String> telefones) {
        this.telefones = telefones;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Contato{" + "id=" + id + ", name=" + name + ", telefones=" + telefones + ", endereco=" + endereco + '}';
    }


    
}
