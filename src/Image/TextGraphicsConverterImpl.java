package Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterImpl implements TextGraphicsConverter {

    // Реализация методов конвертации и настройки конвертера
    private int maxWidth = 736;
    private int maxHeight = 736;
    private double maxRatio = 2.0;
    private TextColorSchema schema = new SimpleTextColorSchema();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException{
        BufferedImage img = ImageIO.read(new URL(url)); // Загрузка изображения по URL

        // Получаем новые размеры
        int width = img.getWidth();
        int height = img.getHeight();

        // Проверка соотношения сторон
        double ratio = (double) width / height;
        if (ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }

        // Вычисление масштаба
        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH); // Уменьшение картинки
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY); // Создание черно-белого изображения
        Graphics2D g2d = bwImg.createGraphics(); // Рисование уменьшенного изображения
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // Формирование ASCII-графики (проход по пикселям циклом)
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int color = new Color(bwImg.getRGB(x, y)).getRed(); // Получение яркости пикселя
                char c = schema.convert(color); // Преобразование яркости в символ
                result.append(c).append(c); // Добавление символа
            }
            result.append('\n');
        }
        return result.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
