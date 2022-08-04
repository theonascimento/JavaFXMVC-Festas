package javafestas.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafestas.model.dao.ItemDeOrcamentoDAO;
import javafestas.model.dao.ProdutoDAO;
import javafestas.model.dao.OrcamentoDAO;
import javafestas.model.database.Database;
import javafestas.model.database.DatabaseFactory;
import javafestas.model.domain.ItemDeOrcamento;
import javafestas.model.domain.Produto;
import javafestas.model.domain.Orcamento;

public class FXMLProcessosOrcamentoController implements Initializable {

    @FXML
    private TableView<Orcamento> tableViewOrcamento;
    @FXML
    private TableColumn<Orcamento, Integer> tableColumnOrcamentoCodigo;
    @FXML
    private TableColumn<Orcamento, Orcamento> tableColumnOrcamentoCliente;
    @FXML
    private TableColumn<Orcamento, Orcamento> tableColumnOrcamentoLocal;
    @FXML
    private TableColumn<Orcamento, Date> tableColumnOrcamentoData;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelOrcamentoCodigo;
    @FXML
    private Label labelOrcamentoCliente;
    @FXML
    private Label labelOrcamentoLocal;
    @FXML
    private Label labelOrcamentoData;
    @FXML
    private Label labelOrcamentoValor;
    @FXML
    private Label labelOrcamentoPago;

    private List<Orcamento> listOrcamento;
    private ObservableList<Orcamento> observableListOrcamento;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final OrcamentoDAO orcamentoDAO = new OrcamentoDAO();
    private final ItemDeOrcamentoDAO itemDeOrcamentoDAO = new ItemDeOrcamentoDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orcamentoDAO.setConnection(connection);

        carregarTableViewOrcamento();

        // Limpando a exibição dos detalhes do Orçamento
        selecionarItemTableViewOrcamento(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewOrcamento.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewOrcamento(newValue));
    }

    public void carregarTableViewOrcamento() {
        tableColumnOrcamentoCodigo.setCellValueFactory(new PropertyValueFactory<>("cdOrcamento"));
        tableColumnOrcamentoCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tableColumnOrcamentoLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
        tableColumnOrcamentoData.setCellValueFactory(new PropertyValueFactory<>("data"));

        listOrcamento = orcamentoDAO.listar();

        observableListOrcamento = FXCollections.observableArrayList(listOrcamento);
        tableViewOrcamento.setItems(observableListOrcamento);
    }

    public void selecionarItemTableViewOrcamento(Orcamento orcamento) {
        if (orcamento != null) {
            labelOrcamentoCodigo.setText(String.valueOf(orcamento.getCdOrcamento()));
            labelOrcamentoCliente.setText(orcamento.getCliente().toString());
            labelOrcamentoLocal.setText(orcamento.getLocal().toString());
            labelOrcamentoData.setText(String.valueOf(orcamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelOrcamentoValor.setText(String.format("%.2f", orcamento.getValor()));
            labelOrcamentoPago.setText(String.valueOf(orcamento.getPago()));
            
        } else {
            labelOrcamentoCodigo.setText("");
            labelOrcamentoCliente.setText("");
            labelOrcamentoLocal.setText("");
            labelOrcamentoData.setText("");
            labelOrcamentoValor.setText("");
            labelOrcamentoPago.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Orcamento orcamento = new Orcamento();
        List<ItemDeOrcamento> listItensDeOrcamento = new ArrayList<>();
        orcamento.setItensDeOrcamento(listItensDeOrcamento);
        boolean buttonConfirmarClicked = showFXMLProcessosOrcamentoDialog(orcamento);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                orcamentoDAO.setConnection(connection);
                orcamentoDAO.inserir(orcamento);
                itemDeOrcamentoDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                for (ItemDeOrcamento listItemDeOrcamento : orcamento.getItensDeOrcamento()) {
                    Produto produto = listItemDeOrcamento.getProduto();
                    listItemDeOrcamento.setOrcamento(orcamentoDAO.buscarUltimoOrcamento());
                    itemDeOrcamentoDAO.inserir(listItemDeOrcamento);
                    produto.setQuantidade(produto.getQuantidade() - listItemDeOrcamento.getQuantidade());
                    produtoDAO.alterar(produto);
                }
                connection.commit();
                carregarTableViewOrcamento();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLProcessosOrcamentoController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLProcessosOrcamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Orcamento orcamento = this.tableViewOrcamento.getSelectionModel().getSelectedItem();
        if (orcamento != null) {
            boolean buttonConfirmarClicked = showFXMLProcessosOrcamentoDialog(orcamento);
            if (buttonConfirmarClicked) {
                try {
                    connection.setAutoCommit(false);
                    orcamentoDAO.setConnection(connection);
                    itemDeOrcamentoDAO.setConnection(connection);
                    produtoDAO.setConnection(connection);
                    
                    // Alterar o orcamento devido a alteração do valor
                    orcamentoDAO.alterar(orcamento); 
                    
                    //Remover todos os itens de orçamento anteriormente associados a orçamento em questão
                    List<ItemDeOrcamento> listItensDeOrcamentoRemover = itemDeOrcamentoDAO.listarPorOrcamento(orcamento);
                    for (ItemDeOrcamento itemDeOrcamentoRemover : listItensDeOrcamentoRemover) {
                        itemDeOrcamentoDAO.remover(itemDeOrcamentoRemover);
                        
                        Produto produto = itemDeOrcamentoRemover.getProduto();
                        produto.setQuantidade(produto.getQuantidade() + itemDeOrcamentoRemover.getQuantidade());
                        produtoDAO.alterar(produto);
                    }
                    
                    //Inserindo os itens de orcamento atualizados do orçamento em questão                    
                    for (ItemDeOrcamento listItemDeOrcamento : orcamento.getItensDeOrcamento()) {
                        Produto produto = listItemDeOrcamento.getProduto();
                        produto = produtoDAO.buscar(produto); // Para considerar a quantidade atualizada (após remoção dos itens pré-existentes)
                        listItemDeOrcamento.setOrcamento(orcamento);
                        itemDeOrcamentoDAO.inserir(listItemDeOrcamento);
                        produto.setQuantidade(produto.getQuantidade() - listItemDeOrcamento.getQuantidade());
                        produtoDAO.alterar(produto);
                    }
                    connection.commit();
                    carregarTableViewOrcamento();
                } catch (SQLException ex) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(FXMLProcessosOrcamentoController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(FXMLProcessosOrcamentoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        Orcamento orcamento = tableViewOrcamento.getSelectionModel().getSelectedItem();
        if (orcamento != null) {
            connection.setAutoCommit(false);
            orcamentoDAO.setConnection(connection);
            itemDeOrcamentoDAO.setConnection(connection);
            produtoDAO.setConnection(connection);
            for (ItemDeOrcamento listItemDeOrcamento : orcamento.getItensDeOrcamento()) {
                Produto produto = listItemDeOrcamento.getProduto();
                produto.setQuantidade(produto.getQuantidade() + listItemDeOrcamento.getQuantidade());
                produtoDAO.alterar(produto);
                itemDeOrcamentoDAO.remover(listItemDeOrcamento);
            }
            orcamentoDAO.remover(orcamento);
            connection.commit();
            carregarTableViewOrcamento();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Orçamento na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLProcessosOrcamentoDialog(Orcamento orcamento) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLProcessosOrcamentoDialogController.class.getResource("/javafestas/view/FXMLProcessosOrcamentoDialog.fxml"));
        
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Orçamentos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        
        // Setando o Orçamento no Controller.
        FXMLProcessosOrcamentoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setOrcamento(orcamento);
        

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
}
