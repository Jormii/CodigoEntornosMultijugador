public class Reclutador {

	// Atributos de clase
	private static final double SLEEP_RECLUTAR_MS = 3000;
	
	// Atributos de objeto
	private final String _NOMBRE;
	private Barracon _barraconReclutas;

	// Constructores
	public Reclutador(Barracon barraconReclutas) {
		_NOMBRE = Soldado.generarNombre();
		_barraconReclutas = barraconReclutas;
	}

	// Metodos de objeto
	public void exec() {
		new Thread(() -> reclutar()).start();
	}

	private void reclutar() {
		while (true) {
			try {
				Thread.sleep((long) (Math.random() * SLEEP_RECLUTAR_MS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Soldado soldadoReclutado = new Soldado();
			_barraconReclutas.anadirSoldado(soldadoReclutado);
			System.out.format("El reclutador %s ha reclutado al soldado %s\n", _NOMBRE, soldadoReclutado.getNombre());
		}
	}

	// Getters, setters, toString(), hashCode() y equals()
	public String getNombre() {
		return _NOMBRE;
	}

	@Override
	public String toString() {
		return String.format("[R - %s", _NOMBRE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_NOMBRE == null) ? 0 : _NOMBRE.hashCode());
		result = prime * result + ((_barraconReclutas == null) ? 0 : _barraconReclutas.hashCode());
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
		Reclutador other = (Reclutador) obj;
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
		return true;
	}

}