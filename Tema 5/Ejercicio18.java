import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejercicio18 {

	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private AtomicInteger numTareas = new AtomicInteger();
	private ConcurrentMap<String, String> duplicates = new ConcurrentHashMap<>();
	private Object lock = new Object();

	public void println(String string) {
		System.out.println(Thread.currentThread().getName() + ": " + string);
	}

	public void findDuplicates(File root) {
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				if (file.isDirectory()) {
					Runnable task = () -> findDuplicates(file);
					executor.execute(task);
					numTareas.incrementAndGet();
				} else {
					String path = duplicates.putIfAbsent(file.getName(), file.getAbsolutePath());
					if (path != null) {
						synchronized (lock) {
							println("Found duplicate file: " + file.getName());
							println(" " + path);
							println(" " + file.getAbsolutePath());
						}
					}
				}
			}
		}
		
		if (numTareas.decrementAndGet() == 0) {
			executor.shutdown();
		}
	}

	public void exec() {
		File homeDir = new File(System.getProperty("user.home"));
		Runnable task = () -> findDuplicates(homeDir);
		executor.execute(task);
		numTareas.incrementAndGet();
	}

	public static void main(String[] args) {
		new Ejercicio18().exec();
	}
}