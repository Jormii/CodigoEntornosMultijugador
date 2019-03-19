import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Ejercicio19 {

	private static final int N_HILOS = 10;
	private static final int MAX_SLEEP = 500;

	private String execAlgoritmo() {
		try {
			Thread.sleep((long) (Math.random() * MAX_SLEEP));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return (Math.random() < 0.5) ? correcto() : fallido();
	}

	private String correcto() {
		return String.format("Tarea %s correcta", Thread.currentThread().getName());
	}

	private String fallido() {
		throw new RuntimeException("Tarea %s fallida");
	}

	public void exec() {
		ExecutorService executor = Executors.newFixedThreadPool(N_HILOS);
		CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

		Callable<String> task = () -> execAlgoritmo();

		for (int i = 0; i < N_HILOS; ++i) {
			completionService.submit(task);
		}

		for (int i = 0; i < N_HILOS; ++i) {
			try {
				Future<String> f = completionService.take();
				System.out.format("%s\n", f.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Ejercicio19().exec();
	}

}
