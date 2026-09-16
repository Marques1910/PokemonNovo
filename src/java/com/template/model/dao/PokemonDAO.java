package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.PokemonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PokemonDAO {

    private static final Logger LOGGER =
            Logger.getLogger(
                    PokemonDAO.class.getName()
            );

    public void cadastrarPokemon(
            PokemonDTO pokemon
    ) {

        String sql =
                "INSERT INTO pokemon "
                        + "(nome, tipo, numero, geracao) "
                        + "VALUES (?, ?, ?, ?)";

        try (
                Connection conn =
                        new Conexao().conectar();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
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

            ps.executeUpdate();

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Erro ao cadastrar Pokémon.",
                    e
            );

            /*
             * PostgreSQL:
             * 23505 = violação de chave única.
             */
            if ("23505".equals(
                    e.getSQLState()
            )) {

                throw new RuntimeException(
                        "Já existe um Pokémon com esse número.",
                        e
                );
            }

            throw new RuntimeException(
                    "Não foi possível cadastrar o Pokémon.",
                    e
            );
        }
    }

    public ArrayList<PokemonDTO> listarPokemons() {

        String sql =
                "SELECT numero, nome, tipo, geracao "
                        + "FROM pokemon "
                        + "ORDER BY numero ASC";

        ArrayList<PokemonDTO> lista =
                new ArrayList<>();

        try (
                Connection conn =
                        new Conexao().conectar();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
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

            return lista;

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Erro ao listar Pokémon.",
                    e
            );

            throw new RuntimeException(
                    "Não foi possível carregar os Pokémon.",
                    e
            );
        }
    }

    public void alterarPokemon(
            PokemonDTO pokemon,
            int numeroOriginal
    ) {

        /*
         * O número é o identificador do Pokémon.
         * Ele não é alterado durante uma edição.
         */
        String sql =
                "UPDATE pokemon "
                        + "SET nome = ?, "
                        + "tipo = ?, "
                        + "geracao = ? "
                        + "WHERE numero = ?";

        try (
                Connection conn =
                        new Conexao().conectar();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
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
                    pokemon.getGeracao()
            );

            ps.setInt(
                    4,
                    numeroOriginal
            );

            int linhasAfetadas =
                    ps.executeUpdate();

            if (linhasAfetadas == 0) {

                throw new RuntimeException(
                        "Pokémon não encontrado para atualização."
                );
            }

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Erro ao atualizar Pokémon.",
                    e
            );

            throw new RuntimeException(
                    "Não foi possível atualizar o Pokémon.",
                    e
            );
        }
    }

    public void excluirPokemon(
            int numero
    ) {

        String sql =
                "DELETE FROM pokemon "
                        + "WHERE numero = ?";

        try (
                Connection conn =
                        new Conexao().conectar();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    numero
            );

            int linhasAfetadas =
                    ps.executeUpdate();

            if (linhasAfetadas == 0) {

                throw new RuntimeException(
                        "Pokémon não encontrado para exclusão."
                );
            }

        } catch (SQLException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Erro ao excluir Pokémon.",
                    e
            );

            throw new RuntimeException(
                    "Não foi possível excluir o Pokémon.",
                    e
            );
        }
    }
}