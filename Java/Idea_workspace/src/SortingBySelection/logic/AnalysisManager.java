package SortingBySelection.logic;

import java.io.File;
import java.util.List;

/**
 * Created by leonard on 22.01.17.
 */
public class AnalysisManager {
	private AnalysisWorker analysisWorker;

	private static AnalysisManager ourInstance = new AnalysisManager();

	public static AnalysisManager getInstance() {
		return ourInstance;
	}

	private AnalysisManager() {
	}

	public boolean analyzeDir(final File directory) {
		if (isAnalyzing()) {
			System.err.println("AnalysisManager: can't start. AnalysisManager is still running. Abort it to start a new Task.");
			return false;
		}
		analysisWorker =new AnalysisWorker(directory);
		analysisWorker.setThread(new Thread(analysisWorker));
		analysisWorker.getThread().start();
		return isAnalyzing();
	}

	public boolean isAnalyzing() {
		return analysisWorker != null && (analysisWorker.isAnalyzing());
	}

	public void abortAnalyzing() {
		if (isAnalyzing()) {
			analysisWorker.abort();
		}
	}

	public void pauseAnalysis() {
		if (isAnalyzing()) {
			analysisWorker.pause();
		}
	}

	public boolean continueAnalysis() {
			return analysisWorker != null && analysisWorker.isAnalyzing() && analysisWorker.continueAnalysis();
	}

	public List<File> getFiles(){
		return analysisWorker != null ? analysisWorker.getFiles() : null;
	}


}
