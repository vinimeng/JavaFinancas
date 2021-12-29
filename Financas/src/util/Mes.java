package util;

public enum Mes {
	JANEIRO(1), FEVEREIRO(2), MARCO(3), ABRIL(4), MAIO(5), JUNHO(6), JULHO(7), AGOSTO(8), SETEMBRO(9), OUTUBRO(10),
	NOVEMBRO(11), DEZEMBRO(12);

	private final int mes;

	Mes(int mes) {
		this.mes = mes;
	}

	public static Mes getMes(int mes) {
		switch (mes) {
		case 1:
			return Mes.JANEIRO;
		case 2:
			return Mes.FEVEREIRO;
		case 3:
			return Mes.MARCO;
		case 4:
			return Mes.ABRIL;
		case 5:
			return Mes.MAIO;
		case 6:
			return Mes.JUNHO;
		case 7:
			return Mes.JULHO;
		case 8:
			return Mes.AGOSTO;
		case 9:
			return Mes.SETEMBRO;
		case 10:
			return Mes.OUTUBRO;
		case 11:
			return Mes.NOVEMBRO;
		case 12:
			return Mes.DEZEMBRO;
		default:
			return null;
		}
	}

	public int getMes() {
		return this.mes;
	}

	@Override
	public String toString() {
		switch (mes) {
		case 1:
			return "JANEIRO";
		case 2:
			return "FEVEREIRO";
		case 3:
			return "MARÇO";
		case 4:
			return "ABRIL";
		case 5:
			return "MAIO";
		case 6:
			return "JUNHO";
		case 7:
			return "JULHO";
		case 8:
			return "AGOSTO";
		case 9:
			return "SETEMBRO";
		case 10:
			return "OUTUBRO";
		case 11:
			return "NOVEMBRO";
		case 12:
			return "DEZEMBRO";
		default:
			return "";
		}
	}
}
