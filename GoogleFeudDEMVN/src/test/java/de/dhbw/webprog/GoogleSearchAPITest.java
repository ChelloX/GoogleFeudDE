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

    private GoogleSearchAPI api = null;

    public GoogleSearchAPITest() {
        api = new GoogleSearchAPI();
    }

    @Test
    public void t1() {
        HashMap<Integer, String> s = api.gibAntwortenZuString("foo bar");
        assertTrue(!s.isEmpty());
    }

    @Test
    public void t2() {
        HashMap<Integer, String> s = api.gibAntwortenZuString("foobar");
        assertTrue(!s.isEmpty());
    }

    @Test
    public void t3() {
        HashMap<Integer, String> s = api.gibAntwortenZuString("foo "); //1x _
        assertTrue(!s.isEmpty());
    }

    @Test
    public void t4() {
        HashMap<Integer, String> s = api.gibAntwortenZuString(" foo"); //1x _
        assertTrue(!s.isEmpty());
    }

    @Test
    public void t5() {
        HashMap<Integer, String> s = api.gibAntwortenZuString("foo  "); //2x _
        assertTrue(!s.isEmpty());
    }

    @Test
    public void t6() {
        HashMap<Integer, String> s = api.gibAntwortenZuString("  foo"); //2x _
        assertTrue(!s.isEmpty());
    }
    
    @Test
    public void t7() {
        HashMap<Integer, String> s = api.gibAntwortenZuString("föö"); //2x _
        assertTrue(!s.isEmpty());
    }
}
