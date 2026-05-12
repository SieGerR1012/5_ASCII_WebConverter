import Image.TextGraphicsConverter;
import Image.TextGraphicsConverterImpl;
import Server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextGraphicsConverterImpl();

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем сервер
    }
}
