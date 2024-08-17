package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ConverterTest {

    @Test
    void IntegerToString() {
        IntegerToStringConverter converter = new IntegerToStringConverter();

        String convert = converter.convert(10);

        assertThat(convert).isEqualTo("10");

    }

    @Test
    void StringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer convert = converter.convert("10");
        assertThat(convert).isEqualTo(10);
    }

    @Test
    void IpPortToString() {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        String convert = converter.convert(ipPort);
        assertThat(convert).isEqualTo("127.0.0.1:8080");
    }

    @Test
    void StringToIpPort() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String ipPort = "127.0.0.1:8080";
        IpPort convert = converter.convert(ipPort);
        assertThat(convert).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

}
