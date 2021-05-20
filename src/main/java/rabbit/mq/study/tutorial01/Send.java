package rabbit.mq.study.tutorial01;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Docker起動コマンドは以下
 * docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
 */
public class Send {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = createConnectionFactory();
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			declareQueue(channel);
			publishMessage(channel, "Hello World!");
		}
	}

	private static void publishMessage(Channel channel, String message) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
		System.out.println(" [x] Sent '" + message + "'");
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
