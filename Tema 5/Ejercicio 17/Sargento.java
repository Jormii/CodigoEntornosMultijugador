import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Iterator;

public class Sargento {

	// Atributos de clase
	private static final int SOLDADOS_POR_PELOTON = 10;
	private static final int UMBRAL_RECLUTAMIENTO = 8;
	private static final double SLEEP_MISION_MS = 2000;
	private static final double SLEEP_DESCANSAR_MS = 3500;
	private static Semaphore semPartirMision = new Semaphore(0);
	private static final int PERMISOS = 2; // Para que partan en mision la mitad de los pelotones, redondeado a la baja

	// Atributos de objeto
	private final String _NOMBRE;
	private Barracon _barraconSoldados;
	private List<Soldado> _peloton;

	// Constructores
	public Sargento(Barracon barraconSoldados) {
		_NOMBRE = Soldado.generarNombre();
		_barraconSoldados = barraconSoldados;
		_peloton = new ArrayList<>(SOLDADOS_POR_PELOTON);
		semPartirMision.release(); // Anade un permiso al semaforo, indicando que hay un nuevo peloton
	}

	// Metodos de objeto
	public void exec() {
		new Thread(() -> algoritmo()).start();
	}

	private void algoritmo() {
		while (true) {
			if (pelotonEstaListo()) {
				realizarMision();
				descansar();
			} else {
				reponerPeloton();
			}
		}
	}

	private boolean pelotonEstaListo() {
		return _peloton.size() > UMBRAL_RECLUTAMIENTO;
	}

	private void realizarMision() {
		semPartirMision.acquireUninterruptibly(PERMISOS);
		System.out.format("El peloton del sargento %s parte de mision. Peloton: %s\n", _NOMBRE, _peloton);

		try {
			Thread.sleep((long) (Math.random() * SLEEP_MISION_MS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Iterator<Soldado> it = _peloton.iterator(); it.hasNext();) {
			Soldado s = it.next();
			if (s.haMuerto()) {
				it.remove();
				System.out.format("El soldado %s del peloton del sargento %s ha muerto\n", s.getNombre(), _NOMBRE);
			}
		}

		System.out.format("El peloton del sargento %s ha regresado de la mision\n", _NOMBRE);
		semPartirMision.release(PERMISOS);
	}

	private void descansar() {
		try {
			Thread.sleep((long) (Math.random() * SLEEP_DESCANSAR_MS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void reponerPeloton() {
		int soldadosAReclutar = SOLDADOS_POR_PELOTON - _peloton.size();
		for (int i = 0; i < soldadosAReclutar; ++i) {
			_peloton.add(reclutarSoldado());
		}
	}
	
	private Soldado reclutarSoldado() {
		return _barraconSoldados.obtenerSoldado();
	}

	// Getters, setters, toString(), hashCode() y equals()
	public String getNombre() {
		return _NOMBRE;
	}

	@Override
	public String toString() {
		return String.format("[S - %s. Peloton: %s", _NOMBRE, _peloton);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_NOMBRE == null) ? 0 : _NOMBRE.hashCode());
		result = prime * result + ((_barraconSoldados == null) ? 0 : _barraconSoldados.hashCode());
		result = prime * result + ((_peloton == null) ? 0 : _peloton.hashCode());
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
		Sargento other = (Sargento) obj;
		if (_NOMBRE == null) {
			if (other._NOMBRE != null)
				return false;
		} else if (!_NOMBRE.equals(other._NOMBRE))
			return false;
		if (_barraconSoldados == null) {
			if (other._barraconSoldados != null)
				return false;
		} else if (!_barraconSoldados.equals(other._barraconSoldados))
			return false;
		if (_peloton == null) {
			if (other._peloton != null)
				return false;
		} else if (!_peloton.equals(other._peloton))
			return false;
		return true;
	}

}