package rabbit.mq.study.tutorial02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = createConnectionFactory();
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

			String[] messages = { "hoge", "fuga", "piyo" };
			String message = String.join(" ", messages);

			publishMessage(channel, message);
			System.out.println(" [x] Sent '" + message + "'");
		}
	}

	private static ConnectionFactory createConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		return factory;
	}

	private static void publishMessage(Channel channel, String message) throws IOException {
		channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes(StandardCharsets.UTF_8));
		System.out.println(" [x] Sent '" + message + "'");
	}
}