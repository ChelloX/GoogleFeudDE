/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class GoogleTrendsAPITest {

    private GoogleTrendsAPI api = null;

    public GoogleTrendsAPITest() {
        api = new GoogleTrendsAPI();
    }

    @Test
    public void t1() {
        String was = api.gibZufaelligeFrageZuKategorie(Statics.getWAS());
        assertNotNull(was);
        assertTrue(!was.equals(""));
    }

    @Test
    public void t2() {
        String wo = api.gibZufaelligeFrageZuKategorie(Statics.getWO());
        assertNotNull(wo);
        assertTrue(!wo.equals(""));
    }

    @Test
    public void t3() {
        String wann = api.gibZufaelligeFrageZuKategorie(Statics.getWANN());
        assertNotNull(wann);
        assertTrue(!wann.equals(""));
    }
}
