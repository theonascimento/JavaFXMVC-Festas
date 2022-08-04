package javafestas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosCliente;

    @FXML
    private MenuItem menuItemCadastrosLocal;

    @FXML
    private MenuItem menuItemCadastrosProduto;

    @FXML
    private MenuItem menuItemProcessosOrcamento;

    @FXML
    private MenuItem menuItemGraficosOrcamentoPorMes;

    @FXML
    private MenuItem menuItemRelatoriosQuantiaArrecadadaPorLocal;
    
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    public void handleMenuItemCadastrosClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafestas/view/FXMLCadastrosCliente.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCadastrosLocal() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafestas/view/FXMLCadastrosLocal.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemProcessosOrcamento() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafestas/view/FXMLProcessosOrcamento.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemGraficoOrcamento() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafestas/view/FXMLGraficoOrcamento.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemRelatorios() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafestas/view/FXMLRelatorios.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemNClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafestas/view/FXMLThreadSockets.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
