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

public class Main {
    public static void main(String[] args) {
        List<String> messages = Arrays.asList("Четыре", "Пять", "Шесть");

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
        }
// Тут решил добавить исключение,которое может быть выброшено методами из dummy-connector.jar,
// если произойдет ошибка при работе с соединением, сессией, очередью или продюсером
        catch (DummyException | InterruptedException excpt) {
            excpt.printStackTrace();
        }
    }
}