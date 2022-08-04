package javafestas.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafestas.model.dao.LocalDAO;
import javafestas.model.database.Database;
import javafestas.model.database.DatabaseFactory;
import javafestas.model.domain.Local;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLCadastrosLocalController implements Initializable {


    @FXML
    private TableView<Local> tableViewLocal;
    @FXML
    private TableColumn<Local, String> tableColumnLocalNome;
    @FXML
    private TableColumn<Local, String> tableColumnLocalCEP;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelLocalCodigo;
    @FXML
    private Label labelLocalNome;
    @FXML
    private Label labelLocalUF;
    @FXML
    private Label labelLocalCidade;
    @FXML
    private Label labelLocalCEP;

    private List<Local> listLocal;
    private ObservableList<Local> observableListLocal;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final LocalDAO localDAO = new LocalDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        localDAO.setConnection(connection);
        carregarTableViewLocal();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewLocal.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewLocal(newValue));

    }

    public void carregarTableViewLocal() {
        tableColumnLocalNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnLocalCEP.setCellValueFactory(new PropertyValueFactory<>("cep"));

        listLocal = localDAO.listar();

        observableListLocal = FXCollections.observableArrayList(listLocal);
        tableViewLocal.setItems(observableListLocal);
    }
    
    public void selecionarItemTableViewLocal(Local local){
        if (local != null) {
            labelLocalCodigo.setText(String.valueOf(local.getCdLocal()));
            labelLocalNome.setText(local.getNome());
            labelLocalUF.setText(local.getUf());
            labelLocalCidade.setText(local.getCidade());
            labelLocalCEP.setText(local.getCep());
        } else {
            labelLocalCodigo.setText("");
            labelLocalNome.setText("");
            labelLocalUF.setText("");
            labelLocalCidade.setText("");
            labelLocalCEP.setText("");
        }

    }   
    @FXML
    public void handleButtonInserir() throws IOException {
        Local local = new Local();
        boolean buttonConfirmarClicked = showFXMLCadastrosLocalDialog(local);
        if (buttonConfirmarClicked) {
            localDAO.inserir(local);
            carregarTableViewLocal();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Local local = tableViewLocal.getSelectionModel().getSelectedItem();
        if (local != null) {
            boolean buttonConfirmarClicked = showFXMLCadastrosLocalDialog(local);
            if (buttonConfirmarClicked) {
                localDAO.alterar(local);
                carregarTableViewLocal();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Local na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Local local = tableViewLocal.getSelectionModel().getSelectedItem();
        if (local != null) {
            localDAO.remover(local);
            carregarTableViewLocal();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Local na Tabela!");
            alert.show();
        }
    }  

    public boolean showFXMLCadastrosLocalDialog(Local local) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrosLocalDialogController.class.getResource("/javafestas/view/FXMLCadastrosLocalDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Locais");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o local no Controller.
        FXMLCadastrosLocalDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setLocal(local);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
}
