import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.function.CustomScoreProvider;

public class MyCustomScoreProvider extends CustomScoreProvider {
    String[] filenames = null;

    public MyCustomScoreProvider(IndexReader reader) {
	super(reader);
	try {
	    filenames = FieldCache.DEFAULT.getStrings(reader, "filename");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public float customScore(int doc, float subQueryScore, float valSrcScore)
	    throws IOException {
	// ����doc��ȡ�ļ���
	String filename = filenames[doc];
	// �ж��ļ���׺�Ƿ�Ϊtxt��β,�ǰ�����*10 ����/10
	if (filename.endsWith(".txt")) {
	    return subQueryScore * 10;
	} else {
	    return subQueryScore / 10;
	}
    }
}
