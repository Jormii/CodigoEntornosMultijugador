import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barracon {

	// Atributos de objeto
	private Semaphore _nSoldados;
	private List<Soldado> _soldados;

	// Constructores
	public Barracon() {
		_nSoldados = new Semaphore(0);
		_soldados = new ArrayList<>();
	}

	// Metodos de objeto
	public Soldado obtenerSoldado() {
		_nSoldados.acquireUninterruptibly();

		synchronized (_soldados) {
			int idx = new Random().nextInt(_soldados.size());
			return _soldados.remove(idx);
		}
	}

	public void anadirSoldado(Soldado s) {
		synchronized (_soldados) {
			_soldados.add(s);
		}
		_nSoldados.release(1);
	}

	// Getters, setters, toString(), hashCode() y equals()
	@Override
	public String toString() {
		return "Barracon [_nSoldados=" + _nSoldados + ", _soldados=" + _soldados + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_soldados == null) ? 0 : _soldados.hashCode());
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
		Barracon other = (Barracon) obj;
		if (_soldados == null) {
			if (other._soldados != null)
				return false;
		} else if (!_soldados.equals(other._soldados))
			return false;
		return true;
	}

}