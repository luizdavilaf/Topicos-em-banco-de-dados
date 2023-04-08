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
        
        
        
//        Inscricao inscricao = new Inscricao();
//        inscricao.setUrl("https://diolinux.com.br/feed");
//        inscricao.setNome("IFRS");
//        
//        
//        SyndFeedInput input = new SyndFeedInput();
//        SyndFeed feed = input.build(new InputSource(inscricao.getUrl()));
//        Iterator itr = feed.getEntries().iterator();
//        while (itr.hasNext()) {
//            Artigo artigo = new Artigo();
//            SyndEntry syndEntry = (SyndEntry) itr.next();
//            artigo.setAutor(syndEntry.getAuthor());
//            artigo.setLink(syndEntry.getLink());
//            List<SyndContent> conteudos = syndEntry.getContents();
//            String result = "";
//            for(SyndContent conteudo: conteudos){                
//               result = result + conteudo.getValue();              
//            }
//            String conteudoFinal = result.replaceAll("\\n", "");
//            conteudoFinal = conteudoFinal.replaceAll("<[^>]*>", "");
//            artigo.setConteudo(conteudoFinal);
//            artigo.setTitulo(syndEntry.getTitle());
//            artigo.setData(syndEntry.getPublishedDate());
//            inscricao.getArtigos().add(artigo);   
//        }
//        InscricaoDAO inscricaoDAO = new InscricaoDAO();
//        inscricaoDAO.adicionar(inscricao);
//        
//       System.out.println(inscricao.getArtigos().get(0).toString());
//    }
}
