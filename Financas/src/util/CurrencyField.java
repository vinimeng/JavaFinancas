package util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;

/**
 * Simple Currency Field for JavaFX
 *
 * @author Gustavo
 * @version 1.0
 */
public class CurrencyField extends TextField {

	private NumberFormat format;
	private BigDecimal amount;

	public CurrencyField() {
	}

	public CurrencyField(Locale locale) {
		initialize(locale, BigDecimal.ZERO);
	}

	public CurrencyField(Locale locale, BigDecimal initialAmount) {
		initialize(locale, initialAmount);
	}

	public void initialize(Locale locale, BigDecimal initialAmount) {
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		amount = initialAmount;
		format = NumberFormat.getCurrencyInstance(locale);
		setText(format.format(initialAmount));

		// Remove selection when textfield gets focus
		focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					Platform.runLater(() -> {
						int lenght = getText().length();
						selectRange(lenght, lenght);
						positionCaret(lenght);
					});
				});

		// Listen the text's changes
		textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				formatText(newValue);
			}
		});
	}

	/**
	 * Get the current amount value
	 *
	 * @return Total amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Change the current amount value
	 *
	 * @param newAmount
	 */
	public void setAmount(BigDecimal newAmount) {
		amount = newAmount;
		formatText(format.format(newAmount));
	}

	/**
	 * Set Currency format
	 *
	 * @param locale
	 */
	public void setCurrencyFormat(Locale locale) {
		format = NumberFormat.getCurrencyInstance(locale);
		formatText(format.format(getAmount()));
	}

	private void formatText(String text) {
		if (text != null && !text.isEmpty()) {
			String plainText = text.replaceAll("[^0-9]", "");

			while (plainText.length() < 3) {
				plainText = "0" + plainText;
			}

			StringBuilder builder = new StringBuilder(plainText);
			builder.insert(plainText.length() - 2, "."); //a11a //11 //011 //0.11

			BigDecimal newValue = new BigDecimal(builder.toString());
			amount = newValue;
			setText(format.format(newValue));//R$ 0,11
		}
	}

	@Override
	public void deleteText(int start, int end) {
		StringBuilder builder = new StringBuilder(getText());
		builder.delete(start, end);
		formatText(builder.toString());
		selectRange(end, end);
	}
}
