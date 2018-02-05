package gumdrop.test.server;

import gumdrop.test.util.Test;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest extends Test {

  public static void main(String[] args) {
    new QueueTest().run();
  }

  @Override
  public void run() {
    Queue<String> q = new LinkedList<>();
    q.add("a");
    q.add("b");
    q.add("c");
    System.out.println("poll = [" + q.poll() + "]");
    System.out.println("poll = [" + q.poll() + "]");
    System.out.println("peek = [" + q.peek() + "]");
    System.out.println("poll = [" + q.poll() + "]");
    System.out.println("poll = [" + q.poll() + "]");
  }

}
