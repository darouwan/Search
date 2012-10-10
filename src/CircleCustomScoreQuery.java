import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;

public class CircleCustomScoreQuery extends CustomScoreQuery {
    float x_center;
    float y_center;
    float r;
    ArrayList<String> filesIncircle;
    ArrayList<String> terms;
    float alpha;
    HashMap<String,Integer> docFreq;
    
    public CircleCustomScoreQuery(Query subQuery, float x_center,
	    float y_center, float r, float alpha, ArrayList<String> filesIncircle, ArrayList<String> terms2, HashMap<String, Integer> docFreq) {
	super(subQuery);
	this.x_center = x_center;
	this.y_center = y_center;
	this.r = r;
	this.filesIncircle = filesIncircle;
	this.terms = terms2;
	this.alpha = alpha;
	this.docFreq = docFreq;
	// TODO Auto-generated constructor stub
    }

    @Override
    protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
	    throws IOException {
	// 默认情况评分是根据原有的评分*传入进来的评分
	// return super.getCustomScoreProvider(reader);

	return new CircleCustomScoreProvider(reader, this.x_center,
		this.y_center, this.r, this.alpha, this.filesIncircle,this.terms,this.docFreq);
    }
}
