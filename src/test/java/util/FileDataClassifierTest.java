package util;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileDataClassifierTest {

    @Nested
    class DetectTypeTests {
        @Test
        void detectType_shouldReturnInteger_forWholeNumberString() throws Exception {
            FileDataClassifier classifier = new FileDataClassifier(null); // null, потому что ParsedArguments нам тут не нужен

            Method method = FileDataClassifier.class.getDeclaredMethod("detectType", String.class);
            method.setAccessible(true);

            DataType result = (DataType) method.invoke(classifier, "123");
            assertEquals(DataType.INTEGER, result);
        }

        @Test
        void detectType_shouldReturnFloat_forDecimalString() throws Exception {
            FileDataClassifier classifier = new FileDataClassifier(null);

            Method method = FileDataClassifier.class.getDeclaredMethod("detectType", String.class);
            method.setAccessible(true);

            DataType result = (DataType) method.invoke(classifier, "3.14");
            assertEquals(DataType.FLOAT, result);
        }

        @Test
        void detectType_shouldReturnString_forNonNumericString() throws Exception {
            FileDataClassifier classifier = new FileDataClassifier(null);

            Method method = FileDataClassifier.class.getDeclaredMethod("detectType", String.class);
            method.setAccessible(true);

            DataType result = (DataType) method.invoke(classifier, "hello world");
            assertEquals(DataType.STRING, result);

        }


        private DataType invokeDetect(String s) throws Exception {
            FileDataClassifier c = new FileDataClassifier(null);
            Method m = FileDataClassifier.class.getDeclaredMethod("detectType", String.class);
            m.setAccessible(true);
            return (DataType) m.invoke(c, s);
        }

        @Test
        void emptyString_isString() throws Exception {
            assertEquals(DataType.STRING, invokeDetect(""));
        }

        @Test
        void spacesOnly_isString() throws Exception {
            assertEquals(DataType.STRING, invokeDetect("   "));
        }

        @Test
        void int_negative_isInteger() throws Exception {
            assertEquals(DataType.INTEGER, invokeDetect("-42"));
        }

        @Test
        void int_positiveWithPlus_isInteger() throws Exception {
            assertEquals(DataType.INTEGER, invokeDetect("+42"));
        }

        @Test
        void int_zeroWithMinus_isInteger() throws Exception {
            assertEquals(DataType.INTEGER, invokeDetect("-0"));
        }

        @Test
        void float_decimalDot_isFloat() throws Exception {
            assertEquals(DataType.FLOAT, invokeDetect("3.14"));
        }

        @Test
        void float_trailingDot_isFloat() throws Exception {
            assertEquals(DataType.FLOAT, invokeDetect("1."));
        }

        @Test
        void float_leadingDot_isFloat() throws Exception {
            assertEquals(DataType.FLOAT, invokeDetect(".5"));
        }

        @Test
        void float_scientificNotation_isFloat() throws Exception {
            assertEquals(DataType.FLOAT, invokeDetect("1e10"));
            assertEquals(DataType.FLOAT, invokeDetect("-5E-2"));
        }

        @Test
        void nan_and_infinity_areFloat_currentLogic() throws Exception {
            assertEquals(DataType.FLOAT, invokeDetect("NaN"));
            assertEquals(DataType.FLOAT, invokeDetect("Infinity"));
            assertEquals(DataType.FLOAT, invokeDetect("-Infinity"));
        }

        @Test
        void tooBigForLong_parsedAsFloat() throws Exception {
            assertEquals(DataType.FLOAT, invokeDetect("999999999999999999999999"));
        }

        @Test
        void commaDecimal_isString() throws Exception {
            assertEquals(DataType.STRING, invokeDetect("3,14"));
        }

        @Test
        void mixedDigitsAndLetters_isString() throws Exception {
            assertEquals(DataType.STRING, invokeDetect("123abc"));
        }

        @Test
        void spacesAroundNumber_withTrim_isInteger() throws Exception {
            assertEquals(DataType.INTEGER, invokeDetect(" 42 "));
        }


    }

    @Nested
    class ReadSingleFileTests {

        @Test
        void shouldSeparateDataIntoCorrectLists() throws Exception {
            // 1. Создаём временный файл с тестовыми данными
            Path tempFile = Files.createTempFile("test", ".txt");
            Files.write(tempFile, List.of(
                    "123",       // integer
                    "45.67",     // float
                    "hello"      // string
            ));

            // 2. Создаём ParsedArguments, чтобы передать в конструктор
            ParsedArguments parsedArgs = new ParsedArguments();
            FileDataClassifier classifier = new FileDataClassifier(parsedArgs);

            // 3. Получаем доступ к приватному методу readSingleFile
            Method method = FileDataClassifier.class.getDeclaredMethod("readSingleFile", Path.class);
            method.setAccessible(true);

            // 4. Вызываем метод
            method.invoke(classifier, tempFile);

            // 5. Проверяем, что данные разложились по нужным спискам
            assertTrue(classifier.getIntegers().contains("123"));
            assertTrue(classifier.getFloats().contains("45.67"));
            assertTrue(classifier.getStrings().contains("hello"));
        }


    }

    @Nested
    class ReadInputFilesTests {

        @Test
        void shouldReadTwoFiles() throws Exception {
            // 1. Создаём два временных файла
            Path file1 = Files.createTempFile("test1", ".txt");
            Files.write(file1, List.of(
                    "123", "45.67", "hello"
            ));

            Path file2 = Files.createTempFile("test2", ".txt");
            Files.write(file2, List.of(
                    "999", "3.14", "world"
            ));

            // 2. Создаём ParsedArguments с путями
            ParsedArguments parsedArgs = new ParsedArguments();
            parsedArgs.setInputFiles(List.of(
                    file1.toString(),
                    file2.toString()
            ));

            // 3. Вызываем метод
            FileDataClassifier classifier = new FileDataClassifier(parsedArgs);
            classifier.readInputFiles();

            // 4. Проверяем, что всё прочиталось
            assertTrue(classifier.getIntegers().containsAll(List.of("123", "999")));
            assertTrue(classifier.getFloats().containsAll(List.of("45.67", "3.14")));
            assertTrue(classifier.getStrings().containsAll(List.of("hello", "world")));
        }

        @Test
        void shouldReadThreeFiles() throws Exception {
            // 1. Создаём три временных файла
            Path file1 = Files.createTempFile("f1", ".txt");
            Files.write(file1, List.of("1", "apple"));

            Path file2 = Files.createTempFile("f2", ".txt");
            Files.write(file2, List.of("2.2", "banana"));

            Path file3 = Files.createTempFile("f3", ".txt");
            Files.write(file3, List.of("3", "3.5", "cherry"));

            // 2. Заполняем ParsedArguments
            ParsedArguments parsedArgs = new ParsedArguments();
            parsedArgs.setInputFiles(List.of(
                    file1.toString(),
                    file2.toString(),
                    file3.toString()
            ));

            // 3. Вызываем метод
            FileDataClassifier classifier = new FileDataClassifier(parsedArgs);
            classifier.readInputFiles();

            // 4. Проверки
            assertTrue(classifier.getIntegers().containsAll(List.of("1", "3")));
            assertTrue(classifier.getFloats().containsAll(List.of("2.2", "3.5")));
            assertTrue(classifier.getStrings().containsAll(List.of("apple", "banana", "cherry")));
        }
    }


}


