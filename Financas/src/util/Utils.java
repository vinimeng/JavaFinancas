package util;

import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Utils {
	private static boolean YESNO = false;

	public static void dialogoOK(Stage stage, String titulo, String mensagem) {
		Dialog<ButtonType> dialogo = new Dialog<>();
		ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
		Stage dialogoStage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		ObservableList<Image> icons = stage.getIcons();

		if (!icons.isEmpty()) {
			Image logo = icons.get(0);
			dialogoStage.getIcons().add(logo);
		}

		dialogo.setTitle(titulo);
		dialogo.setContentText(mensagem);
		dialogo.getDialogPane().getButtonTypes().add(ok);
		dialogo.initOwner(stage);
		dialogo.showAndWait();
	}

	public static boolean dialogoYESNO(Stage stage, String titulo, String mensagem) {
		YESNO = false;

		Dialog<ButtonType> dialogo = new Dialog<>();
		ButtonType sim = new ButtonType("SIM", ButtonData.YES);
		ButtonType nao = new ButtonType("NÃO", ButtonData.NO);
		Stage dialogoStage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		ObservableList<Image> icons = stage.getIcons();

		if (!icons.isEmpty()) {
			Image logo = icons.get(0);
			dialogoStage.getIcons().add(logo);
		}

		dialogo.setTitle(titulo);
		dialogo.setContentText(mensagem);
		dialogo.getDialogPane().getButtonTypes().add(sim);
		dialogo.getDialogPane().getButtonTypes().add(nao);
		dialogo.initOwner(stage);
		dialogo.showAndWait().ifPresent(response -> {
			if (response == sim) {
				YESNO = true;
			}
		});

		return YESNO;
	}

	public static String colorParaHEXString(Color cor) {
		String formato = "#%02x%02x%02x";
		int vermelho = (int) (255 * cor.getRed());
		int verde = (int) (255 * cor.getGreen());
		int azul = (int) (255 * cor.getBlue());

		return String.format(formato, vermelho, verde, azul);
	}
}
