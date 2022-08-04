package javafestas.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafestas.model.domain.ItemDeOrcamento;
import javafestas.model.domain.Produto;
import javafestas.model.domain.Orcamento;

public class ItemDeOrcamentoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ItemDeOrcamento itemDeVenda) {
        String sql = "INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getQuantidade());
            stmt.setDouble(2, itemDeVenda.getValor());
            stmt.setInt(3, itemDeVenda.getProduto().getCdProduto());
            stmt.setInt(4, itemDeVenda.getOrcamento().getCdOrcamento());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeOrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ItemDeOrcamento itemDeOrcamento) {
        return true;
    }

    public boolean remover(ItemDeOrcamento itemDeOrcamento) {
        String sql = "DELETE FROM itensdeorcamento WHERE cdItemDeOrcamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeOrcamento.getCdItemDeOrcamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeOrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ItemDeOrcamento> listar() {
        String sql = "SELECT * FROM itensdeorcamento";
        List<ItemDeOrcamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeOrcamento itemDeOrcamento = new ItemDeOrcamento();
                Produto produto = new Produto();
                Orcamento orcamento = new Orcamento();
                itemDeOrcamento.setCdItemDeOrcamento(resultado.getInt("cdItemDeOrcamento"));
                itemDeOrcamento.setQuantidade(resultado.getInt("quantidade"));
                itemDeOrcamento.setValor(resultado.getDouble("valor"));
                
                produto.setCdProduto(resultado.getInt("cdProduto"));
                orcamento.setCdOrcamento(resultado.getInt("cdOrcamento"));
                
                //Obtendo os dados completos do Produto associado ao Item de Venda
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                OrcamentoDAO orcamentoDAO = new OrcamentoDAO();
                orcamentoDAO.setConnection(connection);
                orcamento = orcamentoDAO.buscar(orcamento);
                
                itemDeOrcamento.setProduto(produto);
                itemDeOrcamento.setOrcamento(orcamento);
                
                retorno.add(itemDeOrcamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeOrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ItemDeOrcamento> listarPorOrcamento(Orcamento orcamento) {
        String sql = "SELECT * FROM itensdeorcamento WHERE cdOrcamento=?";
        List<ItemDeOrcamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orcamento.getCdOrcamento());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeOrcamento itemDeOrcamento = new ItemDeOrcamento();
                Produto produto = new Produto();
                Orcamento v = new Orcamento();
                itemDeOrcamento.setCdItemDeOrcamento(resultado.getInt("cdItemDeOrcamento"));
                itemDeOrcamento.setQuantidade(resultado.getInt("quantidade"));
                itemDeOrcamento.setValor(resultado.getDouble("valor"));
                
                produto.setCdProduto(resultado.getInt("cdProduto"));
                v.setCdOrcamento(resultado.getInt("cdOrcamento"));
                
                //Obtendo os dados completos do Produto associado ao Item de Orcamento
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                itemDeOrcamento.setProduto(produto);
                itemDeOrcamento.setOrcamento(v);
                
                retorno.add(itemDeOrcamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeOrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ItemDeOrcamento buscar(ItemDeOrcamento itemDeOrcamento) {
        String sql = "SELECT * FROM itensdeorcamento WHERE cdItemDeOrcamento=?";
        ItemDeOrcamento retorno = new ItemDeOrcamento();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeOrcamento.getCdItemDeOrcamento());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Produto produto = new Produto();
                Orcamento orcamento = new Orcamento();
                itemDeOrcamento.setCdItemDeOrcamento(resultado.getInt("cdItemDeOrcamento"));
                itemDeOrcamento.setQuantidade(resultado.getInt("quantidade"));
                itemDeOrcamento.setValor(resultado.getDouble("valor"));
                
                produto.setCdProduto(resultado.getInt("cdProduto"));
                orcamento.setCdOrcamento(resultado.getInt("cdOrcamento"));
                
                //Obtendo os dados completos do Cliente associado à Venda
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                OrcamentoDAO orcamentoDAO = new OrcamentoDAO();
                orcamentoDAO.setConnection(connection);
                orcamento = orcamentoDAO.buscar(orcamento);
                
                itemDeOrcamento.setProduto(produto);
                itemDeOrcamento.setOrcamento(orcamento);
                
                retorno = itemDeOrcamento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
