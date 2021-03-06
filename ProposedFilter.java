/**
 * Created by dongyi.kim on 2017-06-07.
 */
public class ProposedFilter extends MedianFilter {

    public ProposedFilter(int kernelSize) {
        super(kernelSize);
    }



    @Override
    public int[][] filtering(int[][] m) {
        int[][] X = getEmptyOutputMatrix(m);
        int h = m.length;
        int w = m[0].length;
        int r = mKernelSize / 2;
        int medianIndex = mKernelSize * mKernelSize / 2;
        int[][] histogramCounter = new int[w][256];
        for(int i = 0; i < h; i++){
            int[] windowCounter = new int[256];
            for(int j = 0 ; j < w; j++){
                int color = m[i][j];
                int rowToRemove = i - mKernelSize;
                if(rowToRemove >= 0){
                    int colorToRemove = m[rowToRemove][j];
                    histogramCounter[j][colorToRemove] --;
                }
                histogramCounter[j][color] ++;

                int columnToRemove = j - mKernelSize;
                for(int ch = 0; ch < 256; ch++){
                    if(columnToRemove >= 0){
                        windowCounter[ch] -= histogramCounter[columnToRemove][ch];
                    }
                    windowCounter[ch] += histogramCounter[j][ch];
                }

                if(i >= mKernelSize - 1 && j >= mKernelSize - 1){
                    int x = j - mKernelSize + 1;
                    int y = i - mKernelSize + 1;
                    int before = 0;
                    for(int cw = 0; cw <256; cw++){
                        before += windowCounter[cw];
                        if( before >= medianIndex ){
                            X[y][x] = cw;
                            break;
                        }

                    }
                }
            }
        }
        return X;
    }
}
