import java.util.Random;

public class Soldado {

	// Atributos de clase
	private static final double MAXIMO_ENTRENAMIENTO = 0.4;
	private static final double PROBABILIDAD_MUERTE = 0.3;
	private static final double UMBRAL_ENTRENADO = 1.0;
	
	private static enum Nombre {
		JESUS, CARLOS, ALEJANDRO, MIGUEL, RAFAEL, PEDRO, ANGEL, PABLO, FERNANDO, SERGIO, LUIS, JORGE, ALBERTO, N_NOMBRES
	};

	private static enum Apellido {
		GARCIA, GONZALEZ, RODRIGUEZ, FERNANDEZ, LOPEZ, MARTINEZ, SANCHEZ, PEREZ, GOMEZ, MARTIN, N_APELLIDOS
	};

	// Atributos de objeto
	private final String _NOMBRE;
	private double _progreso;

	// Constructores
	public Soldado() {
		_NOMBRE = generarNombre();
		_progreso = 0.0;
	}

	// Metodos de clase
	public static String generarNombre() {
		return String.format("%s %s", obtenerNombre(), obtenerApellido());
	}
	
	private static String obtenerNombre() {
		int idx = new Random().nextInt(Nombre.N_NOMBRES.ordinal());
		return Nombre.values()[idx].toString();
	}

	private static String obtenerApellido() {
		int idx = new Random().nextInt(Apellido.N_APELLIDOS.ordinal());
		return Apellido.values()[idx].toString();
	}
	
	// Metodos de objeto
	public void entrenar() {
		double porcentajeEntrenado = Math.random() * MAXIMO_ENTRENAMIENTO;
		_progreso += porcentajeEntrenado;
	}

	public boolean haMuerto() {
		return Math.random() < PROBABILIDAD_MUERTE;
	}

	public boolean estaEntrenado() {
		return _progreso >= UMBRAL_ENTRENADO;
	}

	// Getters, setters, toString(), hashCode() y equals()
	public String getNombre() {
		return _NOMBRE;
	}
	
	public double getProgreso() {
		return _progreso;
	}

	@Override
	public String toString() {
		return String.format("[S - %s: %f.2]", _NOMBRE, _progreso);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_NOMBRE == null) ? 0 : _NOMBRE.hashCode());
		long temp;
		temp = Double.doubleToLongBits(_progreso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Soldado other = (Soldado) obj;
		if (_NOMBRE == null) {
			if (other._NOMBRE != null)
				return false;
		} else if (!_NOMBRE.equals(other._NOMBRE))
			return false;
		if (Double.doubleToLongBits(_progreso) != Double.doubleToLongBits(other._progreso))
			return false;
		return true;
	}
	
}