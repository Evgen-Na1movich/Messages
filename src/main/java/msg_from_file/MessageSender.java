package msg_from_file;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.implementation.ProducerImpl;
import ru.pflb.mq.dummy.implementation.SessionImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageSender {

    public static void main(String[] args) {

        List<String> messages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            Connection connection = new ConnectionImpl();
            connection.start();

            Session session = new SessionImpl();

            Destination destination = session.createDestination("myQueue");
            Producer producer = new ProducerImpl(destination);

            for (String msg : messages) {
                producer.send(msg);
                Thread.sleep(1000); // 1 sec relax
            }


            session.close();
            connection.close();
        } catch (DummyException | InterruptedException excpt) {
            excpt.printStackTrace();
        }
    }
}
