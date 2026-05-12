package Image;

public class SimpleTextColorSchema implements TextColorSchema{
    private final char[] chars = {'#', '$', '@', '%', '*', '+', '-', '\''};

    @Override
    public char convert(int color) {
        int index = (color * (chars.length - 1)) / 255;
        return chars[index];
    }
}