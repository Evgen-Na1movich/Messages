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
import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) throws InterruptedException, DummyException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите 1, если хотите просто вывод из файла");
        System.out.println("Введите 2, если хотите бесконечно выводить сообщения из файла: ");
        System.out.print("Ваш выбор ->  ");
        int choice = scanner.nextInt();

        Connection connection = new ConnectionImpl();
        connection.start();

        Session session = new SessionImpl();

        Destination destination = session.createDestination("myQueue");
        Producer producer = new ProducerImpl(destination);
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));

        switch (choice) {
            case 1:
                while (reader.ready()) {
                    String line = reader.readLine();
                    producer.send(line);
                    Thread.sleep(1000); // 1 sec relax
                }
                break;
            case 2:
                while (true) {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        producer.send(line);
                        Thread.sleep(1000); // 1 sec relax
                    }
                    reader = new BufferedReader(new FileReader(args[0]));
                }
                // Код никогда не выполнется тут
//                session.close();
//                connection.close();
            default:
                System.out.println("Неверный выбор, программа завершена.");
        }
        session.close();
        connection.close();
    }
}