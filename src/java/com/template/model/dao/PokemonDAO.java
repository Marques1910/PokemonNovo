package com.template.model.dao;


import com.template.model.Conexao;
import com.template.model.dto.PokemonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

import static com.template.util.DialogUtil.showError;

public class PokemonDAO {
    private static final Logger logger = Logger.getLogger(PokemonDAO.class.getName());

    public void cadastrarPokemon(PokemonDTO pokemon) {
        String sql = "INSERT INTO pokemon (nome, tipo, numero, geracao) VALUES (?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pokemon.getNome());
            ps.setString(2, pokemon.getTipo());
            ps.setInt(3, pokemon.getNumero());
            ps.setInt(4, pokemon.getGeracao());

            ps.executeUpdate();
            System.out.println("-> Pokemon " + pokemon.getNome() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            showError("Erro ao cadastrar Pokemon!");
        }
    }


    public ArrayList<PokemonDTO> listarPokemons() {
        String sql = "SELECT * FROM pokemon ORDER BY numero ASC";
        ArrayList<PokemonDTO> lista = new ArrayList<>();

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PokemonDTO p = new PokemonDTO();
                p.setNumero(rs.getInt("numero"));
                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setGeracao(rs.getInt("geracao"));

                lista.add(p);
            }

        } catch (SQLException e) {
            showError("Erro ao listar pokemon!");
        }

        return lista;
    }

    public void alterarPokemon(PokemonDTO pokemon) {
        String sql = "UPDATE pokemon SET nome = ?, tipo = ?, geracao = ? WHERE numero = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pokemon.getNome());
            ps.setString(2, pokemon.getTipo());
            ps.setInt(3, pokemon.getGeracao());
            ps.setInt(4, pokemon.getNumero());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("-> Pokemon atualizado com sucesso!");
            } else {
                System.out.println("-> Pokemon não encontrado para atualização.");
            }

        } catch (SQLException e) {
            showError("Erro ao alterar pokemon!");
        }
    }

    public void excluirPokemon(int numero) {
        String sql = "DELETE FROM pokemon WHERE numero = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("-> Pokemon removido com sucesso!");
            } else {
                System.out.println("-> Pokemon com número " + numero + " nao encontrado.");
            }

        } catch (SQLException e) {
            showError("Erro ao excluir pokemon!");
        }
    }
}