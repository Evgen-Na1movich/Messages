package org.example;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.implementation.ProducerImpl;
import ru.pflb.mq.dummy.implementation.SessionImpl;

import java.util.Arrays;
import java.util.List;

public class Task1 {
    public static void main(String[] args) throws DummyException, InterruptedException {
        List<String> messages = Arrays.asList("Четыре", "Пять", "Шесть");


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

    }
}