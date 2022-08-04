package javafestas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafestas.model.domain.Local;

public class LocalDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Local local) {
        String sql = "INSERT INTO locais(nome, uf, cidade, cep) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getUf());
            stmt.setString(3, local.getCidade());
            stmt.setString(4, local.getCep());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Local local) {
        String sql = "UPDATE locais SET nome=?, uf=?, cidade=?, cep=? WHERE cdLocal=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getUf());
            stmt.setString(3, local.getCidade());
            stmt.setString(4, local.getCep());
            stmt.setInt(5, local.getCdLocal());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Local local) {
        String sql = "DELETE FROM locais WHERE cdLocal=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, local.getCdLocal());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Local> listar() {
        String sql = "SELECT * FROM locais";
        List<Local> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Local local = new Local();
                local.setCdLocal(resultado.getInt("cdLocal"));
                local.setNome(resultado.getString("nome"));
                local.setUf(resultado.getString("uf"));
                local.setCidade(resultado.getString("cidade"));
                local.setCep(resultado.getString("cep"));
                retorno.add(local);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Local buscar(Local local) {
        String sql = "SELECT * FROM locais WHERE cdLocal=?";
        Local retorno = new Local();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, local.getCdLocal());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                local.setNome(resultado.getString("nome"));
                local.setUf(resultado.getString("uf"));
                local.setCidade(resultado.getString("cidade"));
                local.setCep(resultado.getString("cep"));
                retorno = local;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
