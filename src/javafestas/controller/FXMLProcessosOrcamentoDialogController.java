package javafestas.controller;


import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafestas.model.dao.ClienteDAO;
import javafestas.model.dao.ProdutoDAO;
import javafestas.model.dao.LocalDAO;
import javafestas.model.database.Database;
import javafestas.model.database.DatabaseFactory;
import javafestas.model.domain.Cliente;
import javafestas.model.domain.ItemDeOrcamento;
import javafestas.model.domain.Produto;
import javafestas.model.domain.Orcamento;
import javafestas.model.domain.Local;

public class FXMLProcessosOrcamentoDialogController implements Initializable {

    @FXML
    private ComboBox comboBoxOrcamentoCliente;
    @FXML
    private ComboBox comboBoxOrcamentoLocal;
    @FXML
    private DatePicker datePickerOrcamentoData;
    @FXML
    private CheckBox checkBoxOrcamentoPago;
    @FXML
    private ComboBox comboBoxOrcamentoProduto;
    @FXML
    private TableView<ItemDeOrcamento> tableViewItensOrcamento;
    @FXML
    private TableColumn<ItemDeOrcamento, Produto> tableColumnOrcamentoProduto;
    @FXML
    private TableColumn<ItemDeOrcamento, Integer> tableColumnOrcamentoQuantidade;
    @FXML
    private TableColumn<ItemDeOrcamento, Double> tableColumnOrcamentoValor;
    @FXML
    private TextField textFieldOrcamentoQuantidade;
    @FXML
    private TextField textFieldOrcamentoValor;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonAdicionar;
    @FXML
    private Button buttonRemover;

    private List<Cliente> listClientes;
    private List<Produto> listProdutos;
    private List<Local> listLocal;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Produto> observableListProdutos;
    private ObservableList<Local> observableListLocal;
    private ObservableList<ItemDeOrcamento> observableListItensDeOrcamento;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final LocalDAO localDAO = new LocalDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Orcamento orcamento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        produtoDAO.setConnection(connection);
        localDAO.setConnection(connection);

        carregarComboBoxClientes();
        carregarComboBoxProdutos();
        carregarComboBoxLocal();

        tableColumnOrcamentoProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tableColumnOrcamentoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnOrcamentoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    public void carregarComboBoxClientes() {
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxOrcamentoCliente.setItems(observableListClientes);
    }

    public void carregarComboBoxProdutos() {
        listProdutos = produtoDAO.listar();
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        comboBoxOrcamentoProduto.setItems(observableListProdutos);
    }
    
    public void carregarComboBoxLocal() {
        listLocal = localDAO.listar();
        observableListLocal = FXCollections.observableArrayList(listLocal);
        comboBoxOrcamentoLocal.setItems(observableListLocal);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Orcamento getOrcamento() {
        return this.orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
        
        //[No caso de alteração] Deixando selecionado os dados do orçamento escolhido
        comboBoxOrcamentoCliente.getSelectionModel().select(orcamento.getCliente());
        comboBoxOrcamentoLocal.getSelectionModel().select(orcamento.getLocal());
        datePickerOrcamentoData.setValue(orcamento.getData());
        checkBoxOrcamentoPago.setSelected(orcamento.getPago());
        observableListItensDeOrcamento = FXCollections.observableArrayList(orcamento.getItensDeOrcamento());
        tableViewItensOrcamento.setItems(observableListItensDeOrcamento);
        textFieldOrcamentoValor.setText(String.format("%.2f", orcamento.getValor()));
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonAdicionar() {
        Produto produto;
        ItemDeOrcamento itemDeOrcamento = new ItemDeOrcamento();

        if (comboBoxOrcamentoProduto.getSelectionModel().getSelectedItem() != null) {
            produto = (Produto) comboBoxOrcamentoProduto.getSelectionModel().getSelectedItem();

            if (produto.getQuantidade() >= Integer.parseInt(textFieldOrcamentoQuantidade.getText())) {
                itemDeOrcamento.setProduto((Produto) comboBoxOrcamentoProduto.getSelectionModel().getSelectedItem());
                itemDeOrcamento.setQuantidade(Integer.parseInt(textFieldOrcamentoQuantidade.getText()));
                itemDeOrcamento.setValor(itemDeOrcamento.getProduto().getPreco() * itemDeOrcamento.getQuantidade());

                orcamento.getItensDeOrcamento().add(itemDeOrcamento);
                orcamento.setValor(orcamento.getValor() + itemDeOrcamento.getValor());

                observableListItensDeOrcamento = FXCollections.observableArrayList(orcamento.getItensDeOrcamento());
                tableViewItensOrcamento.setItems(observableListItensDeOrcamento);

                textFieldOrcamentoValor.setText(String.format("%.2f", orcamento.getValor()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Problemas na escolha do produto!");
                alert.setContentText("Não existe a quantidade de produtos disponíveis no estoque!");
                alert.show();
            }
        }
    }

    @FXML
    public void handleButtonRemover() {
        ItemDeOrcamento itemDeOrcamento;

        if (tableViewItensOrcamento.getSelectionModel().getSelectedItem() != null) {
            itemDeOrcamento = (ItemDeOrcamento) tableViewItensOrcamento.getSelectionModel().getSelectedItem();

            orcamento.getItensDeOrcamento().remove(itemDeOrcamento);
            orcamento.setValor(orcamento.getValor() - itemDeOrcamento.getValor());

            observableListItensDeOrcamento = FXCollections.observableArrayList(orcamento.getItensDeOrcamento());
            tableViewItensOrcamento.setItems(observableListItensDeOrcamento);

            textFieldOrcamentoValor.setText(String.format("%.2f", orcamento.getValor()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Problemas na escolha do item de orcamento!");
            alert.setContentText("Você não selecionou um item de orcamento da tabela!");
            alert.show();
        }

    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            orcamento.setCliente((Cliente) comboBoxOrcamentoCliente.getSelectionModel().getSelectedItem());
            orcamento.setLocal((Local) comboBoxOrcamentoLocal.getSelectionModel().getSelectedItem());
            orcamento.setPago(checkBoxOrcamentoPago.isSelected());
            orcamento.setData(datePickerOrcamentoData.getValue());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxOrcamentoCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (comboBoxOrcamentoLocal.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Local inválido!\n";
        }
        if (datePickerOrcamentoData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (observableListItensDeOrcamento == null) {
            errorMessage += "Itens de Orçamento inválidos!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
