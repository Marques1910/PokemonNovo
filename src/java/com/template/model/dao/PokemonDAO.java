package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.PokemonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.template.util.DialogUtil.showError;

public class PokemonDAO {

    // CADASTRAR
    public void cadastrarPokemon(PokemonDTO pokemon) {

        String sql =
                "INSERT INTO pokemon (nome, tipo, numero, geracao) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, pokemon.getNome());
            ps.setString(2, pokemon.getTipo());
            ps.setInt(3, pokemon.getNumero());
            ps.setInt(4, pokemon.getGeracao());

            ps.executeUpdate();

            System.out.println(
                    "Pokemon " + pokemon.getNome()
                            + " cadastrado com sucesso!"
            );

        } catch (SQLException e) {

            showError(
                    "Erro ao cadastrar Pokemon: "
                            + e.getMessage()
            );
        }
    }


    // LISTAR
    public ArrayList<PokemonDTO> listarPokemons() {

        String sql =
                "SELECT * FROM pokemon ORDER BY numero ASC";

        ArrayList<PokemonDTO> lista =
                new ArrayList<>();

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                PokemonDTO pokemon =
                        new PokemonDTO();

                pokemon.setNumero(
                        rs.getInt("numero")
                );

                pokemon.setNome(
                        rs.getString("nome")
                );

                pokemon.setTipo(
                        rs.getString("tipo")
                );

                pokemon.setGeracao(
                        rs.getInt("geracao")
                );

                lista.add(pokemon);
            }

        } catch (SQLException e) {

            showError(
                    "Erro ao listar Pokemon: "
                            + e.getMessage()
            );
        }

        return lista;
    }


    // ALTERAR
    public void alterarPokemon(
            PokemonDTO pokemon,
            int numeroOriginal
    ) {

        String sql =
                "UPDATE pokemon " +
                        "SET nome = ?, tipo = ?, numero = ?, geracao = ? " +
                        "WHERE numero = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    pokemon.getNome()
            );

            ps.setString(
                    2,
                    pokemon.getTipo()
            );

            ps.setInt(
                    3,
                    pokemon.getNumero()
            );

            ps.setInt(
                    4,
                    pokemon.getGeracao()
            );

            // Número que o Pokemon tinha antes da edição
            ps.setInt(
                    5,
                    numeroOriginal
            );

            int linhasAfetadas =
                    ps.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println(
                        "Pokemon atualizado com sucesso!"
                );

            } else {

                System.out.println(
                        "Pokemon nao encontrado para atualizacao."
                );
            }

        } catch (SQLException e) {

            showError(
                    "Erro ao alterar Pokemon: "
                            + e.getMessage()
            );
        }
    }


    // EXCLUIR
    public void excluirPokemon(int numero) {

        String sql =
                "DELETE FROM pokemon WHERE numero = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    numero
            );

            int linhasAfetadas =
                    ps.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println(
                        "Pokemon removido com sucesso!"
                );

            } else {

                System.out.println(
                        "Pokemon de numero "
                                + numero
                                + " nao encontrado."
                );
            }

        } catch (SQLException e) {

            showError(
                    "Erro ao excluir Pokemon: "
                            + e.getMessage()
            );
        }
    }
}