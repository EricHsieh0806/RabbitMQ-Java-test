package rabbittest;
        import com.rabbitmq.client.Channel;
        import com.rabbitmq.client.Connection;
        import com.rabbitmq.client.ConnectionFactory;
        import java.io.IOException;
        import java.util.Scanner;

public class Producer {
    private static final String QUEUE_NAME = "Your rabbitmq queue name";

    public void start(int loop_count){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("Your rabbitmq server host IP");
        factory.setUsername("Your access queue account");
        factory.setPassword("Your access password");
        Connection connection = null;
        Channel channel = null;
        Scanner sc = new Scanner(System.in);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            int i = loop_count; // Setting control variable
            while (i > 0) {
                // Loop
                String message = "Your queue message content";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                i--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No args, Please input a number for message loop count!");
            return;
        }
        Producer p = new Producer();
        p.start(Integer.parseInt(args[0]));
    }


}