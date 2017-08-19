#include <iostream>
#include <thread>
#include <mutex>

#define BIG 1000000000L


long cnt = 0;

void task(std::mutex* mutex) {
	mutex->lock();
	for(long i = 0; i < BIG; ++i){
		++cnt;
	}
	printf("%ld\n", cnt);
	printf("This is from a Thread\n");
	mutex->unlock();
}
void task2(std::mutex* mutex) {
	mutex->lock();
	for(long i = 0; i < BIG; ++i){
		--cnt;
	}
	printf("%ld\n", cnt);
	printf("This is from a Thread\n");
	mutex->unlock();
}

int main() {
	std::mutex mutex;
	std::thread myThread(task, &mutex);
	std::thread myThread2(task2, &mutex);

	std::cout << "Hello, World!" << std::endl;
	if(myThread.joinable()) {
		myThread.join();
	}
	if(myThread2.joinable()) {
		myThread2.join();
	}
	return 0;
}