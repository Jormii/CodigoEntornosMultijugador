package EM_Tema5.tema5;

import java.util.List;
import java.util.ArrayList;

public class Instructor {

	public static final int SOLDADOS_A_INSTRUIR = 10;

	private final String NOMBRE;
	private CampoEntrenamiento campo;

	public Instructor(String nombre, CampoEntrenamiento _campo) {
		NOMBRE = nombre;
		campo = _campo;
	}

	public void exec() {
		new Thread(() -> algoritmo()).start();
	}

	private void algoritmo() {
		while (true) {
			List<Soldado> soldadosSeleccionados = seleccionarSoldados();
			entrenarSoldados(soldadosSeleccionados);
		}
	}

	private List<Soldado> seleccionarSoldados() {
		List<Soldado> soldadosSeleccionados = new ArrayList<>(SOLDADOS_A_INSTRUIR);

		for (int i = 0; i < SOLDADOS_A_INSTRUIR; ++i) {
			soldadosSeleccionados.add(campo.obtenerSoldado());
		}

		System.out.format("El instructor %s ha seleccionado a los siguientes soldados: %s\n", NOMBRE,
				soldadosSeleccionados);

		return soldadosSeleccionados;
	}

	private void entrenarSoldados(List<Soldado> soldados) {
		for (Soldado s : soldados) {
			s.entrenar();
			if (s.estaEntrenado()) {
				System.out.format("El instructor %s ha terminado el entrenamiento del soldado %s\n", NOMBRE, s);
				campo.anadirSoldadoEntrenado(s);
			} else {
				campo.anadirSoldadoFormacion(s);
			}
		}
	}

	// Getters, setters y toString
	public String getNombre() {
		return NOMBRE;
	}

}
