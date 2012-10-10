import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.function.CustomScoreProvider;

public class RectangleCustomScoreProvider extends CustomScoreProvider {
    // String[] filenames = null;

    float[] x;
    float[] y;
    float x_center;
    float y_center;
    float x_axis;
    float y_axis;
    float alpha;

    public RectangleCustomScoreProvider(IndexReader reader, float x_center,
	    float y_center, float x_axis, float y_axis, float alpha) {
	super(reader);
	try {
	    x = FieldCache.DEFAULT.getFloats(reader, "x");
	    y = FieldCache.DEFAULT.getFloats(reader, "y");
	    this.x_center = x_center;
	    this.y_center = y_center;
	    this.x_axis = x_axis;
	    this.y_axis = y_axis;
	    this.alpha = alpha;
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public float customScore(int doc, float subQueryScore, float valSrcScore)
	    throws IOException {
	// 根据doc获取文件名
	// String filename = filenames[doc];

	float dist1 = Location.getDist(x_center, y_center, x[doc], y[doc]);
	float dist2 = (float) Math.sqrt((x_axis / 2) * (x_axis / 2) + (y_axis / 2)
		* (y_axis / 2));
	float d = 1 - dist1 / dist2;

	float score = subQueryScore * alpha + d * (1 - alpha);
	System.out
		.println(FieldCache.DEFAULT.getStrings(reader, "fileName")[doc]
			+ " subQueryScore = " + subQueryScore + " d=" + d
			+ " score = " + score);
	return (float) score;
    }
}
