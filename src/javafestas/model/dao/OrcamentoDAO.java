package javafestas.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafestas.model.domain.Cliente;
import javafestas.model.domain.Local;
import javafestas.model.domain.ItemDeOrcamento;
import javafestas.model.domain.Orcamento;

public class OrcamentoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Orcamento orcamento) {
        String sql = "INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(orcamento.getData()));
            stmt.setDouble(2, orcamento.getValor());
            stmt.setBoolean(3, orcamento.getPago());
            stmt.setInt(4, orcamento.getCliente().getCdCliente());
            stmt.setInt(5, orcamento.getLocal().getCdLocal());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Orcamento orcamento) {
        String sql = "UPDATE orcamentos SET data=?, valor=?, pago=?, cdCliente=?, cdLocal=? WHERE cdOrcamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(orcamento.getData()));
            stmt.setDouble(2, orcamento.getValor());
            stmt.setBoolean(3, orcamento.getPago());
            stmt.setInt(4, orcamento.getCliente().getCdCliente());
            stmt.setInt(5, orcamento.getLocal().getCdLocal());
            stmt.setInt(6, orcamento.getCdOrcamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Orcamento orcamento) {
        String sql = "DELETE FROM orcamentos WHERE cdOrcamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orcamento.getCdOrcamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Orcamento> listar() {
        String sql = "SELECT * FROM orcamentos";
        List<Orcamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Orcamento orcamento = new Orcamento();
                Cliente cliente = new Cliente();
                Local local = new Local();
                List<ItemDeOrcamento> itensDeOrcamento = new ArrayList();

                orcamento.setCdOrcamento(resultado.getInt("cdOrcamento"));
                orcamento.setData(resultado.getDate("data").toLocalDate());
                orcamento.setValor(resultado.getDouble("valor"));
                orcamento.setPago(resultado.getBoolean("pago"));
                cliente.setCdCliente(resultado.getInt("cdCliente"));
                local.setCdLocal(resultado.getInt("cdLocal"));

                //Obtendo os dados completos do Cliente associado à Venda
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);
                
                //Obtendo os dados completos do Local associado à Venda
                LocalDAO localDAO = new LocalDAO();
                localDAO.setConnection(connection);
                local = localDAO.buscar(local);

                //Obtendo os dados completos dos Itens de Orcamento associados à Venda
                ItemDeOrcamentoDAO itemDeOrcamentoDAO = new ItemDeOrcamentoDAO();
                itemDeOrcamentoDAO.setConnection(connection);
                itensDeOrcamento = itemDeOrcamentoDAO.listarPorOrcamento(orcamento);

                orcamento.setCliente(cliente);
                orcamento.setLocal(local);
                orcamento.setItensDeOrcamento(itensDeOrcamento);
                retorno.add(orcamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Orcamento buscar(Orcamento orcamento) {
        String sql = "SELECT * FROM orcamentos WHERE cdOrcamento=?";
        Orcamento retorno = new Orcamento();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orcamento.getCdOrcamento());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente cliente = new Cliente();
                Local local = new Local();
                orcamento.setCdOrcamento(resultado.getInt("cdOrcamento"));
                orcamento.setData(resultado.getDate("data").toLocalDate());
                orcamento.setValor(resultado.getDouble("valor"));
                orcamento.setPago(resultado.getBoolean("pago"));
                cliente.setCdCliente(resultado.getInt("cdCliente"));
                local.setCdLocal(resultado.getInt("cdLocal"));
                orcamento.setCliente(cliente);
                orcamento.setLocal(local);
                retorno = orcamento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Orcamento buscarUltimoOrcamento() {
        String sql = "SELECT max(cdOrcamento) FROM orcamentos";
        Orcamento retorno = new Orcamento();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setCdOrcamento(resultado.getInt("max"));
                return retorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Map<Integer, ArrayList> listarQuantidadeOrcamentosPorMes() {
        String sql = "select count(cdOrcamento), extract(year from data) as ano, extract(month from data) as mes from orcamentos group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
