/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package apresentacao;

import static com.rometools.rome.feed.atom.Content.XML;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import negocio.Artigo;
import negocio.Inscricao;
import org.xml.sax.InputSource;
import persistencia.InscricaoDAO;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) throws IllegalArgumentException, FeedException {
        System.out.println("começando");
        JFrame telaPrincipal = new JFrame("Leitor de Feeds");

        telaPrincipal.setSize(1024, 768);
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setResizable(false);
        telaPrincipal.setDefaultCloseOperation(EXIT_ON_CLOSE);
        telaPrincipal.setLayout(null);
        telaPrincipal.setVisible(true);

        new MenuInscricao().setVisible(true);


    }
}
