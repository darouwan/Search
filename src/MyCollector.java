import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

public class MyCollector extends Collector {
    IndexReader reader = null;// reader用于读取获取文档
    // 收集信息的map
    public Map<Integer, Float> map = new HashMap<Integer, Float>();
    // MyScorer score = new MyScorer(null);

    float x_center = 0;
    float y_center = 0;
    float r = 0;
    ArrayList<String> resultSet = new ArrayList<String>();
    HashMap<String, Integer> docFreq = new HashMap<String, Integer>();

    public MyCollector(float x, float y, float r) {
	this.x_center = x;
	this.y_center = y;
	this.r = r;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean acceptsDocsOutOfOrder() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public void collect(int doc) throws IOException {
	// TODO Auto-generated method stub

	Document document = reader.document(doc);
	// int id = Integer.parseInt(document.get("fileName"));
	float x = Float.parseFloat(document.get("x"));
	float y = Float.parseFloat(document.get("y"));
	// Location loc = new Location(x, y);
	float dist = Location.getDist(x, y, x_center, y_center);

	TermFreqVector termFreqVector = reader
		.getTermFreqVector(doc, "content");
	if(termFreqVector==null){
	    System.out.println("termFreqVector is null");
	}
	if (termFreqVector != null) {
	    String[] terms = termFreqVector.getTerms();
	    // int[] count = termFreqVector.getTermFrequencies();
	    if (dist <= r) {
		System.out.println("doc:" + document.get("fileName"));
		resultSet.add(document.get("fileName"));

		for (int i = 0; i < terms.length; i++) {
		    String term = terms[i];
		    if (docFreq.containsKey(term)) {
			int count = docFreq.get(term) + 1;
			docFreq.put(term, count);
		    } else {
			docFreq.put(term, 1);
		    }
		}

	    }
	}
	// map.put(id, dist);
	// System.out.println("put:" + id + " " + dist);
    }

    @Override
    public void setNextReader(IndexReader reader, int docBase)
	    throws IOException {
	// TODO Auto-generated method stub
	this.reader = reader;// 假设reader由多个subReader构成，那么本方法将被调用与subReader个数相同的次数
	System.out.println("set reader");
    }

    @Override
    public void setScorer(Scorer scorer) throws IOException {
	// TODO Auto-generated method stub

    }

    public ArrayList<String> getResultSet() {
	return resultSet;
    }

    public HashMap<String, Integer> getDocFreq() {
	return docFreq;
    }

    public void setDocFreq(HashMap<String, Integer> docFreq) {
	this.docFreq = docFreq;
    }

}
