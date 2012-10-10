import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Circle_Test {
	public void processQuery(String indexPath, float alpha, float x_center,
			float y_center, float r, ArrayList<String> terms)
			throws IOException {
		File INDEX_DIR = new File(indexPath);
		Directory dir = FSDirectory.open(INDEX_DIR);
		IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
		MyCollector collector = new MyCollector(x_center, y_center, r);

		NumericRangeQuery<Float> xQuery = NumericRangeQuery.newFloatRange("x",
				x_center - r, x_center + r, true, true);
		NumericRangeQuery<Float> yQuery = NumericRangeQuery.newFloatRange("y",
				y_center - r, y_center + r, true, true);

		BooleanQuery boolQuery = new BooleanQuery();
		boolQuery.add(xQuery, Occur.MUST);
		boolQuery.add(yQuery, Occur.MUST);  

		searcher.search(boolQuery, collector);
		ArrayList<String> fileInCircle = collector.getResultSet();
		HashMap<String, Integer> docFreq = collector.getDocFreq();
		System.out.println("DocFreq = " + docFreq + " fileInCircleSize = "
				+ fileInCircle.size());

		BooleanQuery termBoolQuery = new BooleanQuery();
		for (int i = 0; i < terms.size(); i++) {
			Term term = new Term("content", terms.get(i));
			TermQuery termQuery = new TermQuery(term);
			termBoolQuery.add(termQuery, Occur.SHOULD);
		}

		CircleCustomScoreQuery myQuery = new CircleCustomScoreQuery(
				termBoolQuery, x_center, y_center, r, alpha, fileInCircle,
				terms, docFreq);
		TopDocs tops = searcher.search(myQuery, 10); // retrieve top-10
		// results
		for (int i = 0; i < tops.scoreDocs.length; i++) {
			// System.out.println(i+" result found");
			ScoreDoc sdoc = tops.scoreDocs[i];
			if (sdoc.score >= 0) {
				Document doc = searcher.doc(sdoc.doc);

				System.out.println(doc.get("fileName") + "," + sdoc.score);
			}

		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Circle_Test ct = new Circle_Test();

		String indexPath = "index";
		float alpha = 0.4f;
		float x_center = 16;
		float y_center = 39;
		float r = 3;

		String keyWords = "food pizza bar";
		String[] terms = keyWords.split(" ");
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < terms.length; i++) {
			al.add(terms[i]);
		}

		ct.processQuery(indexPath, alpha, x_center, y_center, r, al);
	}

}
