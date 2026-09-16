package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public final class DialogUtil {

    private DialogUtil() {
    }

    public static void showError(
            String mensagem
    ) {

        Alert alert =
                new Alert(AlertType.ERROR);

        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    public static void showInfo(
            String mensagem
    ) {

        Alert alert =
                new Alert(AlertType.INFORMATION);

        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    public static boolean showConfirmation(
            String titulo,
            String mensagem
    ) {

        ButtonType btnConfirmar =
                new ButtonType(
                        "Confirmar",
                        ButtonBar.ButtonData.OK_DONE
                );

        ButtonType btnCancelar =
                new ButtonType(
                        "Cancelar",
                        ButtonBar.ButtonData.CANCEL_CLOSE
                );

        Alert alert =
                new Alert(
                        AlertType.CONFIRMATION,
                        mensagem,
                        btnConfirmar,
                        btnCancelar
                );

        alert.setTitle(titulo);
        alert.setHeaderText(null);

        return alert
                .showAndWait()
                .orElse(btnCancelar)
                == btnConfirmar;
    }
}