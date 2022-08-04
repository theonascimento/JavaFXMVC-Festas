package javafestas.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafestas.model.dao.OrcamentoDAO;
import javafestas.model.database.Database;
import javafestas.model.database.DatabaseFactory;
import javafestas.model.domain.Orcamento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLRelatoriosController implements Initializable {

    @FXML
    private TableView<Orcamento> tableViewOrca ;
    @FXML
    private TableColumn<Orcamento, Integer> tableColumnCodOrca;
    @FXML
    private TableColumn<Orcamento, Double> tableColumnValo;
    @FXML
    private TableColumn<Orcamento, Date> tableColumnDat;
    @FXML
    private TableColumn<Orcamento, Boolean> tableColumnPag;
    @FXML
    private Button buttonImprimir;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final OrcamentoDAO orcamentoDAO = new OrcamentoDAO();
    
    private List<Orcamento> listOrcamento;
    private ObservableList<Orcamento> observableListOrcamento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orcamentoDAO.setConnection(connection);
        
        carregarTableViewOrcamento();
               
    }    
    
    public void carregarTableViewOrcamento() {
        tableColumnCodOrca.setCellValueFactory(new PropertyValueFactory<>("cdOrcamento"));
        tableColumnValo.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tableColumnDat.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnPag.setCellValueFactory(new PropertyValueFactory<>("pago"));
        
        listOrcamento = orcamentoDAO.listar();

        observableListOrcamento = FXCollections.observableArrayList(listOrcamento);
        tableViewOrca.setItems(observableListOrcamento);
    }
 
   
    public void handleImprimir() throws JRException{

        URL url = getClass().getResource("/javafestas/relatorios/JAVAFXRelatoriosValorTotal.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }
    
}