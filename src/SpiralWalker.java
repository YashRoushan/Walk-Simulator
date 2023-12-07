import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SpiralWalker extends RandomWalker{

    public static final int START_X = 0;
    public static final int START_Y = 0;
    public static final int STEP_SIZE = 1; //each step along the lattice moves this many units

    private MarkovChain mc;
    private Coordinate curPos;
    private ArrayList<Coordinate> path;

    // state array is storing the direction pattern
    private String [] state = {"N", "E", "S", "W"};

    /**
     * Construct a RandomWalker from a Markov chain.
     * The Markov chain should at most have the state labels "N", "E", "S", "W";
     * which encode the directions to walk
     *
     * @param mc the Markov chain encoding walk direction
     */
    public SpiralWalker(MarkovChain mc) {
        super(mc);
        path = new ArrayList<>();
    }


    /**
     * Perform a random walk of Nsteps and return the resulting path.
     * The path is encoded as a list of coordinates indicating the start and end of
     * each step.
     *
     * If Nsteps is 0 or negative, the resulting path is empty.
     *
     * Note that each call to walk resets the path of this walker to start again from
     * the default starting point and clears the history of the previous path.
     *
     * @param Nsteps: the number of steps to simulate in the random walk
     * @return the path of the walk.
     */
    @Override
    public ArrayList<Coordinate> walk(int Nsteps) {

        int N = Nsteps;
        int directionChange = 0;
        int steps = 1;
        int stepCount = 0;
        int squareCorners = 4;

        Coordinate curPos = new Coordinate(START_X, START_Y);
        path.clear(); //reset the path for this new walk.

        if (N > 0) {
            path.add(new Coordinate(curPos.x, curPos.y));
        }


        // take the steps 'Nsteps' times
        for (int i = 0; i < Nsteps; i++) {

            Coordinate step = new Coordinate(0,0);
            String direction = state[directionChange % squareCorners];

            // changing directions according to the state
            if (direction.equals("N")) {
                step.accumulate(new Coordinate(0, STEP_SIZE));
            } else if (direction.equals("E")) {
                step.accumulate(new Coordinate(STEP_SIZE, 0));
            } else if (direction.equals("S")) {
                step.accumulate(new Coordinate(0, -STEP_SIZE));
            } else {
                step.accumulate(new Coordinate(-STEP_SIZE, 0));
            }

            // change curPos
            curPos.x = curPos.x + step.x;
            curPos.y = curPos.y +  step.y;

            path.add(new Coordinate(curPos.x, curPos.y));

            stepCount++;
            if (stepCount == steps) {
                stepCount = 0;

                // repeat direction after 4(squareCorners) steps
                directionChange = (directionChange + 1) % squareCorners;

                // Increase steps after every two direction changes
                if (directionChange % 2 == 0) {
                    steps++;
                }
            }
        }

        return path;
    }


    // method to print the output of SpiralWalker class
    public void saveWalkToFile(String fname) throws IOException {
        PrintWriter writer = new PrintWriter(fname);
        String format = fname.substring(fname.length()-4,fname.length());
        for (Coordinate coord : this.path) {
            if(format.equals(".txt")){
                writer.println(String.format("(%d, %d)", coord.x, coord.y));
            }
            else if(format.equals(".dat")){
                writer.print(String.format(coord.x + " " +  coord.y + " "));
            }
        }
        writer.close();
    }
}
