package com.people.app.Utils;

import com.people.app.util.Utils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @Test
    public void RutVerifierTest(){
        boolean valid = Utils.rutVerifier(146307760);
        Assert.assertTrue(valid);

    }
}
