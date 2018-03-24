package fr.xephi.authme.datasource.columnshandler;

import ch.jalu.configme.properties.Property;
import ch.jalu.datasourcecolumns.ColumnType;
import ch.jalu.datasourcecolumns.StandardTypes;
import fr.xephi.authme.data.auth.PlayerAuth;

import java.util.function.Function;

/**
 * Util class for initializing {@link AuthMeColumns} constants.
 */
final class AuthMeColumnsFactory {

    private AuthMeColumnsFactory() {
    }

    static AuthMeColumns<Integer> createInteger(Property<String> nameProperty,
                                                Function<PlayerAuth, Integer> playerAuthGetter,
                                                ColumnOptions... options) {
        return createInternal(StandardTypes.INTEGER, nameProperty, playerAuthGetter, options);
    }

    static AuthMeColumns<Long> createLong(Property<String> nameProperty,
                                          Function<PlayerAuth, Long> playerAuthGetter,
                                          ColumnOptions... options) {
        return createInternal(StandardTypes.LONG, nameProperty, playerAuthGetter, options);
    }

    static AuthMeColumns<String> createString(Property<String> nameProperty,
                                              Function<PlayerAuth, String> playerAuthGetter,
                                              ColumnOptions... options) {
        return createInternal(StandardTypes.STRING, nameProperty, playerAuthGetter, options);
    }

    static AuthMeColumns<Double> createDouble(Property<String> nameProperty,
                                              Function<PlayerAuth, Double> playerAuthGetter,
                                              ColumnOptions... options) {
        return createInternal(StandardTypes.DOUBLE, nameProperty, playerAuthGetter, options);
    }

    static AuthMeColumns<Float> createFloat(Property<String> nameProperty,
                                            Function<PlayerAuth, Float> playerAuthGetter,
                                            ColumnOptions... options) {
        return createInternal(StandardTypes.FLOAT, nameProperty, playerAuthGetter, options);
    }

    private static <T> AuthMeColumns<T> createInternal(ColumnType<T> type, Property<String> nameProperty,
                                                       Function<PlayerAuth, T> authGetter, ColumnOptions... options) {
        return new AuthMeColumns<>(type, nameProperty, authGetter, isOptional(options), hasDefaultForNull(options));
    }

    private static boolean isOptional(ColumnOptions[] options) {
        return containsInArray(ColumnOptions.OPTIONAL, options);
    }

    private static boolean hasDefaultForNull(ColumnOptions[] options) {
        return containsInArray(ColumnOptions.DEFAULT_FOR_NULL, options);
    }

    private static boolean containsInArray(ColumnOptions needle, ColumnOptions[] haystack) {
        for (ColumnOptions option : haystack) {
            if (option == needle) {
                return true;
            }
        }
        return false;
    }

    enum ColumnOptions {

        OPTIONAL,

        DEFAULT_FOR_NULL
    }
}
