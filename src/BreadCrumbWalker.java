import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BreadCrumbWalker extends RandomWalker{
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
    public BreadCrumbWalker(MarkovChain mc) {
        super(mc);
        this.mc = mc;
        path = new ArrayList<>();
    }

    /**
     * Perform a random walk of Nsteps and return the resulting path.
     * The path is encoded as a list of coordinates indicating the start and end of
     * each step. Specifically, the coordinate at index i and the coordinate at index i+1
     * encode the i+1'th step from coordinate i to coordinate i+1.
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
        Coordinate curPos = new Coordinate(START_X, START_Y);
        path.clear(); //reset the path for this new walk.

        if (N > 0) {
            path.add(new Coordinate(curPos.x, curPos.y));
        }
        for (int step = 0; step < N; ++step) {
            mc.nextState();
            curPos.accumulate(getStepDirection());
            path.add(new Coordinate(curPos.x, curPos.y));
        }

        // add the path in reverse order before returning the path.
        for(int i = path.size() - 1; i >=0 ; i--){
            path.add(path.get(i));
        }
        return path;
    }

    // method to print the output of BreadCrumbWalker class
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
