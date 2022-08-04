package javafestas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafestas.model.domain.Local;

public class FXMLCadastrosLocalDialogController implements Initializable {

    @FXML
    private Label labelLocalNome;
    @FXML
    private Label labelLocalUF;
    @FXML
    private Label labelLocalCidade;
    @FXML
    private Label labelLocalCEP;
    @FXML
    private TextField textFieldLocalNome;
    @FXML
    private TextField textFieldLocalUF;
    @FXML
    private TextField textFieldLocalCidade;
    @FXML
    private TextField textFieldLocalCEP;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Local local;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
        this.textFieldLocalNome.setText(local.getNome());
        this.textFieldLocalUF.setText(local.getUf());
        this.textFieldLocalCidade.setText(local.getCidade());
        this.textFieldLocalCEP.setText(local.getCep());
    }

    @FXML
    public void handleButtonConfirmar() {

        if (validarEntradaDeDados()) {

            local.setNome(textFieldLocalNome.getText());
            local.setUf(textFieldLocalUF.getText());
            local.setCidade(textFieldLocalCidade.getText());
            local.setCep(textFieldLocalCEP.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }

    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldLocalNome.getText() == null || textFieldLocalNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldLocalUF.getText() == null || textFieldLocalUF.getText().length() == 0) {
            errorMessage += "UF inválido!\n";
        }
        if (textFieldLocalCidade.getText() == null || textFieldLocalCidade.getText().length() == 0) {
            errorMessage += "Cidade inválida!\n";
        }
        if (textFieldLocalCEP.getText() == null || textFieldLocalCEP.getText().length() == 0) {
            errorMessage += "CEP inválido!\n";
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
