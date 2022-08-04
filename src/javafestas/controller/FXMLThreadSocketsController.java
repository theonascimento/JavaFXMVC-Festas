/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafestas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafestas.runnable.RunnableCliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author theo
 */
public class FXMLThreadSocketsController implements Initializable {

    @FXML
    private Label labelTES;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RunnableCliente runnableCliente = new RunnableCliente(labelTES);
        Thread t1 = new Thread(runnableCliente);
        t1.start();
    }    
    
}
