package mod6AI.ai;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void testTokenize() throws Exception {
        ArrayList<String> tokens = (ArrayList<String>) Tokenizer.tokenize("I don't like Java.\nHowever Java 8 has some pretty nice features. :)");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("i"); expected.add(Tokenizer.removeAllNoneAlphanumeric("don't")); expected.add("like"); expected.add("java");
        expected.add("however"); expected.add("java"); expected.add("8"); expected.add("has"); expected.add("some");
        expected.add("pretty"); expected.add("nice"); expected.add("features");

        assertEquals(expected, tokens);
    }

    @Test
    public void testRemoveAllNoneAlphanumeric() throws Exception {
        String t1 = "1, 2, 3, 4, 5, 6, 7, 8, 9& 0";
        String t2 = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
        assertEquals("1234567890", Tokenizer.removeAllNoneAlphanumeric(t1));
        assertEquals("QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm",
                Tokenizer.removeAllNoneAlphanumeric(t2));
    }
}