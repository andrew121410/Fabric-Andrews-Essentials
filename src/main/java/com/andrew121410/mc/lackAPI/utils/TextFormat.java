package com.andrew121410.mc.lackAPI.utils;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TextFormat {

    public static Text stringToFormattedText(String text) {
        MutableText output = new LiteralText("");
        char[] textArray = text.toCharArray();

        int[] markers = new int[text.replaceAll("[^&]+", "").length() + 2];
        char[] formatArray = new char[markers.length + 1];

        int j = 1;
        for (int i = 0; i < textArray.length; i++) {
            if (textArray[i] == '&') {
                markers[j] = i;
                formatArray[j] = textArray[i + 1];
                j++;
            }
        }

        markers[0] = 0;
        markers[markers.length - 1] = text.length();
        formatArray[0] = 'f';
        for (int i = 0; i < markers.length - 1; i++) {

            output.append(getFormattedPart(text, markers[i], markers[i + 1], getFormatting(formatArray[i])));
        }

        return output;
    }

    private static Text getFormattedPart(String text, int firstIndex, int lastIndex, Formatting formatting) {
        String outputString = text.substring(firstIndex, lastIndex);
        outputString = outputString.replaceAll("&[b0931825467adcfeklmnor]", "");
        MutableText output = new LiteralText(outputString);
        if (formatting == Formatting.RESET) formatting = lastFormatting;
        output = output.formatted(formatting);
        return output;
    }

    static Formatting lastFormatting = Formatting.WHITE;

    private static Formatting getFormatting(char character) {
//    kept getting an error when using Formatting.fromCode(char) so here's this monstrosity
        String test = "b0931825467adcfeklmnor";
        if (!test.contains(Character.toString(character))) return Formatting.RESET;
        Formatting output = Formatting.WHITE;
        switch (character) {
            case 'b':
                output = Formatting.AQUA;
                break;
            case '0':
                output = Formatting.BLACK;
                break;
            case '9':
                output = Formatting.BLUE;
                break;
            case '3':
                output = Formatting.DARK_AQUA;
                break;
            case '1':
                output = Formatting.DARK_BLUE;
                break;
            case '8':
                output = Formatting.DARK_GRAY;
                break;
            case '2':
                output = Formatting.DARK_GREEN;
                break;
            case '5':
                output = Formatting.DARK_PURPLE;
                break;
            case '4':
                output = Formatting.DARK_RED;
                break;
            case '6':
                output = Formatting.GOLD;
                break;
            case '7':
                output = Formatting.GRAY;
                break;
            case 'a':
                output = Formatting.GREEN;
                break;
            case 'd':
                output = Formatting.LIGHT_PURPLE;
                break;
            case 'c':
                output = Formatting.RED;
                break;
            case 'f':
                output = Formatting.WHITE;
                break;
            case 'e':
                output = Formatting.YELLOW;
                break;
            case 'k':
                output = Formatting.OBFUSCATED;
                break;
            case 'l':
                output = Formatting.BOLD;
                break;
            case 'm':
                output = Formatting.STRIKETHROUGH;
                break;
            case 'n':
                output = Formatting.UNDERLINE;
                break;
            case 'o':
                output = Formatting.ITALIC;
                break;
            case 'r':
                output = Formatting.RESET;
                break;
        }
        lastFormatting = output;
        return output;
    }
}
