//
// Created by leonard on 30.11.16.
//

#include "FileManager.h"

void FileManager::printFileToConsole(const string &path) {
	ifstream inputFileStream; // file input stream
	inputFileStream.open(path, ios::binary | ios::in);

	if (inputFileStream.is_open()) {
		string line;
		while (getline(inputFileStream, line)) {
			cout << line << '\n';
		}

		// check for error flags
		if (!inputFileStream.good()) {
			if (inputFileStream.bad()) {
				// read/write error occurred e.g. eof reached
				cout << "Flag: bad" << endl;
			}
			if (inputFileStream.fail()) {
				// in case an alphanumerical/format error occurred
				cout << "Flag: fail" << endl;
			}
			if(inputFileStream.eof()){
				cout << "Flag: eof" << endl;
			}
		}

		inputFileStream.clear(); // reset state flags

		streampos start;
		streampos end;

		inputFileStream.seekg(0, ios::beg);
		start = inputFileStream.tellg();

		inputFileStream.seekg(0, ios::end);
		end = inputFileStream.tellg();

		int size = (int) end;

		cout << "Size = " << (end-start) << " Bytes." << endl;
		cout << "Size = " << end << " Bytes." << endl;
		cout << "Size = " << size << " Bytes." << endl;

		inputFileStream.close();
	} else {
		cout << "Unable to open inputFileStream";
	}


}
