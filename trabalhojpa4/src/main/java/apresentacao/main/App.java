/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change telaPrincipal license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit telaPrincipal template
 */
package apresentacao.main;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {
        System.out.println("começando");
        JFrame telaPrincipal = new JFrame("Hospital ltda");

        telaPrincipal.setSize(1024, 768);        
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setResizable(false);
        telaPrincipal.setDefaultCloseOperation(EXIT_ON_CLOSE);
        telaPrincipal.setLayout(null);
        telaPrincipal.setVisible(true);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);

            }
        });

    }

}
