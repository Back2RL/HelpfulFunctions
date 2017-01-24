package SortingBySelection.logic;

import java.io.File;

/**
 * Created by leonard on 22.01.17.
 */
public class Controller{
	private static Controller ourInstance = new Controller();

	public static Controller getInstance() {
		return ourInstance;
	}

	private Controller() {
	}

	public boolean startAnalyzingDirectory(final File directory) {
		return AnalysisManager.getInstance().analyzeDir(directory);
	}

	public static int getFileCount(){
		return AnalysisManager.getInstance().getFiles().size();
	}

	public static void main(String args[]){
		Controller.getInstance().startAnalyzingDirectory(new File("/home"));
		AnalysisManager.getInstance().pauseAnalysis();
		System.out.println(AnalysisManager.getInstance().continueAnalysis());
		while(AnalysisManager.getInstance().isAnalyzing()){
			try {
				Thread.sleep(500		);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getFileCount()+" "+AnalysisManager.getInstance().hasFinished());
		}

		System.out.println(getFileCount()+" "+AnalysisManager.getInstance().hasFinished());
	}
}
