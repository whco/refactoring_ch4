import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;

public class FileReaderTester extends TestCase {

    public FileReaderTester(String name) {
        super(name);
    }

    FileReader _input;
    FileReader _empty;

    @Override
    protected void setUp() throws Exception {
        try {
            _input = new FileReader("data.txt");
            _empty = newEmptyFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("테스트 파일을 열 수 없음");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            _input.close();
        } catch (IOException e) {
            throw new RuntimeException("테스트 파일 닫는 중 에러 발생");
        }
    }

    private FileReader newEmptyFile() throws IOException {
        File empty = new File("empty.txt");
        FileOutputStream out = new FileOutputStream(empty);
        out.close();
        return new FileReader(empty);
    }
    public void testRead() throws IOException {
        char ch = '&';
//        _input.close();
        for (int i = 0; i < 4; i++) {
            ch = (char) _input.read();
        }
        assertEquals('d', ch);
    }

    public void testReadAtEnd() throws IOException {
        int ch = -1234;
        for (int i = 0; i < 141; i++) {
            ch = _input.read();
        }
        assertEquals("read at end", -1, _input.read());
    }

    public void testReadBoundaries() throws IOException {
        assertEquals("read first char", 'B', _input.read());
        int ch;
        for (int i = 1; i < 140; i++) {
            ch = _input.read();
        }
        assertEquals("read last char", '6', _input.read());
        assertEquals("read at end", -1, _input.read());
        assertEquals("read past end", -1, _input.read());
    }

    public void testReadAfterClose() throws IOException {
        _input.close();
        try {
            _input.read();
            fail("read past end에 예외가 발생하지 않음");
        } catch (IOException io) {

        }
    }
    public void testEmptyRead() throws IOException {
        assertEquals(-1, _empty.read());
    }
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new FileReaderTester("testRead"));
        suite.addTest(new FileReaderTester("testReadAtEnd"));
        return suite;
    }

    public static void main(String[] args) {
//        junit.textui.TestRunner.run(suite());
        junit.textui.TestRunner.run(new TestSuite(FileReaderTester.class));//test로 시작하는 이름의 모든 메서드의 테스트 케이스가 든 테스트 스위트 생성
    }
}
