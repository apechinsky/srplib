package org.srplib.reflection.valuefactory.factories;

import java.util.Calendar;
import java.util.Date;

import org.srplib.reflection.valuefactory.ValueFactory;
import org.srplib.reflection.valuefactory.TypeMeta;

/**
 * @author Anton Pechinsky
 */
public class DateValueFactory implements ValueFactory<Date> {

    public static ValueFactory<Date> initial() {
        return new ConstantValueFactory<>(new Date(0));
    }

    @Override
    public Date get(TypeMeta meta) {

        return initial().get(meta);
    }


    /**
     * Создает дату на основе переданных года, месяца и дня, часа. минуты и секунды.
     *
     * <p>Миллисекунды устанавливаются в 0.</p>
     *
     * <p>Аналогичный метод класса Date ({@link Date#Date(int, int, int, int, int, int)})- deprecated. Данный метод
     * использует календарь для создания даты.</p>
     *
     * @param year год
     * @param month месяц
     * @param day день
     * @return Date дата
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Создает дату на основе переданных года, месяца и дня.
     *
     * @param year год
     * @param month месяц
     * @param day день
     * @return Date дата
     */
    public static Date newDate(int year, int month, int day) {
        return newDate(year, month, day, 0, 0, 0);
    }

}
