
package javafestas.runnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class RunnableCliente extends Thread{
    
    private Label labelTES;
    
    public RunnableCliente (Label labelTES){
        this.labelTES = labelTES;
    }
        
    @Override
    public void run() {
        while(true){
            try (Socket socket = new Socket("34.125.13.172", 12345)) {
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                int clientes =(int)entrada.readObject();
                Platform.runLater(() -> this.labelTES.setText("Clientes: " + clientes));
                System.out.println("Cliente : " + clientes);
                entrada.close();
                socket.close();
            }
            catch (IOException ex) {
                System.out.println("Nao deu");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RunnableCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            sleep1();
        }
        
    }
    public void sleep1() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {

        }
    }

}
