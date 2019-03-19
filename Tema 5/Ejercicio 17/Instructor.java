import java.util.List;
import java.util.ArrayList;

public class Instructor {

	// Atributos de clase
	public static final int RECLUTAS_A_INSTRUIR = 10;

	// Atributos de objeto
	private final String _NOMBRE;
	private Barracon _barraconReclutas;
	private Barracon _barraconSoldados;

	// Constructores
	public Instructor(Barracon barraconReclutas, Barracon barraconSoldados) {
		_NOMBRE = Soldado.generarNombre();
		_barraconReclutas = barraconReclutas;
		_barraconSoldados = barraconSoldados;
	}

	// Metodos de objeto
	public void exec() {
		new Thread(() -> algoritmo()).start();
	}

	private void algoritmo() {
		while (true) {
			List<Soldado> reclutasSeleccionados = seleccionarReclutas();
			entrenarReclutas(reclutasSeleccionados);
		}
	}

	private List<Soldado> seleccionarReclutas() {
		List<Soldado> reclutasSeleccionados = new ArrayList<>(RECLUTAS_A_INSTRUIR);

		for (int i = 0; i < RECLUTAS_A_INSTRUIR; ++i) {
			reclutasSeleccionados.add(_barraconReclutas.obtenerSoldado());
		}

		System.out.format("El instructor %s ha seleccionado a los siguientes reclutas: %s\n", _NOMBRE,
				reclutasSeleccionados);

		return reclutasSeleccionados;
	}

	private void entrenarReclutas(List<Soldado> reclutas) {
		for (Soldado recluta : reclutas) {
			recluta.entrenar();
			if (recluta.estaEntrenado()) {
				System.out.format("El instructor %s ha terminado el entrenamiento del recluta %s\n", _NOMBRE, recluta);
				_barraconSoldados.anadirSoldado(recluta);
			} else {
				_barraconReclutas.anadirSoldado(recluta);
			}
		}
	}

	// Getters, setters, toString(), hashCode() y equals()
	public String getNombre() {
		return _NOMBRE;
	}

	@Override
	public String toString() {
		return String.format("[I - %s", _NOMBRE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_NOMBRE == null) ? 0 : _NOMBRE.hashCode());
		result = prime * result + ((_barraconReclutas == null) ? 0 : _barraconReclutas.hashCode());
		result = prime * result + ((_barraconSoldados == null) ? 0 : _barraconSoldados.hashCode());
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
		Instructor other = (Instructor) obj;
		if (_NOMBRE == null) {
			if (other._NOMBRE != null)
				return false;
		} else if (!_NOMBRE.equals(other._NOMBRE))
			return false;
		if (_barraconReclutas == null) {
			if (other._barraconReclutas != null)
				return false;
		} else if (!_barraconReclutas.equals(other._barraconReclutas))
			return false;
		if (_barraconSoldados == null) {
			if (other._barraconSoldados != null)
				return false;
		} else if (!_barraconSoldados.equals(other._barraconSoldados))
			return false;
		return true;
	}

}