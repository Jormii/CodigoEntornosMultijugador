public class Ejercicio17 {

	// Atributos de objeto
	private int _nSargentos;
	private int _nReclutadores;
	private int _nInstructores;
	
	// Constructores
	public Ejercicio17(int nSargentos, int nReclutadores, int nInstructores) {
		_nSargentos = nSargentos;
		_nReclutadores = nReclutadores;
		_nInstructores = nInstructores;
	}

	// Metodos de objeto
	public void exec() {
		Barracon barraconReclutas = new Barracon();
		Barracon barraconSoldados = new Barracon();

		for (int i = 0; i < _nSargentos; ++i) {
			new Sargento(barraconSoldados).exec();
		}
		
		for (int i = 0; i < _nReclutadores; ++i) {
			new Reclutador(barraconReclutas).exec();;
		}
		
		for (int i = 0; i < _nInstructores; ++i) {
			new Instructor(barraconReclutas, barraconSoldados).exec();
		}
	}

	// Main
	public static void main(String[] args) {
		int nSargentos = 2;
		int nReclutadores = 2;
		int nInstructores = 4;
		
		new Ejercicio17(nSargentos, nReclutadores, nInstructores).exec();
	}

}
