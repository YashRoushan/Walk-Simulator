
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BreadCrumbWalker class.
 * These are quite basic as the randomness makes unit tests challenging.
 * See component tests with WalkSim.
 *
 * 1. walk()
 * 1a. common case: ensure walk path is the correct length for number of steps.
 * 1b. edge case: walk 0 steps.
 * 1c: error case: walk negative steps.

 */
public class BreadCrumbWalkerTest {

    static final float[] testMat1 = {0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f};
    static final MarkovChain testMC1 = new MarkovChain(new FloatMatrix(testMat1, 4));

    @Test
    void walk_testCommon() {
        BreadCrumbWalker walker = new BreadCrumbWalker(testMC1);
        int Nsteps = 10;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size(), (Nsteps + 1) * 2, "With N steps, expected N+1 endpoints on the path");
    }

    @Test
    void walk_testZeroSteps() {
        BreadCrumbWalker walker = new BreadCrumbWalker(testMC1);
        int Nsteps = 0;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size(),0, "Expected empty path with negative steps.");
    }

    @Test
    void walk_testNegSteps() {
        BreadCrumbWalker walker = new BreadCrumbWalker(testMC1);
        int Nsteps = -1;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size(),0, "Expected empty path with negative steps.");
    }




}
