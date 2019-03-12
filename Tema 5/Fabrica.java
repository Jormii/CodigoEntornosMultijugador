package EM_Tema5.tema5;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Fabrica {

	public static final int TAMANO_ALMACEN = 20;
	private static Map<Integer, Fabrica> fabricasActivas = new HashMap<>();

	private final int ID;
	private Mecha[] mechas;
	private Robot[] robots;
	private Maquina[] maquinas;
	private Map<Mecha.Pieza, BlockingQueue<Mecha.Pieza>> almacen;
	private Map<Mecha.Pieza, Integer> piezasInstaladas;

	public Fabrica(int _id, int nMechas) {
		ID = _id;
		almacen = new HashMap<>(Mecha.Pieza.N_PIEZAS.ordinal());
		piezasInstaladas = new ConcurrentHashMap<>(Mecha.Pieza.N_PIEZAS.ordinal());
		fabricasActivas.put(ID, this);

		mechas = new Mecha[nMechas];
		robots = new Robot[nMechas];
		for (int i = 0; i < nMechas; ++i) {
			mechas[i] = new Mecha(i);
			robots[i] = new Robot(ID, mechas[i]);
		}

		int nMaquinas = Mecha.Pieza.N_PIEZAS.ordinal();
		maquinas = new Maquina[nMaquinas];
		for (int i = 0; i < nMaquinas; ++i) {
			maquinas[i] = new Maquina(ID, Mecha.Pieza.values()[i]);
			almacen.put(Mecha.Pieza.values()[i], new ArrayBlockingQueue<>(TAMANO_ALMACEN));
		}
	}

	public void exec() {
		for (Robot r : robots) {
			r.exec();
		}

		for (Maquina m : maquinas) {
			m.exec();
		}
	}

	public void almacenarPieza(Mecha.Pieza pieza) {
		BlockingQueue<Mecha.Pieza> almacenPieza = almacen.get(pieza);

		try {
			almacenPieza.put(pieza);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Mecha.Pieza recogerPieza(Mecha.Pieza pieza) {
		Mecha.Pieza p = null;
		try {
			p = almacen.get(pieza).take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Integer nInstalaciones = piezasInstaladas.putIfAbsent(pieza, 1);
		if (nInstalaciones != null) {
			piezasInstaladas.replace(pieza, ++nInstalaciones);
			if (piezasInstaladas.get(pieza) == mechas.length) {
				pararFabrica(pieza);
			}
		}
		return p;
	}

	public void pararFabrica(Mecha.Pieza piezaResponsable) {
		maquinas[piezaResponsable.ordinal()].detenerMaquina();
	}

	public static Fabrica obtenerFabricaPorId(int id) {
		return fabricasActivas.get(id);
	}

}
