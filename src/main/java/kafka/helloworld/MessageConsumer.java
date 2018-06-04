package kafka.helloworld;

import java.util.Scanner;

public class MessageConsumer {
	
	
	private static Scanner in;
	
	public static void main(String[] args) {
		
		in = new Scanner(System.in);
        String topicName = args[0];
        String groupId = args[1];
        
        ConsumerThread consumerRunnable =  new ConsumerThread(topicName, groupId);
        consumerRunnable.start();
        
        String line = "";
        while (!line.equals("exit")) {
            line = in.next();
        }
        
        consumerRunnable.getKafkaConsumer().wakeup();
        System.out.println("Stopping consumer .....");
        try {
			consumerRunnable.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
