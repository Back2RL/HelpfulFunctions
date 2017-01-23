package SortingBySelection.logic;

import java.io.File;

/**
 * Created by leonard on 22.01.17.
 */
public class Controller {
	private static Controller ourInstance = new Controller();

	public static Controller getInstance() {
		return ourInstance;
	}

	private Controller() {
	}

	public boolean startAnalyizingDirectory(final File directory) {
		return AnalysisManager.getInstance().analyzeDir(directory);
	}

	public static void main(String args[]){
		Controller.getInstance().startAnalyizingDirectory(new File("/home"));

		try {
			Thread.sleep(3000		);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AnalysisManager.getInstance().pauseAnalysis();
		try {
			Thread.sleep(3000		);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(AnalysisManager.getInstance().continueAnalysis());
		try {
			Thread.sleep(6000		);
		} catch (InterruptedException e) {
		}
		while(AnalysisManager.getInstance().isAnalyzing()){
			try {
				Thread.sleep(1000		);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(AnalysisManager.getInstance().getFiles().size());
		}

	}

}
