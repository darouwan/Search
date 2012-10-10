import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class Rectangle_Test {

    /**
     * @param indexPath
     * @param alpha
     * @param x_center
     * @param y_center
     * @param x_axis
     * @param y_axis
     * @param terms
     * @throws IOException
     * @throws ParseException
     * 
     * 
     */
    public void processQuery(String indexPath, float alpha, float x_center,
	    float y_center, float x_axis, float y_axis,
	    ArrayList<String> terms) throws IOException, ParseException {
	File INDEX_DIR = new File(indexPath);
	Directory dir = FSDirectory.open(INDEX_DIR);
	IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));

	NumericRangeQuery<Float> xQuery = NumericRangeQuery.newFloatRange(
		"x", x_center - x_axis / 2, x_center + x_axis / 2, true, true);
	NumericRangeQuery<Float> yQuery = NumericRangeQuery.newFloatRange(
		"y", y_center - y_axis / 2, y_center + y_axis / 2, true, true);

	BooleanQuery boolQuery = new BooleanQuery();
	boolQuery.add(xQuery, Occur.MUST);
	boolQuery.add(yQuery, Occur.MUST);

	BooleanQuery termBoolQuery = new BooleanQuery();

	for (int i = 0; i < terms.size(); i++) {
	    Term term = new Term("content", terms.get(i));
	    TermQuery termQuery = new TermQuery(term);
	    termBoolQuery.add(termQuery, Occur.SHOULD);
	}

	boolQuery.add(termBoolQuery, Occur.MUST);
	RectangleCustomScoreQuery myQuery = new RectangleCustomScoreQuery(boolQuery, null,
		x_center, y_center, x_axis, y_axis, alpha);

	// boolQuery.add(termBoolQuery, Occur.MUST);

	TopDocs tops = searcher.search(myQuery, 10); // retrieve top-10
						     // results
	for (int i = 0; i < tops.scoreDocs.length; i++) {
	    // System.out.println(i+" result found");
	    ScoreDoc sdoc = tops.scoreDocs[i];
	    Document doc = searcher.doc(sdoc.doc);

	    // double x = Double.parseDouble(doc.get("x"));
	    // double y = Double.parseDouble(doc.get("y"));
	    // double dist1 = Location.getDist(x_center, y_center, x, y);
	    // double dist2 = Math.sqrt((x_axis / 2) * (x_axis / 2) + (y_axis /
	    // 2)
	    // * (y_axis / 2));
	    // // System.out.println(dist1 + " " + dist2);
	    // double d = 1 - dist1 / dist2;
	    //
	    // double score = sdoc.score * alpha + d * (1 - alpha);
	    System.out.println(doc.get("fileName") + "," + sdoc.score);
	}
    }

    public static void main(String[] args) throws CorruptIndexException,
	    LockObtainFailedException, IOException, ParseException {
	// TODO Auto-generated method stub

	Rectangle_Test rt = new Rectangle_Test();

	String indexPath = "index";
	float alpha = (float) 0.5;
	float x_center = 14;
	float y_center = 40;
	float x_axis = 10;
	float y_axis = 10;

	String keyWords = "fuel agip";
	String[] terms = keyWords.split(" ");
	ArrayList<String> al = new ArrayList<String>();
	for (int i = 0; i < terms.length; i++) {
	    al.add(terms[i]);
	}

	rt.processQuery(indexPath, alpha, x_center, y_center, x_axis, y_axis,
		al);

	// System.out.println(rt.getDist(33, 15, 14.1732125, 42.4918742));

    }

}
