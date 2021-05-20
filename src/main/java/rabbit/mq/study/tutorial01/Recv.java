package rabbit.mq.study.tutorial01;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = createConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		declareQueue(channel);
		
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
		};
		
		// 第2引数でAutoAck=trueに設定している
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
		});
	}

	private static void declareQueue(Channel channel) throws IOException {
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}

	private static ConnectionFactory createConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		return factory;
	}
}
