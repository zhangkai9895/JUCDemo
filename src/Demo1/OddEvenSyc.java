package Demo1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/***
 * 两个线程交替打印 0~100 的奇偶数
 * 使用sychronized 和wait进行线程通信
 */
public class OddEvenSyc {


}

class PrintOddEven{
    private Lock lock = new ReentrantLock();

}
