package EM_Tema5.tema5;

import java.util.Random;

public class Soldado {

	private enum Nombre {
		JESUS, CARLOS, ALEJANDRO, MIGUEL, RAFAEL, PEDRO, ANGEL, PABLO, FERNANDO, SERGIO, LUIS, JORGE, ALBERTO, N_NOMBRES
	};

	private enum Apellido {
		GARCIA, GONZALEZ, RODRIGUEZ, FERNANDEZ, LOPEZ, MARTINEZ, SANCHEZ, PEREZ, GOMEZ, MARTIN, N_APELLIDOS
	};

	public static final float MAXIMO_ENTRENAMIENTO = 0.4f;	/* Maximo porcentaje que puede entrenar un soldado por sesion.
															Cuando alcanza o supera 1, se considera que ha completado el entrenamiento */
	public static final float PROBABILIDAD_MUERTE = 0.3f;

	private final String NOMBRE;
	private float progreso;

	public Soldado(String nombre) {
		NOMBRE = nombre;
		progreso = 0.0f;
	}

	public void entrenar() {
		float porcentajeEntrenado = new Random().nextFloat() * MAXIMO_ENTRENAMIENTO;
		progreso += porcentajeEntrenado;
	}
	
	public boolean estaEntrenado() {
		return progreso >= 1.0f;
	}

	public static String obtenerNombre() {
		int idx = new Random().nextInt(Nombre.N_NOMBRES.ordinal());
		return Nombre.values()[idx].toString();
	}

	public static String obtenerApellido() {
		int idx = new Random().nextInt(Apellido.N_APELLIDOS.ordinal());
		return Apellido.values()[idx].toString();
	}

	// Getters, setters y toString()
	public String getNombre() {
		return NOMBRE;
	}

	@Override
	public String toString() {
		return NOMBRE;
	}

}
