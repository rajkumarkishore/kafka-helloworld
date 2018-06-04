package kafka.helloworld;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerThread extends Thread {
	
	private String topicName;
    private String groupId;
    private KafkaConsumer<String,String> kafkaConsumer;
    
    private static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";
	private static final String KEY_DESERIALIZER_CLASS_CONFIG = "org.apache.kafka.common.serialization.StringDeserializer";
	private static final String VALUE_DESERIALIZER_CLASS_CONFIG = "org.apache.kafka.common.serialization.StringDeserializer";
	private static final String CLIENT_ID_CONFIG = "simple";
	
    public ConsumerThread(String topicName, String groupId){
        this.topicName = topicName;
        this.groupId = groupId;
    }

	@Override
	public void run() {
		
		Properties configProperties = new Properties();
	    configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
	    configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KEY_DESERIALIZER_CLASS_CONFIG);
	    configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS_CONFIG);
	    configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	    configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, CLIENT_ID_CONFIG);
	    
	    
	    //Figure out where to start processing messages from
        kafkaConsumer = new KafkaConsumer<String, String>(configProperties);
        kafkaConsumer.subscribe(Arrays.asList(topicName));
        
      //Start processing messages
        while(true) {
        	ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
        	
        	for(ConsumerRecord<String, String> record: records) {
        		System.out.println(record.value());
        	}
        }
	}

	public KafkaConsumer<String, String> getKafkaConsumer() {
		return kafkaConsumer;
	}

}
