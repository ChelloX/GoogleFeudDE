/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

import java.util.HashMap;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class GoogleSearchAPITest {

    @Test
    public void testAntwortenZuString() {
        GoogleSearchAPI api = new GoogleSearchAPI();
        HashMap<Integer, String> s1 = api.gibAntwortenZuString("foo bar");
        HashMap<Integer, String> s2 = api.gibAntwortenZuString("foobar");
        HashMap<Integer, String> s3 = api.gibAntwortenZuString("foo "); //1x _
        HashMap<Integer, String> s4 = api.gibAntwortenZuString(" foo"); // 1x_
        HashMap<Integer, String> s5 = api.gibAntwortenZuString("foo  "); //2x _
        HashMap<Integer, String> s6 = api.gibAntwortenZuString("  foo"); //2x _

        assertTrue(!s1.isEmpty());
        assertTrue(!s2.isEmpty());
        assertTrue(!s3.isEmpty());
        assertTrue(!s4.isEmpty());
        assertTrue(!s5.isEmpty());
    }
}
