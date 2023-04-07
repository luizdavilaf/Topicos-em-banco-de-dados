/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package apresentacao;

import static com.rometools.rome.feed.atom.Content.XML;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import java.util.Iterator;
import org.xml.sax.InputSource;

/**
 *
 * @author luizd
 */
public class Main {

    public static void main(String[] args) throws IllegalArgumentException, FeedException {
        String url = "https://ifrs.edu.br/rss";
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new InputSource(url));
        Iterator itr = feed.getEntries().iterator();
        while (itr.hasNext()) {
            SyndEntry syndEntry = (SyndEntry) itr.next();
            System.out.println(syndEntry.getAuthor());
            
        }
    }
}
