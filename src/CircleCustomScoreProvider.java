import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.function.CustomScoreProvider;

public class CircleCustomScoreProvider extends CustomScoreProvider {
	float[] x;
	float[] y;
	float x_center;
	float y_center;
	float r;
	float alpha;
	ArrayList<String> filesInCircle;
	ArrayList<String> terms;
	IndexReader reader;
	String[] files;
	int ds;
	HashMap<String, Integer> docFreq;

	public CircleCustomScoreProvider(IndexReader reader, float x_center,
			float y_center, float r, float alpha,
			ArrayList<String> filesInCircle, ArrayList<String> terms,
			HashMap<String, Integer> docFreq) {
		super(reader);
		try {
			x = FieldCache.DEFAULT.getFloats(reader, "x");
			y = FieldCache.DEFAULT.getFloats(reader, "y");
			files = FieldCache.DEFAULT.getStrings(reader, "fileName");
			this.x_center = x_center;
			this.y_center = y_center;
			this.r = r;
			this.filesInCircle = filesInCircle;
			this.reader = reader;
			this.alpha = alpha;
			this.ds = filesInCircle.size();
			this.terms = terms;
			this.docFreq = docFreq;
			System.out.println("ds = " + ds);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub

	}

	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScore) {
		// System.out.println("x = " + x[doc] + " y = " + y[doc]);
		float d_t = 0;
		String fileName = files[doc];
		if (!filesInCircle.contains(fileName)) {
			// System.out.println(fileName+ " not in circle");
			return -1;
		}
		for (int i = 0; i < terms.size(); i++) {
			Term t = new Term("content", terms.get(i));

			int df = 0;
			// System.out.println(fileName);
			df = docFreq.get(terms.get(i));
			// System.out.println("df of "+terms.get(i) + ": "+df);
			TermFreqVector termFreqVector = null;
			try {
				termFreqVector = reader.getTermFreqVector(doc, "content");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int pos = termFreqVector.indexOf(terms.get(i));
			if (pos != -1) {
				int count = termFreqVector.getTermFrequencies()[pos];
				float tf = (float) (1 + Math.log10(count));
				System.out.print("tf of " + terms.get(i) + ": " + tf);
				System.out.print(" df of " + df + ": " + df);
				float idf = (float) Math.log10((float) ds / df);
				System.out.println(" idf of " + terms.get(i) + ": " + idf);
				d_t = d_t + tf * idf;
				// System.out.println(d_t);
			}

		}

		float dist1 = (float) Location.getDist(x_center, y_center, x[doc],
				y[doc]);
		float dist2 = r;
		float d = 1 - dist1 / dist2;
		// System.out.println("Spatial = " + d);

		float score = d_t * alpha + d * (1 - alpha);
		try {
			System.out.println(FieldCache.DEFAULT
					.getStrings(reader, "fileName")[doc]
					+ " d_texture = "
					+ d_t + " d_spatial=" + d + " score = " + score);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;

	}
}
