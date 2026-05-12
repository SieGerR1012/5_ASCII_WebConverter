import Image.TextGraphicsConverter;
import Server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = null;

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем сервер
    }
}