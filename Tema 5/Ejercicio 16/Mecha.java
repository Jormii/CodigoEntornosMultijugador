package EM_Tema5.tema5;

import java.util.Map;
import java.util.HashMap;

public class Mecha {

	public static enum Pieza {
		CABEZA, TORSO, BRAZOS, PIERNAS, N_PIEZAS
	};

	private final int ID;
	private final Map<Pieza, Boolean> PIEZAS_INSTALADAS;

	public Mecha(int _id) {
		ID = _id;

		int nPiezas = Pieza.N_PIEZAS.ordinal();
		PIEZAS_INSTALADAS = new HashMap<>(nPiezas);

		for (int i = 0; i < nPiezas; ++i) {
			PIEZAS_INSTALADAS.put(Pieza.values()[i], Boolean.FALSE);
		}
	}

	public void instalarPieza(Pieza pieza) {
		PIEZAS_INSTALADAS.put(pieza, Boolean.TRUE);
	}

	// Getters y setters
	public int getId() {
		return ID;
	}

	@Override
	public String toString() {
		return "Mecha [ID=" + ID + ", PIEZAS_INSTALADAS=" + PIEZAS_INSTALADAS + "]";
	}

}
