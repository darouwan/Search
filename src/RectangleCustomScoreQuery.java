import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.ValueSourceQuery;

public class RectangleCustomScoreQuery extends CustomScoreQuery {
    float x_center;
    float y_center;
    float x_axis;
    float y_axis;
    float alpha;

    public RectangleCustomScoreQuery(Query subQuery, ValueSourceQuery valSrcQuery,
	    float x_center, float y_center, float x_axis, float y_axis,
	    float alpha) {
	super(subQuery, valSrcQuery);
	this.x_center = x_center;
	this.y_center = y_center;
	this.x_axis = x_axis;
	this.y_axis = y_axis;
	this.alpha = alpha;
    }

    @Override
    protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
	    throws IOException {
	// 默认情况评分是根据原有的评分*传入进来的评分
	// return super.getCustomScoreProvider(reader);

	return new RectangleCustomScoreProvider(reader, this.x_center, this.y_center,
		this.x_axis, this.y_axis,this.alpha);
    }

}
