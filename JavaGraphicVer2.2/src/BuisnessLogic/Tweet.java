package BuisnessLogic;

import java.util.Date;
import java.util.Dictionary;

public class Tweet
{
    Tweet (double Latitude, double Longitude, Date Datetime, String Text)
    {
        latitude = Latitude;
        longitude = Longitude;
        datetime = Datetime;
        text = Text;
    }

    // широта (x)
    public double latitude;

    // долгота (y)
    public double longitude;

    // дата и время
    Date datetime;

    // текст
    String text;

    public double GetWeight (Dictionary sent)
    {
        // Слова твита
        String[] words = this.text.split("[,;:.!?\\s]+");

        // Финальное мнение
         double o = 0;

        // Промежуточное мнение
        double f = 0;

        // Счетчик
        int i = 0;

        // Вспомогательный счетчик (для отката основного)
        int q = 0;

        // Добавочная строка
        String s = "";

        // Перебираем слова
        while (i < words.length)
        {
            s = "";
            q = 0;

            // Выбираем слово
            String word1 = words[i];

            // Пока есть строки из "слово" + "след. слово" и не выходим за границы массива
            while (sent.get(word1 + s) != null && (i + 1) < words.length)
            {
                // Обновление веса
                f = (double)sent.get(word1 + s);

                // Обновление добавочной строки
                s += " " + words[i + 1];

                i++;
                q++;
                if (q == 2) {
                    System.out.println("");
                }
            }

            // Добавляем промужуточный вес к финальному
            o += f;

            i++;

            // Смещаем счетчик
            i -= q;
        }
        return o;
    }


    public String toString()
    {
        return String.format("Latitude: " + latitude + "\nLongitude: " + longitude + "\nDate: " + datetime.toString() + "\nText: " + text.replace('%',' '));
    }
}
