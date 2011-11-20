package analysis.utils;

import java.awt.Paint;
import org.jfree.chart.renderer.category.LayeredBarRenderer;

/**
 * @author Aloren
 */
public class ColorBarRenderer extends LayeredBarRenderer {

    /**
     * Color of the bars.
     */
    private Paint basePaint;

    public ColorBarRenderer(Paint curPaint) {
        super();
        basePaint = curPaint;
    }

    @Override
    public Paint getItemPaint(int x_row, int x_col) {
        return basePaint;
    }

    @Override
    public Paint getSeriesFillPaint(int series) {
        return basePaint;
    }

}
