package kafka.helloworld;

import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public class MessageProducer {

	private static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";
	private static final String KEY_SERIALIZER_CLASS_CONFIG = "org.apache.kafka.common.serialization.ByteArraySerializer";
	private static final String VALUE_SERIALIZER_CLASS_CONFIG = "org.apache.kafka.common.serialization.StringSerializer";

	private static Scanner in;

	public static void main(String[] args) {

		if(args.length < 1) {
			System.err.println("Please specify 1 parameter!");
			System.exit(-1);
		}
		
		String topic = args[0];
		in = new Scanner(System.in);
		System.out.println("Enter a message (type exit to quit)");

		Properties prop = new Properties();
		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KEY_SERIALIZER_CLASS_CONFIG);
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG);

		Producer<String, String> producer = new KafkaProducer<String, String>(prop);
		String line = in.nextLine();
		while (!line.equalsIgnoreCase("exit")) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, line);
			producer.send(record);
			line = in.nextLine();
		}

		in.close();
		producer.close();
	}

}
