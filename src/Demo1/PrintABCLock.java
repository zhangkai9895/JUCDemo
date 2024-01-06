package Demo1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 三个线程分别打印 A，B，C，要求这三个线程一起运行，打印 n 次，输出形如“ABCABCABC....”的字符串
 * 使用Lock和 Condition精准唤醒，同时要注意虚假唤醒
 */

public class PrintABCLock {

    public static void main(String[] args) {
        Task task = new Task(10);
        new Thread(()->{
            task.PrintA();
            System.out.println();
        }).start();
        new Thread(()->{
            task.PrintB();
        }).start();
        new Thread(()->{
            task.PrintC();
        }).start();
    }
    
}

class  Task{
    private  int times;//执行任务的次数
    private  Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    private int taskID =1;
    public Task(int times) {
        this.times = times;
    }

    //打印A
    public void PrintA(){
        for (int i=0;i<times;i++){
            lock.lock();
            try {
                while (taskID != 1){
                    condition1.await();
                }
                System.out.println("A");
                taskID = 2;
                condition2.signal();
            }catch (Exception e){
               e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

    }
    //打印B
    public void PrintB(){
        for (int i=0;i<times;i++){
            lock.lock();
            try {
                while (taskID != 2){
                    condition2.await();
                }
                System.out.println("B");
                taskID = 3;
                condition3.signal();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    //打印C
    public void PrintC(){
        for (int i=0;i<times;i++){
            lock.lock();
            try {
                while (taskID != 3){
                    condition3.await();
                }
                System.out.println("C");
                taskID = 1;
                condition1.signal();
            }catch (Exception e){
               e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }


}
