#include <iostream>
#include <thread>
#include <mutex>
#include <future>
#include <vector>

#define BIG 1000000L

std::mutex stdio_mutex;
long cnt = 0;

inline void printThreadID() {
    std::lock_guard<std::mutex> lockGuard(stdio_mutex);
    std::cout << "My Thread-ID is " << std::this_thread::get_id() << std::endl;
}

void task(std::mutex *mutex) {
    std::this_thread::yield();
    printThreadID();

    for (long i = 0; i < BIG; ++i) {
        std::lock_guard<std::mutex> lockGuard(*mutex);
        ++cnt;
    }

    std::lock_guard<std::mutex> lockGuard(stdio_mutex);
    std::cout << cnt << std::endl;
    std::cout << "This is from a Thread\n";
}

void task2(std::mutex *mutex) {
    std::this_thread::yield();
    printThreadID();

    for (long i = 0; i < BIG; ++i) {
        std::lock_guard<std::mutex> lockGuard(*mutex);
        --cnt;
    }

    std::lock_guard<std::mutex> lockGuard(stdio_mutex);
    std::cout << cnt << std::endl;
    std::cout << "This is from a Thread\n";
}

void defaultThread() {
    std::mutex mutex;

    const unsigned maxThreads = std::thread::hardware_concurrency();
    std::cout << "Available Threads on this System: " << maxThreads << std::endl;

    if (maxThreads > 0) {
        std::vector<std::thread> myThreads;
        for (unsigned i = 0; i < maxThreads; i += 2) {
            myThreads.emplace_back(task, &mutex);
            myThreads.emplace_back(task2, &mutex);
        }
        for (auto &thread: myThreads) {
            if (thread.joinable()) {
                thread.join();
            }
        }
    }
}

// a non-optimized way of checking for prime numbers:
bool is_prime(int x) {
    printThreadID();

    stdio_mutex.lock();
    std::cout << "Calculating. Please, wait...\n";
    stdio_mutex.unlock();

    for (int i = 2; i < x; ++i) {
        if (x % i == 0) {
            return false;
        }
    }

    std::lock_guard<std::mutex> lockGuard(stdio_mutex);
    std::cout << "The async. Task has finished\n";
    return true;
}

std::string myAsyncTask() {
    printThreadID();
    std::this_thread::sleep_for(std::chrono::milliseconds(5000));
    return "The async. Task has finished\n";
}

void defaultAsync() {
    // call is_prime(313222313) asynchronously:
    std::future<bool> fut = std::async(is_prime, 313222313);

    stdio_mutex.lock();
    std::cout << "Checking whether 313222313 is prime.\n";
    std::cout << "checking, please wait\n";
    stdio_mutex.unlock();

    std::chrono::milliseconds span(10);
    while (fut.wait_for(span) == std::future_status::timeout) {
        std::lock_guard<std::mutex> lock_guard(stdio_mutex);
        std::cout << '.';
    }

    bool ret = fut.get();      // waits for is_prime to return

    if (ret) std::cout << "It is prime!\n";
    else std::cout << "It is not prime.\n";


    std::cout << std::async(myAsyncTask).get();
    // same result:
    std::future<std::string> myFuture = std::async(myAsyncTask);
    std::cout << myFuture.get();
}

int main() {
    printThreadID();
    defaultThread();
    defaultAsync();
    return 0;
}